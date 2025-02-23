package io.yukkuric.botania_overpowered;

public abstract class BotaniaOP {
    public static final String MOD_ID = "botania_overpowered";
    static BotaniaOP INSTANCE;

    public abstract boolean isModLoaded(String id);

    public BotaniaOP() {
        INSTANCE = this;
    }
}
