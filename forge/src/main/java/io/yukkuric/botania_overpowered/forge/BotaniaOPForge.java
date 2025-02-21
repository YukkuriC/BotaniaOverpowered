package io.yukkuric.botania_overpowered.forge;

import io.yukkuric.botania_overpowered.BotaniaOP;
import net.minecraftforge.fml.common.Mod;

@Mod(BotaniaOP.MOD_ID)
public final class BotaniaOPForge {
    public BotaniaOPForge() {
        // Run our common setup.
        BotaniaOP.init();
    }
}
