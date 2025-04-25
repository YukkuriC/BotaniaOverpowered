package io.yukkuric.botania_overpowered.mixin;

import com.simibubi.create.content.processing.burner.BlazeBurnerBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BlazeBurnerBlockEntity.class)
public interface BlazeBurnerAccessor {
    @Accessor(value = "remainingBurnTime", remap = false)
    void setRemainingBurnTime(int value);

    @Accessor(value = "activeFuel", remap = false)
    void setActiveFuel(BlazeBurnerBlockEntity.FuelType value);
}
