package io.yukkuric.botania_overpowered.mixin.enchanter;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.yukkuric.botania_overpowered.BotaniaOPConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
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
}
