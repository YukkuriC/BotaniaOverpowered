package io.yukkuric.botania_overpowered.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.yukkuric.botania_overpowered.BotaniaOPConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import vazkii.botania.common.block.flower.generating.NarslimmusBlockEntity;
import vazkii.botania.common.internal_caps.NarslimmusComponent;

@Mixin(NarslimmusBlockEntity.class)
public class MixinNarslimmus {
    @WrapOperation(method = "tickFlower", at = @At(value = "INVOKE", target = "Lvazkii/botania/common/internal_caps/NarslimmusComponent;isNaturalSpawned()Z"), remap = false)
    boolean noCheckTnt(NarslimmusComponent instance, Operation<Boolean> original) {
        if (!BotaniaOPConfig.skipNarslimmusNaturalCheck()) return original.call(instance);
        return true;
    }
}
