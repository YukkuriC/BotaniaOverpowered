package io.yukkuric.botania_overpowered.mixin;

import io.yukkuric.botania_overpowered.BotaniaOPConfig;
import io.yukkuric.botania_overpowered.mixin_interface.PylonPumpExt;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.*;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vazkii.botania.api.block.WandBindable;
import vazkii.botania.api.mana.ManaPool;
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
        if (!isValidBindingPos(newPos)) newPos = null;
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
    private ManaPool getBoundTargetAt(BlockPos target) {
        if (target == null || level == null) return null;
        var be = level.getBlockEntity(target);
        if (!(be instanceof ManaPool pool)) return null;
        return pool;
    }
    public ManaPool getBoundTarget() {
        return getBoundTargetAt(bindPos);
    }

    // pump tick mixin
    @Inject(method = "commonTick", at = @At("RETURN"), remap = false)
    private static void tickPump(Level level, BlockPos worldPosition, BlockState state, PylonBlockEntity self, CallbackInfo ci) {
        // ensure connection
        if (!BotaniaOPConfig.enablesManaPylonPump()) return;
        var srcBE = level.getBlockEntity(worldPosition.below());
        if (!(srcBE instanceof ManaPool srcPool)) return;
        var targetPool = PylonPumpExt.class.cast(self).getBoundTarget();
        if (targetPool == null) return;

        // do pump
        var extractAmount = BotaniaOPConfig.pylonPumpSpeed();
        extractAmount = Math.min(extractAmount, srcPool.getCurrentMana());
        extractAmount = Math.min(extractAmount, targetPool.getMaxMana() - targetPool.getCurrentMana());
        int reachedAmount = (int) Math.round(extractAmount * (1 - BotaniaOPConfig.pylonPumpLossRatio()));
        srcPool.receiveMana(-extractAmount);
        targetPool.receiveMana(reachedAmount);

        // client fx
        if (level.isClientSide && extractAmount > 0 && BotaniaOPConfig.enablesPylonPumpFx()) {
            // TODO
        }
    }
}
