package io.yukkuric.botania_overpowered;

public class BotaniaOPConfig {
    static CommonAccess CFG;
    public static void bindConfig(CommonAccess cfg) {
        CFG = cfg;
    }

    public static boolean showManaAmount() {
        return CFG.showManaAmount();
    }
    public static boolean skipDandelifeonClearBoard() {
        return CFG.skipDandelifeonClearBoard();
    }
    public static boolean skipEntropinnyumDuperCheck() {
        return CFG.skipEntropinnyumDuperCheck();
    }
    public static boolean enableEntropinnyumUnderwater() {
        return CFG.enableEntropinnyumUnderwater();
    }
    public static boolean skipNarslimmusNaturalCheck() {
        return CFG.skipNarslimmusNaturalCheck();
    }

    public interface CommonAccess {
        boolean showManaAmount();
        boolean skipDandelifeonClearBoard();
        boolean skipEntropinnyumDuperCheck();
        boolean enableEntropinnyumUnderwater();
        boolean skipNarslimmusNaturalCheck();
    }
}
