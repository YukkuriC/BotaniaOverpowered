package io.yukkuric.botania_overpowered;

import com.mojang.logging.LogUtils;
import io.yukkuric.botania_overpowered.api.DandelifeonRules;
import org.slf4j.Logger;

public abstract class BotaniaOP {
    public static final String MOD_ID = "botania_overpowered";
    public static final Logger LOGGER = LogUtils.getLogger();
    static BotaniaOP INSTANCE;

    public abstract boolean isModLoaded(String id);

    public BotaniaOP() {
        DandelifeonRules.keepAlive();
        INSTANCE = this;
    }
}
