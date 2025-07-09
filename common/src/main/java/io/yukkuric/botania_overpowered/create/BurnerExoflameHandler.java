package io.yukkuric.botania_overpowered.create;

import com.simibubi.create.content.processing.burner.BlazeBurnerBlockEntity;
import io.yukkuric.botania_overpowered.api.BotaniaOPTags;
import io.yukkuric.botania_overpowered.mixin.BlazeBurnerAccessor;
import vazkii.botania.api.block.ExoflameHeatable;
import vazkii.botania.common.block.flower.functional.ExoflameBlockEntity;

public class BurnerExoflameHandler implements ExoflameHeatable {
    protected static ExoflameBlockEntity theFlower;
    public static void receiveActiveFlowerFromHackedMixin(ExoflameBlockEntity target) {
        theFlower = target;
    }

    protected BlazeBurnerBlockEntity burner;
    protected BlazeBurnerAccessor exposed;
    public BurnerExoflameHandler(BlazeBurnerBlockEntity src) {
        burner = src;
        exposed = BlazeBurnerAccessor.class.cast(src);
    }

    @Override
    public boolean canSmelt() {
        return burner.isValidBlockAbove() || burner.getLevel().getBlockState(burner.getBlockPos().above()).is(BotaniaOPTags.BLAZEBURNER_TARGET);
    }
    @Override
    public int getBurnTime() {
        var level = burner.getActiveFuel();
        var res = burner.getRemainingBurnTime();
        if (theFlower.overgrowth && level.ordinal() < BlazeBurnerBlockEntity.FuelType.SPECIAL.ordinal()) return 0;
        if (!theFlower.overgrowth && level == BlazeBurnerBlockEntity.FuelType.SPECIAL) res += 5000;
        return res;
    }
    @Override
    public void boostBurnTime() {
        var targetLevel = theFlower.overgrowth ? BlazeBurnerBlockEntity.FuelType.SPECIAL : BlazeBurnerBlockEntity.FuelType.NORMAL;
        if (burner.getActiveFuel().ordinal() < targetLevel.ordinal()) {
            exposed.setActiveFuel(targetLevel);
            burner.updateBlockState();
        }
        exposed.setRemainingBurnTime(burner.getRemainingBurnTime() + 200);
    }
    @Override
    public void boostCookTime() {
    }
}
