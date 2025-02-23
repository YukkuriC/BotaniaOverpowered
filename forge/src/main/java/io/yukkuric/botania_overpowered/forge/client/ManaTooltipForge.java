package io.yukkuric.botania_overpowered.forge.client;

import io.yukkuric.botania_overpowered.client.ManaTooltip;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class ManaTooltipForge extends ManaTooltip {
    public static void showMana(ItemTooltipEvent e) {
        var stack = e.getItemStack();
        var tooltip = e.getToolTip();
        handleManaDisplay(stack, tooltip);
    }
}
