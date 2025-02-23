package io.yukkuric.botania_overpowered.fabric;

import io.yukkuric.botania_overpowered.BotaniaOP;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public final class BotaniaOPFabric extends BotaniaOP implements ModInitializer {
    @Override
    public void onInitialize() {
    }

    @Override
    public boolean isModLoaded(String id) {
        return FabricLoader.getInstance().isModLoaded(id);
    }
}
