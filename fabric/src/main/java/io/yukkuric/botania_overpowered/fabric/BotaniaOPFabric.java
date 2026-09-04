package io.yukkuric.botania_overpowered.fabric;

import com.simibubi.create.content.processing.burner.BlazeBurnerBlockEntity;
import io.yukkuric.botania_overpowered.BotaniaOP;
import io.yukkuric.botania_overpowered.BotaniaOPConfig;
import io.yukkuric.botania_overpowered.create.BurnerExoflameHandler;
import io.yukkuric.yclib.YCLib;
import net.fabricmc.api.ModInitializer;
import vazkii.botania.api.BotaniaFabricCapabilities;

public final class BotaniaOPFabric extends BotaniaOP implements ModInitializer {
    @Override
    public void onInitialize() {
        BotaniaOPConfigFabric.setup();

        // exoflame create handler
        YCLib.tryLoadInterop("create", () -> {
            BotaniaFabricCapabilities.EXOFLAME_HEATABLE.registerFallback((world, pos, state, blockEntity, context) -> {
                if (BotaniaOPConfig.heatsBlazeBurner() && blockEntity instanceof BlazeBurnerBlockEntity burner) {
                    return new BurnerExoflameHandler(burner);
                } else {
                    return null;
                }
            });
        });
    }
}
