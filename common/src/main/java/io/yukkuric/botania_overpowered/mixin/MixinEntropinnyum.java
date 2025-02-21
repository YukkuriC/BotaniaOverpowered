package io.yukkuric.botania_overpowered.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import vazkii.botania.common.block.flower.generating.EntropinnyumBlockEntity;
import vazkii.botania.common.internal_caps.EthicalComponent;

@Mixin(EntropinnyumBlockEntity.class)
public class MixinEntropinnyum {
    @WrapOperation(method = "tickFlower", at = @At(value = "INVOKE", target = "Lvazkii/botania/common/internal_caps/EthicalComponent;isUnethical()Z"), remap = false)
    boolean noCheckTnt(EthicalComponent instance, Operation<Boolean> original) {
        return false;
    }

    @WrapOperation(method = "tickFlower", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/material/FluidState;isEmpty()Z"))
    boolean noCheckFluid(FluidState instance, Operation<Boolean> original) {
        return true;
    }
}
