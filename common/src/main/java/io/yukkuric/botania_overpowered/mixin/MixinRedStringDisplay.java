package io.yukkuric.botania_overpowered.mixin;

import io.yukkuric.botania_overpowered.BotaniaOPConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vazkii.botania.client.render.block_entity.RedStringBlockEntityRenderer;

@Mixin(RedStringBlockEntityRenderer.class)
public class MixinRedStringDisplay {
    @Shadow
    private static int transparency;
    @Inject(method = "tick", at = @At("HEAD"), remap = false, cancellable = true)
    private static void alwaysDisplay(CallbackInfo ci) {
        if (BotaniaOPConfig.keepRedString()) {
            transparency = 10;
            ci.cancel();
        }
    }
}
