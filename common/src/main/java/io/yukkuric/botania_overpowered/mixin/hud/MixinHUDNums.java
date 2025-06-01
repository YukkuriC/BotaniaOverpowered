package io.yukkuric.botania_overpowered.mixin.hud;

import com.llamalad7.mixinextras.sugar.Local;
import io.yukkuric.botania_overpowered.BotaniaOPConfig;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import vazkii.botania.client.impl.BotaniaAPIClientImpl;

@Mixin(BotaniaAPIClientImpl.class)
public class MixinHUDNums {
	@ModifyVariable(method = {"drawSimpleManaHUD", "drawComplexManaHUD"}, at = @At("HEAD"), argsOnly = true)
	String addManaNums(String original, @Local(ordinal = 1, argsOnly = true) int mana, @Local(ordinal = 2, argsOnly = true) int manaMax) {
		if (!BotaniaOPConfig.showManaAmount() || !Screen.hasShiftDown()) return original;
		return "%s(%s/%s)".formatted(original, mana, manaMax);
	}
}
