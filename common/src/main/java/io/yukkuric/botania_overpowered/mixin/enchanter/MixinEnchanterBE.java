package io.yukkuric.botania_overpowered.mixin.enchanter;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.yukkuric.botania_overpowered.BotaniaOPConfig;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vazkii.botania.common.block.block_entity.ManaEnchanterBlockEntity;

@Mixin(ManaEnchanterBlockEntity.class)
public class MixinEnchanterBE {
    @Shadow
    public ItemStack itemToEnchant;
    @Inject(method = "isEnchantmentValid", at = @At("HEAD"), remap = false, cancellable = true)
    void acceptsBook(Enchantment ench, CallbackInfoReturnable<Boolean> cir) {
        if ((BotaniaOPConfig.enchantBooks() && itemToEnchant.is(Items.BOOK))
                || (BotaniaOPConfig.ignoresCompatibleCheck() && ench.canEnchant(itemToEnchant))) {
            cir.setReturnValue(true);
        }
    }
    @WrapOperation(method = "commonTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;enchant(Lnet/minecraft/world/item/enchantment/Enchantment;I)V"))
    private static void bookEnchantWrap(ItemStack instance, Enchantment enchantment, int level, Operation<Void> original, @Local(argsOnly = true) ManaEnchanterBlockEntity self) {
        original.call(instance, enchantment, level);
        if (BotaniaOPConfig.enchantBooks() && instance.is(Items.BOOK)) {
            self.itemToEnchant = new ItemStack(Items.ENCHANTED_BOOK, 1);
            self.itemToEnchant.getOrCreateTag().put("StoredEnchantments", instance.getOrCreateTag().get("Enchantments"));
        }
    }
}
