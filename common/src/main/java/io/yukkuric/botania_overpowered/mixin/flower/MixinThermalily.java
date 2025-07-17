package io.yukkuric.botania_overpowered.mixin.flower;

import io.yukkuric.botania_overpowered.BotaniaOPConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vazkii.botania.api.block_entity.GeneratingFlowerBlockEntity;
import vazkii.botania.common.block.flower.generating.FluidGeneratorBlockEntity;

@Mixin(FluidGeneratorBlockEntity.class)
public abstract class MixinThermalily extends GeneratingFlowerBlockEntity {
    public MixinThermalily(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Shadow(remap = false)
    protected int cooldown;

    @Inject(method = "tickFlower", at = @At(value = "INVOKE", target = "Ljava/util/Arrays;asList([Ljava/lang/Object;)Ljava/util/List;", ordinal = 0), remap = false, cancellable = true)
    public void cutBeforeCooldownReduce(CallbackInfo ci) {
        if (BotaniaOPConfig.enablesPassiveThermalily() && this.cooldown > 0) ci.cancel();
    }
}
