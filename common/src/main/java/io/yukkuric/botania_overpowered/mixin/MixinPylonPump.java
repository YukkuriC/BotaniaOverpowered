package io.yukkuric.botania_overpowered.mixin;

import io.yukkuric.botania_overpowered.BotaniaOPConfig;
import io.yukkuric.botania_overpowered.mixin_interface.PylonPumpExt;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.*;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vazkii.botania.api.block.WandBindable;
import vazkii.botania.api.mana.ManaReceiver;
import vazkii.botania.client.fx.WispParticleData;
import vazkii.botania.common.block.PylonBlock;
import vazkii.botania.common.block.block_entity.PylonBlockEntity;
import vazkii.botania.common.helper.MathHelper;

import java.util.Objects;

@Mixin(PylonBlockEntity.class)
public class MixinPylonPump extends BlockEntity implements WandBindable, PylonPumpExt {
    public MixinPylonPump(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    // WandBindable
    private BlockPos bindPos = null;
    public boolean canSelect(Player player, ItemStack itemStack, BlockPos blockPos, Direction direction) {
        return BotaniaOPConfig.enablesManaPylonPump();
    }
    public boolean bindTo(Player foo, ItemStack bar, BlockPos newPos, Direction baz) {
        if (!BotaniaOPConfig.enablesManaPylonPump()) return false;
        if (!isValidBindingPos(newPos) || worldPosition.below().equals(newPos)) newPos = null;
        if (!Objects.equals(newPos, bindPos)) {
            bindPos = newPos;
            setChanged();
        }
        return true;
    }
    public @Nullable BlockPos getBinding() {
        if (!BotaniaOPConfig.enablesManaPylonPump()) return null;
        return bindPos;
    }
    private boolean isValidBindingPos(BlockPos pos) {
        if (level == null || !level.isLoaded(pos)) return false;
        var maxDist = BotaniaOPConfig.pylonPumpMaxRange();
        if (MathHelper.distSqr(worldPosition, pos) > maxDist * maxDist) return false;
        return getBoundTargetAt(pos) != null;
    }

    // BlockEntity with sync
    public void load(CompoundTag tag) {
        if (tag.contains("pos", Tag.TAG_COMPOUND)) bindPos = NbtUtils.readBlockPos(tag.getCompound("pos"));
    }
    protected void saveAdditional(CompoundTag tag) {
        if (bindPos != null) tag.put("pos", NbtUtils.writeBlockPos(bindPos));
    }
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
    public @NotNull CompoundTag getUpdateTag() {
        var res = new CompoundTag();
        saveAdditional(res);
        return res;
    }

    // PylonPumpExt, no cfg check
    private ManaReceiver getBoundTargetAt(BlockPos target) {
        if (target == null || level == null) return null;
        var be = level.getBlockEntity(target);
        if (!(be instanceof ManaReceiver pool)) return null;
        return pool;
    }
    public ManaReceiver getBoundTarget() {
        return getBoundTargetAt(bindPos);
    }
    public Vec3 getBoundCenter() {
        return Vec3.atCenterOf(bindPos);
    }

    // pump tick mixin
    @Inject(method = "commonTick", at = @At("RETURN"), remap = false)
    private static void tickPump(Level level, BlockPos worldPosition, BlockState state, PylonBlockEntity self, CallbackInfo ci) {
        // ensure connection
        if (!BotaniaOPConfig.enablesManaPylonPump()) return;
        var srcBE = level.getBlockEntity(worldPosition.below());
        if (!(srcBE instanceof ManaReceiver srcPool)) return;
        var targetPool = ((PylonPumpExt) self).getBoundTarget();
        if (targetPool == null || targetPool.isFull()) return;

        // do pump
        var extractAmount = BotaniaOPConfig.pylonPumpSpeed();
        extractAmount = Math.min(extractAmount, srcPool.getCurrentMana());
        int reachedAmount = (int) Math.round(extractAmount * (1 - BotaniaOPConfig.pylonPumpLossRatio()));
        if (reachedAmount <= 0) return;
        var oldMana = targetPool.getCurrentMana();
        targetPool.receiveMana(reachedAmount);
        var reachedReal = targetPool.getCurrentMana() - oldMana;
        if (reachedReal != reachedAmount)
            extractAmount = Math.round((float) extractAmount * reachedReal / (float) reachedAmount);
        srcPool.receiveMana(-extractAmount);

        // client fx
        if (level.isClientSide && extractAmount > 0 && BotaniaOPConfig.enablesPylonPumpFx()) {
            var variant = ((PylonBlock) state.getBlock()).variant;
            var start = Vec3.atCenterOf(worldPosition);
            var end = ((PylonPumpExt) self).getBoundCenter();
            var dir = end.subtract(start).normalize().scale(0.5);
            start = start.add(dir);
            end = end.subtract(dir);
            for (var i = 0; i < BotaniaOPConfig.pylonPumpFxStrength(); i++) {
                var particle = WispParticleData.wisp((float) Math.random() / 3.0F, variant.r, variant.g, variant.b, 0.5f).withNoClip(true);
                var mid = Math.random();
                var speed = Math.random() * 0.2;
                level.addParticle(particle, Mth.lerp(mid, start.x, end.x), Mth.lerp(mid, start.y, end.y), Mth.lerp(mid, start.z, end.z), speed * dir.x, speed * dir.y, speed * dir.z);
            }
        }
    }
}
