package io.yukkuric.botania_overpowered;

public class BotaniaOPConfig {
    static CommonAccess CFG;
    public static void bindConfig(CommonAccess cfg) {
        CFG = cfg;
    }
    /// <b>Display</b><br>displays the EVIL numbers
    public static boolean showManaAmount() {
        return CFG.showManaAmount();
    }
    /// <b>Display</b><br>always show red strings even without wand in hand; might have inpact on frame rate, use at your own risk
    public static boolean keepRedString() {
        return CFG.keepRedString();
    }
    /// <b>Dandelifeon</b><br>no longer wipe all cells out when consuming
    public static boolean skipDandelifeonClearBoard() {
        return CFG.skipDandelifeonClearBoard();
    }
    /// <b>Dandelifeon</b><br>custom rule bitset for new cells
    public static int ruleLifeGameNew() {
        return CFG.ruleLifeGameNew();
    }
    /// <b>Dandelifeon</b><br>custom rule bitset for keeping cells
    public static int ruleLifeGameKeep() {
        return CFG.ruleLifeGameKeep();
    }
    /// <b>Entropinnyum</b><br>accepts TNTs from dupers
    public static boolean skipEntropinnyumDuperCheck() {
        return CFG.skipEntropinnyumDuperCheck();
    }
    /// <b>Entropinnyum</b><br>accepts TNTs inside liquids
    public static boolean enableEntropinnyumUnderwater() {
        return CFG.enableEntropinnyumUnderwater();
    }
    /// <b>Narslimmus</b><br>accepts ALL slimes
    public static boolean skipNarslimmusNaturalCheck() {
        return CFG.skipNarslimmusNaturalCheck();
    }
    /// <b>Endoflame</b><br>skips the burning process for fuel items
    public static boolean instantBurnFuel() {
        return CFG.instantBurnFuel();
    }
    /// <b>Endoflame</b><br>increases capacity when consuming large fuel
    public static boolean extraCapacityForFuel() {
        return CFG.extraCapacityForFuel();
    }
    /// <b>Endoflame</b><br>no more 32000 tick burning limit
    public static boolean breaksMaxBurningTimeLimit() {
        return CFG.breaksMaxBurningTimeLimit();
    }
    /// <b>Exoflame</b><br>heats Blaze Burner blocks from Create
    public static boolean heatsBlazeBurner() {
        return CFG.heatsBlazeBurner();
    }
    /// <b>Exoflame</b><br>applies hyper-heat for Blaze Burner blocks when on enchanted soil
    public static boolean enchantedSoilEnablesSuperHeat() {
        return CFG.enchantedSoilEnablesSuperHeat();
    }
    /// <b>ManaPylonPump</b><br>makes Mana Pylons extract mana from the pool below and dump to wand-bound mana pool
    public static boolean enablesManaPylonPump() {
        return CFG.enablesManaPylonPump();
    }
    /// <b>ManaPylonPump</b><br>max distance between the pylon and the pool bound
    public static int pylonPumpMaxRange() {
        return CFG.pylonPumpMaxRange();
    }
    /// <b>ManaPylonPump</b><br>how many mana points will be extracted each tick (for reference, each spark pair has speed of 1000)
    public static int pylonPumpSpeed() {
        return CFG.pylonPumpSpeed();
    }
    /// <b>ManaPylonPump</b><br>how much ratio of mana will vanish during the transport
    public static double pylonPumpLossRatio() {
        return CFG.pylonPumpLossRatio();
    }
    /// <b>ManaPylonPump</b><br>displays extra particles when the pylon pump works
    public static boolean enablesPylonPumpFx() {
        return CFG.enablesPylonPumpFx();
    }
    /// <b>ManaPylonPump</b><br>how thick the pump particle line will be
    public static int pylonPumpFxStrength() {
        return CFG.pylonPumpFxStrength();
    }
    /// <b>Thermalily</b><br>no longer consumes lava during its cooldown
    public static boolean enablesPassiveThermalily() {
        return CFG.enablesPassiveThermalily();
    }
    /// <b>ManaEnchanter</b><br>may enchant books as enchantment table does
    public static boolean enchantBooks() {
        return CFG.enchantBooks();
    }
    /// <b>ManaEnchanter</b><br>item enchantments are valid sources too
    public static boolean treatEnchantedItemAsBook() {
        return CFG.treatEnchantedItemAsBook();
    }
    /// <b>ManaEnchanter</b><br>all available enchantments will be accepted, not onlt the first
    public static boolean acceptsAllInsideBook() {
        return CFG.acceptsAllInsideBook();
    }
    /// <b>ManaEnchanter</b><br>accepts conflicted enchantments (for example, infinity & mending)
    public static boolean ignoresCompatibleCheck() {
        return CFG.ignoresCompatibleCheck();
    }
    /// <b>ManaEnchanter</b><br>splits all enchantments as separate books if enchanter structure breaks after enchantment finished
    public static boolean doFinalEnchantmentSplit() {
        return CFG.doFinalEnchantmentSplit();
    }
    /// <b>GaiaGuardian</b><br>accepts damage higher than 32
    public static boolean removesDamageCap() {
        return CFG.removesDamageCap();
    }
    /// <b>GaiaGuardian</b><br>how many ticks to skip summoning stage when gaia guardian being attacked; 0 to disable this feature
    public static int decreasesInvulTimeByAttacking() {
        return CFG.decreasesInvulTimeByAttacking();
    }
    /// <b>GaiaGuardian</b><br>allows being attacked by & dropping loots for fake players (like deployer from `Create`)
    public static boolean allowsFakePlayer() {
        return CFG.allowsFakePlayer();
    }
    /// <b>Weapon</b><br>Terra Blade beams inherit damage (and fire aspect) from the sword
    public static boolean terraBladeBeamInheritsItemDamage() {
        return CFG.terraBladeBeamInheritsItemDamage();
    }
    /// <b>Weapon</b><br>Thundercaller's chain lightning inherits damage (and fire aspect) from the sword
    public static boolean chainLightningInheritsItemDamage() {
        return CFG.chainLightningInheritsItemDamage();
    }
    /// <b>Weapon</b><br>Starcaller's stars inherit damage (and fire aspect) from the sword
    public static boolean fallingStarInheritsItemDamage() {
        return CFG.fallingStarInheritsItemDamage();
    }
    /// <b>Weapon</b><br>Starcaller's stars don't hurt non-living entities like item frames
    public static boolean fallingStarSkipsNonLiving() {
        return CFG.fallingStarSkipsNonLiving();
    }
    /// <b>Misc</b><br>scales the output rate for Power Generator block
    public static int multiplyFEGeneratorOutput() {
        return CFG.multiplyFEGeneratorOutput();
    }
    public interface CommonAccess {
        String desc_showManaAmount = "displays the EVIL numbers";
        String desc_keepRedString = "always show red strings even without wand in hand; might have inpact on frame rate, use at your own risk";
        String desc_skipDandelifeonClearBoard = "no longer wipe all cells out when consuming";
        String desc_ruleLifeGameNew = "custom rule bitset for new cells";
        String desc_ruleLifeGameKeep = "custom rule bitset for keeping cells";
        String desc_skipEntropinnyumDuperCheck = "accepts TNTs from dupers";
        String desc_enableEntropinnyumUnderwater = "accepts TNTs inside liquids";
        String desc_skipNarslimmusNaturalCheck = "accepts ALL slimes";
        String desc_instantBurnFuel = "skips the burning process for fuel items";
        String desc_extraCapacityForFuel = "increases capacity when consuming large fuel";
        String desc_breaksMaxBurningTimeLimit = "no more 32000 tick burning limit";
        String desc_heatsBlazeBurner = "heats Blaze Burner blocks from Create";
        String desc_enchantedSoilEnablesSuperHeat = "applies hyper-heat for Blaze Burner blocks when on enchanted soil";
        String desc_enablesManaPylonPump = "makes Mana Pylons extract mana from the pool below and dump to wand-bound mana pool";
        String desc_pylonPumpMaxRange = "max distance between the pylon and the pool bound";
        String desc_pylonPumpSpeed = "how many mana points will be extracted each tick (for reference, each spark pair has speed of 1000)";
        String desc_pylonPumpLossRatio = "how much ratio of mana will vanish during the transport";
        String desc_enablesPylonPumpFx = "displays extra particles when the pylon pump works";
        String desc_pylonPumpFxStrength = "how thick the pump particle line will be";
        String desc_enablesPassiveThermalily = "no longer consumes lava during its cooldown";
        String desc_enchantBooks = "may enchant books as enchantment table does";
        String desc_treatEnchantedItemAsBook = "item enchantments are valid sources too";
        String desc_acceptsAllInsideBook = "all available enchantments will be accepted, not onlt the first";
        String desc_ignoresCompatibleCheck = "accepts conflicted enchantments (for example, infinity & mending)";
        String desc_doFinalEnchantmentSplit = "splits all enchantments as separate books if enchanter structure breaks after enchantment finished";
        String desc_removesDamageCap = "accepts damage higher than 32";
        String desc_decreasesInvulTimeByAttacking = "how many ticks to skip summoning stage when gaia guardian being attacked; 0 to disable this feature";
        String desc_allowsFakePlayer = "allows being attacked by & dropping loots for fake players (like deployer from `Create`)";
        String desc_terraBladeBeamInheritsItemDamage = "Terra Blade beams inherit damage (and fire aspect) from the sword";
        String desc_chainLightningInheritsItemDamage = "Thundercaller's chain lightning inherits damage (and fire aspect) from the sword";
        String desc_fallingStarInheritsItemDamage = "Starcaller's stars inherit damage (and fire aspect) from the sword";
        String desc_fallingStarSkipsNonLiving = "Starcaller's stars don't hurt non-living entities like item frames";
        String desc_multiplyFEGeneratorOutput = "scales the output rate for Power Generator block";
        boolean showManaAmount();
        boolean keepRedString();
        boolean skipDandelifeonClearBoard();
        int ruleLifeGameNew();
        int ruleLifeGameKeep();
        boolean skipEntropinnyumDuperCheck();
        boolean enableEntropinnyumUnderwater();
        boolean skipNarslimmusNaturalCheck();
        boolean instantBurnFuel();
        boolean extraCapacityForFuel();
        boolean breaksMaxBurningTimeLimit();
        boolean heatsBlazeBurner();
        boolean enchantedSoilEnablesSuperHeat();
        boolean enablesManaPylonPump();
        int pylonPumpMaxRange();
        int pylonPumpSpeed();
        double pylonPumpLossRatio();
        boolean enablesPylonPumpFx();
        int pylonPumpFxStrength();
        boolean enablesPassiveThermalily();
        boolean enchantBooks();
        boolean treatEnchantedItemAsBook();
        boolean acceptsAllInsideBook();
        boolean ignoresCompatibleCheck();
        boolean doFinalEnchantmentSplit();
        boolean removesDamageCap();
        int decreasesInvulTimeByAttacking();
        boolean allowsFakePlayer();
        boolean terraBladeBeamInheritsItemDamage();
        boolean chainLightningInheritsItemDamage();
        boolean fallingStarInheritsItemDamage();
        boolean fallingStarSkipsNonLiving();
        int multiplyFEGeneratorOutput();
    }
}
