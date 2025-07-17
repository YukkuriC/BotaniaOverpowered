package io.yukkuric.botania_overpowered.mixin.flower;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.yukkuric.botania_overpowered.BotaniaOPConfig;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import vazkii.botania.common.block.flower.generating.EntropinnyumBlockEntity;
import vazkii.botania.common.internal_caps.EthicalComponent;

@Mixin(EntropinnyumBlockEntity.class)
public class MixinEntropinnyum {
    @WrapOperation(method = "tickFlower", at = @At(value = "INVOKE", target = "Lvazkii/botania/common/internal_caps/EthicalComponent;isUnethical()Z"), remap = false)
    boolean noCheckTnt(EthicalComponent instance, Operation<Boolean> original) {
        if (!BotaniaOPConfig.skipEntropinnyumDuperCheck()) return original.call(instance);
        return false;
    }

    @WrapOperation(method = "tickFlower", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/material/FluidState;isEmpty()Z"))
    boolean noCheckFluid(FluidState instance, Operation<Boolean> original) {
        if (!BotaniaOPConfig.enableEntropinnyumUnderwater()) return original.call(instance);
        return true;
    }
}
