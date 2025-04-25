package io.yukkuric.botania_overpowered.create;

import com.simibubi.create.content.processing.burner.BlazeBurnerBlockEntity;
import io.yukkuric.botania_overpowered.mixin.BlazeBurnerAccessor;
import vazkii.botania.api.block.ExoflameHeatable;

public class BurnerExoflameHandler implements ExoflameHeatable {
    protected BlazeBurnerBlockEntity burner;
    protected BlazeBurnerAccessor exposed;
    public BurnerExoflameHandler(BlazeBurnerBlockEntity src) {
        burner = src;
        exposed = BlazeBurnerAccessor.class.cast(src);
    }

    @Override
    public boolean canSmelt() {
        return burner.isValidBlockAbove();
    }
    @Override
    public int getBurnTime() {
        var res = burner.getRemainingBurnTime();
        if (burner.getActiveFuel() == BlazeBurnerBlockEntity.FuelType.SPECIAL) res += 5000;
        return res;
    }
    @Override
    public void boostBurnTime() {
        if (burner.getActiveFuel() == BlazeBurnerBlockEntity.FuelType.NONE)
            exposed.setActiveFuel(BlazeBurnerBlockEntity.FuelType.NORMAL);
        exposed.setRemainingBurnTime(burner.getRemainingBurnTime() + 200);
    }
    @Override
    public void boostCookTime() {
    }
}
