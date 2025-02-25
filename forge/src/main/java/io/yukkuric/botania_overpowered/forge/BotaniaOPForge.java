package io.yukkuric.botania_overpowered.forge;

import io.yukkuric.botania_overpowered.BotaniaOP;
import io.yukkuric.botania_overpowered.forge.client.ManaTooltipForge;
import io.yukkuric.botania_overpowered.forge.mekanism.MekasuitManaItem;
import mekanism.common.item.gear.ItemMekaSuitArmor;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vazkii.botania.api.BotaniaForgeCapabilities;

@Mod(BotaniaOP.MOD_ID)
public final class BotaniaOPForge extends BotaniaOP {
    final ResourceLocation MEKASUIT_MANA_ID = ResourceLocation.tryBuild(MOD_ID, "mekasuit_mana");

    @Override
    public boolean isModLoaded(String id) {
        return ModList.get().isLoaded(id);
    }

    public BotaniaOPForge() {
        var evBus = MinecraftForge.EVENT_BUS;
        evBus.addListener(ManaTooltipForge::showMana);
        if (isModLoaded("mekanism")) {
            evBus.addGenericListener(ItemStack.class, (AttachCapabilitiesEvent<ItemStack> e) -> {
                var stack = e.getObject();
                if (!(stack.getItem() instanceof ItemMekaSuitArmor)) return;
                e.addCapability(MEKASUIT_MANA_ID, new ICapabilityProvider() {
                    @Override
                    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction arg) {
                        if (!capability.equals(BotaniaForgeCapabilities.MANA_ITEM)) return LazyOptional.empty();
                        return LazyOptional.of(() -> new MekasuitManaItem(stack)).cast();
                    }
                });
            });
        }

        var ctx = ModLoadingContext.get();
        BotaniaOPConfigForge.register(ctx);
    }
}
