package io.yukkuric.botania_overpowered.mixin.enchanter;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.yukkuric.botania_overpowered.BotaniaOPConfig;
import net.minecraft.world.item.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import vazkii.botania.common.block.mana.ManaEnchanterBlock;

@Mixin(ManaEnchanterBlock.class)
public class MixinEnchanterBlock {
    @WrapOperation(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;is(Lnet/minecraft/world/item/Item;)Z"))
    boolean acceptBooks(ItemStack instance, Item item, Operation<Boolean> original) {
        if (BotaniaOPConfig.enchantBooks() && instance.is(Items.BOOK)) return false;
        return original.call(instance, item);
    }
}
