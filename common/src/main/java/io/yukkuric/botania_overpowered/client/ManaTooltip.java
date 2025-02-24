package io.yukkuric.botania_overpowered.client;

import io.yukkuric.botania_overpowered.BotaniaOPConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import vazkii.botania.api.mana.ManaBarTooltip;
import vazkii.botania.xplat.XplatAbstractions;

import java.util.List;

public class ManaTooltip {
    static int COLOR_FULL = colorForPercent(100);

    static int colorForPercent(int percent) {
        return Mth.hsvToRgb(percent / 300F, 1.0F, 1.0F);
    }

    public static void handleManaDisplay(ItemStack stack, List<Component> tooltip) {
        if (!BotaniaOPConfig.showManaAmount()) return;
        var manaItem = XplatAbstractions.INSTANCE.findManaItem(stack);
        if (manaItem == null) return;
        var mana = manaItem.getMana();
        var maxMana = manaItem.getMaxMana();
        var percent = Math.round(ManaBarTooltip.getFractionForDisplay(manaItem) * 100);
        var color = colorForPercent(percent);
        tooltip.add(Component.translatable("bot_op.mana.display",
                Component.literal(String.valueOf(mana)).withStyle(s -> s.withColor(color)),
                Component.literal(String.valueOf(maxMana)).withStyle(s -> s.withColor(COLOR_FULL)),
                Component.literal(String.valueOf(percent) + "%").withStyle(s -> s.withColor(color))
        ));
    }
}
