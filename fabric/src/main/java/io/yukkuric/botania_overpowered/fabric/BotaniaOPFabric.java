package io.yukkuric.botania_overpowered.fabric;

import io.yukkuric.botania_overpowered.BotaniaOP;
import net.fabricmc.api.ModInitializer;

public final class BotaniaOPFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        BotaniaOP.init();
    }
}
