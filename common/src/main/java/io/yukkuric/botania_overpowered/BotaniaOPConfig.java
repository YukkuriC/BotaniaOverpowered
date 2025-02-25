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
        static String desc_showManaAmount="displays the EVIL numbers";
        static String desc_skipDandelifeonClearBoard="no longer wipe all cells out when consuming";
        static String desc_skipEntropinnyumDuperCheck="accepts TNTs from dupers";
        static String desc_enableEntropinnyumUnderwater="accepts TNTs inside liquids";
        static String desc_skipNarslimmusNaturalCheck="accepts ALL slimes";

        boolean showManaAmount();
        boolean skipDandelifeonClearBoard();
        boolean skipEntropinnyumDuperCheck();
        boolean enableEntropinnyumUnderwater();
        boolean skipNarslimmusNaturalCheck();
    }
}
