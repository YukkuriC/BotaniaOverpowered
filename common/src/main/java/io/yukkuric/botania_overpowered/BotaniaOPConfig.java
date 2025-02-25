package io.yukkuric.botania_overpowered;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BotaniaOPConfig {
    static CommonAccess CFG;
    static final List<Consumer<CommonAccess>> onLoadedCFG = new ArrayList<>();

    public static void bindConfigLoaded(Consumer<CommonAccess> trigger) {
        onLoadedCFG.add(trigger);
    }
    public static void bindConfig(CommonAccess cfg) {
        CFG = cfg;
        for (var f : onLoadedCFG) {
            try {
                f.accept(cfg);
            } catch (Throwable e) {
                BotaniaOP.LOGGER.error("Error on loading config", e);
            }
        }
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
        static String desc_showManaAmount = "displays the EVIL numbers";
        static String desc_skipDandelifeonClearBoard = "no longer wipe all cells out when consuming";
        static String desc_ruleLifeGameNew = "custom rule bitset for new cells";
        static String desc_ruleLifeGameKeep = "custom rule bitset for keeping cells";
        static String desc_skipEntropinnyumDuperCheck = "accepts TNTs from dupers";
        static String desc_enableEntropinnyumUnderwater = "accepts TNTs inside liquids";
        static String desc_skipNarslimmusNaturalCheck = "accepts ALL slimes";

        boolean showManaAmount();
        boolean skipDandelifeonClearBoard();
        int ruleLifeGameNew();
        int ruleLifeGameKeep();
        boolean skipEntropinnyumDuperCheck();
        boolean enableEntropinnyumUnderwater();
        boolean skipNarslimmusNaturalCheck();
    }
}
