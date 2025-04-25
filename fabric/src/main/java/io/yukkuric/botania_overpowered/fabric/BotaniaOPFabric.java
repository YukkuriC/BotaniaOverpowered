package io.yukkuric.botania_overpowered.fabric;

import com.simibubi.create.content.processing.burner.BlazeBurnerBlockEntity;
import io.yukkuric.botania_overpowered.BotaniaOP;
import io.yukkuric.botania_overpowered.BotaniaOPConfig;
import io.yukkuric.botania_overpowered.create.BurnerExoflameHandler;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import vazkii.botania.api.BotaniaFabricCapabilities;

public final class BotaniaOPFabric extends BotaniaOP implements ModInitializer {
    @Override
    public void onInitialize() {
        BotaniaOPConfigFabric.setup();

        // exoflame create handler
        if (isModLoaded("create")) {
            BotaniaFabricCapabilities.EXOFLAME_HEATABLE.registerFallback((world, pos, state, blockEntity, context) -> {
                if (BotaniaOPConfig.heatsBlazeBurner() && blockEntity instanceof BlazeBurnerBlockEntity burner) {
                    return new BurnerExoflameHandler(burner);
                } else {
                    return null;
                }
            });
        }
    }

    @Override
    public boolean isModLoaded(String id) {
        return FabricLoader.getInstance().isModLoaded(id);
    }
}
