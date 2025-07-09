package io.yukkuric.botania_overpowered.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.yukkuric.botania_overpowered.BotaniaOPConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vazkii.botania.api.block_entity.GeneratingFlowerBlockEntity;
import vazkii.botania.common.block.flower.generating.EndoflameBlockEntity;

@Mixin(EndoflameBlockEntity.class)
public abstract class MixinEndoflame extends GeneratingFlowerBlockEntity {
    @Shadow(remap = false)
    private int burnTime;
    @Shadow(remap = false)
    public abstract int getMaxMana();
    @Shadow(remap = false)
    @Final
    private static int FUEL_CAP;
    public MixinEndoflame(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    private int time2mana(int time) {
        return (int) Math.min(Integer.MAX_VALUE, time * 3f / 2);
    }
    private int mana2time(int mana) {
        return (int) Math.min(Integer.MAX_VALUE, mana * 2f / 3);
    }

    private int overrideMaxMana = -1;
    @Inject(method = "getMaxMana", at = @At("HEAD"), remap = false, cancellable = true)
    public void replacedMaxMana(CallbackInfoReturnable<Integer> cir) {
        if (BotaniaOPConfig.extraCapacityForFuel() && overrideMaxMana > 0) cir.setReturnValue(overrideMaxMana);
    }
    @Inject(method = "getBurnTime", at = @At("RETURN"), remap = false)
    public void calcMaxMana(CallbackInfoReturnable<Integer> cir) {
        overrideMaxMana = Math.max(getMaxMana(), time2mana(cir.getReturnValue()));
    }

    @Inject(method = "tickFlower", at = @At("HEAD"), remap = false)
    void manualBoostTicks(CallbackInfo ci) {
        if (!BotaniaOPConfig.instantBurnFuel()) return;
        if (burnTime > 0) {
            var maxManaFromTime = time2mana(burnTime);
            var maxManaToFill = getMaxMana() - getMana();
            if (maxManaToFill >= maxManaFromTime) {
                addMana(maxManaFromTime);
                burnTime = 0;
            } else {
                addMana(maxManaToFill);
                burnTime -= mana2time(maxManaToFill);
            }
        }
    }

    @WrapOperation(method = "tickFlower", at = @At(value = "INVOKE", target = "Ljava/lang/Math;min(II)I"), remap = false)
    int noMore32000Limit(int a, int b, Operation<Integer> original) {
        if (a != FUEL_CAP || !BotaniaOPConfig.breaksMaxBurningTimeLimit()) return original.call(a, b);
        return b;
    }
}
