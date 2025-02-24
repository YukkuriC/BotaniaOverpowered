package io.yukkuric.botania_overpowered.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.yukkuric.botania_overpowered.BotaniaOPConfig;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vazkii.botania.common.block.flower.generating.NarslimmusBlockEntity;
import vazkii.botania.common.internal_caps.NarslimmusComponent;

@Mixin(NarslimmusBlockEntity.class)
public class MixinNarslimmus {
    @WrapOperation(method = "tickFlower", at = @At(value = "INVOKE", target = "Lvazkii/botania/common/internal_caps/NarslimmusComponent;isNaturalSpawned()Z"), remap = false)
    boolean alwaysNatural(NarslimmusComponent instance, Operation<Boolean> original) {
        if (!BotaniaOPConfig.skipNarslimmusNaturalCheck()) return original.call(instance);
        return true;
    }

    @Inject(method = "onSpawn", at = @At("HEAD"), cancellable = true)
    private static void noSetNatural(Entity entity, CallbackInfo ci) {
        if (BotaniaOPConfig.skipNarslimmusNaturalCheck()) ci.cancel();
    }
}
