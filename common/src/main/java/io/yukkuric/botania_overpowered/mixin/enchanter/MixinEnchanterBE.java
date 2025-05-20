package io.yukkuric.botania_overpowered.mixin.enchanter;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.yukkuric.botania_overpowered.BotaniaOPConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vazkii.botania.common.block.block_entity.ManaEnchanterBlockEntity;
import vazkii.botania.common.handler.BotaniaSounds;

import java.util.List;
import java.util.Objects;

@Mixin(ManaEnchanterBlockEntity.class)
public abstract class MixinEnchanterBE extends BlockEntity {
    public MixinEnchanterBE(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Shadow(remap = false)
    public ItemStack itemToEnchant;
    @Shadow(remap = false)
    @Final
    private List<EnchantmentInstance> enchants;
    @Shadow(remap = false)
    protected abstract boolean hasEnchantAlready(Enchantment enchant);
    @Shadow(remap = false)
    protected abstract boolean isEnchantmentValid(@Nullable Enchantment ench);
    @Shadow(remap = false)
    public ManaEnchanterBlockEntity.State stage;
    @Shadow(remap = false)
    protected abstract void advanceStage();
    @Inject(method = "isEnchantmentValid", at = @At("HEAD"), remap = false, cancellable = true)
    void acceptsBook(Enchantment ench, CallbackInfoReturnable<Boolean> cir) {
        if ((BotaniaOPConfig.enchantBooks() && itemToEnchant.is(Items.BOOK))
                || (BotaniaOPConfig.ignoresCompatibleCheck() && ench.canEnchant(itemToEnchant))) {
            cir.setReturnValue(true);
        }
    }
    @Inject(method = "commonTick", at = @At(value = "INVOKE", target = "Ljava/util/List;clear()V"))
    private static void bookEnchantWrap(Level level, BlockPos worldPosition, BlockState state, ManaEnchanterBlockEntity self, CallbackInfo ci) {
        if (BotaniaOPConfig.enchantBooks() && self.itemToEnchant.is(Items.BOOK)) {
            var oldItem = self.itemToEnchant;
            self.itemToEnchant = new ItemStack(Items.ENCHANTED_BOOK, 1);
            self.itemToEnchant.getOrCreateTag().put("StoredEnchantments", oldItem.getOrCreateTag().get("Enchantments"));
        }
    }

    @Inject(method = "gatherEnchants", at = @At(value = "INVOKE_ASSIGN", target = "Ljava/util/List;iterator()Ljava/util/Iterator;"), cancellable = true, remap = false)
    void collectElse(CallbackInfo ci, @Local List<ItemEntity> items) {
        if (!BotaniaOPConfig.acceptsAllInsideBook()) return;
        var locatedBookThisTurn = false;

        // each book full check
        for (var entity : items) {
            var item = entity.getItem();
            if (!item.is(Items.ENCHANTED_BOOK)) continue;
            var enchants = EnchantmentHelper.getEnchantments(item);
            if (enchants.isEmpty()) continue;
            var hasEnchantsThisBook = false;
            for (var pair : enchants.entrySet()) {
                var enchant = pair.getKey();
                if (enchant == null || this.hasEnchantAlready(enchant) || !this.isEnchantmentValid(enchant)) continue;
                this.enchants.add(new EnchantmentInstance(enchant, pair.getValue()));
                hasEnchantsThisBook = true;
            }
            if (hasEnchantsThisBook) {
                this.level.playSound(null, this.worldPosition, BotaniaSounds.ding, SoundSource.BLOCKS, 1.0F, 1.0F);
                locatedBookThisTurn = true;
                break;
            }
        }

        if (!locatedBookThisTurn) {
            if (this.enchants.isEmpty()) stage = ManaEnchanterBlockEntity.State.IDLE;
            else advanceStage();
        }

        ci.cancel();
    }

    @WrapOperation(method = "gatherEnchants", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"))
    boolean everythingIsBook(ItemStack instance, Item item, Operation<Boolean> original) {
        if (BotaniaOPConfig.treatEnchantedItemAsBook() && Objects.equals(item, Items.ENCHANTED_BOOK)) return true;
        return original.call(instance, item);
    }

    // skip wand use check, I swear nobody could notice this
    @Inject(method = "onUsedByWand", at = @At("HEAD"), remap = false, cancellable = true)
    void skipWandInitialCheck(Player player, ItemStack wand, Direction side, CallbackInfoReturnable<Boolean> cir) {
        if (stage == ManaEnchanterBlockEntity.State.IDLE) {
            this.advanceStage();
            cir.setReturnValue(true);
        }
    }
}
