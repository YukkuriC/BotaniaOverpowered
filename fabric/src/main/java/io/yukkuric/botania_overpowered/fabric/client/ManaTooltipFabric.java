package io.yukkuric.botania_overpowered.fabric.client;

import io.yukkuric.botania_overpowered.client.ManaTooltip;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ManaTooltipFabric extends ManaTooltip implements ItemTooltipCallback {
    @Override
    public void getTooltip(ItemStack itemStack, TooltipFlag tooltipFlag, List<Component> tooltip) {
        handleManaDisplay(itemStack, tooltip);
    }
}
