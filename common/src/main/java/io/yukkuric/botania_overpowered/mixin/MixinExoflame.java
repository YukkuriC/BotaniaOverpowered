package io.yukkuric.botania_overpowered.mixin;

import io.yukkuric.botania_overpowered.create.BurnerExoflameHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vazkii.botania.api.block_entity.SpecialFlowerBlockEntity;
import vazkii.botania.common.block.flower.functional.ExoflameBlockEntity;

@Mixin(ExoflameBlockEntity.class)
public abstract class MixinExoflame extends SpecialFlowerBlockEntity {
    public MixinExoflame(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Inject(method = "tickFlower", at = @At("HEAD"), remap = false)
    void tellBurnersWhoIAmInSingleThread(CallbackInfo ci) {
        if (level.isClientSide) return;
        BurnerExoflameHandler.receiveActiveFlowerFromHackedMixin(ExoflameBlockEntity.class.cast(this));
    }
}
