package io.yukkuric.botania_overpowered.forge.mekanism;

import com.google.common.base.Suppliers;
import io.yukkuric.botania_overpowered.forge.BotaniaOPConfigForge;
import mekanism.api.energy.IEnergyContainer;
import mekanism.api.math.FloatingLong;
import mekanism.common.util.StorageUtils;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import vazkii.botania.api.mana.ManaItem;

import java.util.function.Supplier;

public class MekasuitManaItem implements ManaItem {
    final Supplier<IEnergyContainer> source;

    public MekasuitManaItem(ItemStack target) {
        source = Suppliers.memoize(() -> StorageUtils.getEnergyContainer(target, 0));
    }

    static int Joule2Mana(FloatingLong raw) {
        var FE = raw.multiply(0.4); // j -> fe
        return FE.multiply(BotaniaOPConfigForge.MekasuitConversionRatio()).intValue();
    }

    static boolean isRatioValid() {
        return BotaniaOPConfigForge.MekasuitConversionRatio() > 0;
    }

    @Override
    public int getMana() {
        return Joule2Mana(source.get().getEnergy());
    }

    @Override
    public int getMaxMana() {
        return Joule2Mana(source.get().getMaxEnergy());
    }

    @Override
    public void addMana(int i) {
        var ratio = BotaniaOPConfigForge.MekasuitConversionRatio();
        if (ratio <= 0) return;
        var finalMana = getMana() + i;
        finalMana = Math.max(finalMana, 0);
        var fe = FloatingLong.create(finalMana / ratio);
        source.get().setEnergy(fe.multiply(2.5)); // fe -> j
    }

    @Override
    public boolean canReceiveManaFromPool(BlockEntity blockEntity) {
        return isRatioValid();
    }

    @Override
    public boolean canReceiveManaFromItem(ItemStack itemStack) {
        return false;
    }

    @Override
    public boolean canExportManaToPool(BlockEntity blockEntity) {
        return isRatioValid();
    }

    @Override
    public boolean canExportManaToItem(ItemStack itemStack) {
        return isRatioValid();
    }

    @Override
    public boolean isNoExport() {
        return false;
    }
}
