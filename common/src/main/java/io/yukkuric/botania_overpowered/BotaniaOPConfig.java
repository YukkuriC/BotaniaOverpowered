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
    public static int ruleLifeGameNew() {
        return CFG.ruleLifeGameNew();
    }
    public static int ruleLifeGameKeep() {
        return CFG.ruleLifeGameKeep();
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
    public static boolean heatsBlazeBurner() {
        return CFG.heatsBlazeBurner();
    }
    public static boolean enablesManaPylonPump() {
        return CFG.enablesManaPylonPump();
    }
    public static int pylonPumpMaxRange() {
        return CFG.pylonPumpMaxRange();
    }
    public static int pylonPumpSpeed() {
        return CFG.pylonPumpSpeed();
    }
    public static double pylonPumpLossRatio() {
        return CFG.pylonPumpLossRatio();
    }
    public static boolean enablesPylonPumpFx() {
        return CFG.enablesPylonPumpFx();
    }
    public interface CommonAccess {
        String desc_showManaAmount = "displays the EVIL numbers";
        String desc_skipDandelifeonClearBoard = "no longer wipe all cells out when consuming";
        String desc_ruleLifeGameNew = "custom rule bitset for new cells";
        String desc_ruleLifeGameKeep = "custom rule bitset for keeping cells";
        String desc_skipEntropinnyumDuperCheck = "accepts TNTs from dupers";
        String desc_enableEntropinnyumUnderwater = "accepts TNTs inside liquids";
        String desc_skipNarslimmusNaturalCheck = "accepts ALL slimes";
        String desc_heatsBlazeBurner = "heats Blaze Burner blocks from Create";
        String desc_enablesManaPylonPump = "makes Mana Pylons extract mana from the pool below and dump to wand-bound mana pool";
        String desc_pylonPumpMaxRange = "max distance between the pylon and the pool bound";
        String desc_pylonPumpSpeed = "how many mana points will be extracted each tick (for reference, each spark pair has speed of 1000)";
        String desc_pylonPumpLossRatio = "how much ratio of mana will vanish during the transport";
        String desc_enablesPylonPumpFx = "displays extra particles when the pylon pump works";
        boolean showManaAmount();
        boolean skipDandelifeonClearBoard();
        int ruleLifeGameNew();
        int ruleLifeGameKeep();
        boolean skipEntropinnyumDuperCheck();
        boolean enableEntropinnyumUnderwater();
        boolean skipNarslimmusNaturalCheck();
        boolean heatsBlazeBurner();
        boolean enablesManaPylonPump();
        int pylonPumpMaxRange();
        int pylonPumpSpeed();
        double pylonPumpLossRatio();
        boolean enablesPylonPumpFx();
    }
}
