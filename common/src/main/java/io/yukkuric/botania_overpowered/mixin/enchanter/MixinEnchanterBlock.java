package io.yukkuric.botania_overpowered.mixin.enchanter;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.yukkuric.botania_overpowered.BotaniaOPConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.Tag;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vazkii.botania.common.block.block_entity.ManaEnchanterBlockEntity;
import vazkii.botania.common.block.mana.ManaEnchanterBlock;

@Mixin(ManaEnchanterBlock.class)
public class MixinEnchanterBlock {
    @WrapMethod(method = "use")
    InteractionResult replaceBookStack(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit, Operation<InteractionResult> original) {
        // before: replace to stack 1
        var enchanter = (ManaEnchanterBlockEntity) world.getBlockEntity(pos);
        var oldStack = player.getItemInHand(hand);
        var mustReplace = BotaniaOPConfig.enchantBooks() // config on
                && oldStack.is(Items.BOOK) && oldStack.getCount() > 1 // has pile of books
                && enchanter != null && enchanter.itemToEnchant.isEmpty(); // enchanter accepting item
        if (mustReplace) {
            player.setItemInHand(hand, new ItemStack(Items.BOOK));
            oldStack.shrink(1);
        }

        // original func
        var res = original.call(state, world, pos, player, hand, hit);

        // after: recover stack
        if (mustReplace) player.setItemInHand(hand, oldStack);
        return res;
    }

    @WrapOperation(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"))
    boolean acceptBooks(ItemStack instance, Item item, Operation<Boolean> original) {
        if (BotaniaOPConfig.enchantBooks() && instance.is(Items.BOOK)) return false;
        return original.call(instance, item);
    }

    @Inject(method = "onRemove", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/Containers;dropItemStack(Lnet/minecraft/world/level/Level;DDDLnet/minecraft/world/item/ItemStack;)V"), remap = false)
    void finalSplit(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving, CallbackInfo ci, @Local ManaEnchanterBlockEntity enchanter) {
        if (!BotaniaOPConfig.doFinalEnchantmentSplit()) return;
        var isBook = enchanter.itemToEnchant.is(Items.ENCHANTED_BOOK);
        var enchantments = EnchantmentHelper.getEnchantments(enchanter.itemToEnchant).entrySet()
                .stream().map((pair) -> new EnchantmentInstance(pair.getKey(), pair.getValue())).toList();
        if (enchantments.isEmpty()) return;
        var isFirst = true;
        for (var e : enchantments) {
            if (isFirst) {
                isFirst = false;
                if (isBook) {
                    enchanter.itemToEnchant.getOrCreateTag().getList("StoredEnchantments", Tag.TAG_COMPOUND).clear();
                    EnchantedBookItem.addEnchantment(enchanter.itemToEnchant, e);
                    continue;
                }
            }
            var newBook = EnchantedBookItem.createForEnchantment(e);
            Containers.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), newBook);
        }
        if (!isBook) enchanter.itemToEnchant.getEnchantmentTags().clear();
    }
}
