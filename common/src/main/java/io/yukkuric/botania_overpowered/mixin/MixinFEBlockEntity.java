package io.yukkuric.botania_overpowered.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.yukkuric.botania_overpowered.BotaniaOPConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import vazkii.botania.common.block.block_entity.mana.PowerGeneratorBlockEntity;

@Mixin(PowerGeneratorBlockEntity.class)
public class MixinFEBlockEntity {
    @WrapOperation(method = "serverTick", at = @At(value = "INVOKE", target = "Ljava/lang/Math;min(II)I"))
    private static int increaseOutput(int energy, int required, Operation<Integer> original) {
        required *= BotaniaOPConfig.multiplyFEGeneratorOutput();
        return original.call(energy, required);
    }
}
