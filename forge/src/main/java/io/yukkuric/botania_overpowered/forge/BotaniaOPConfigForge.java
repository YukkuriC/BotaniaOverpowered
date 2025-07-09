package io.yukkuric.botania_overpowered.forge;

import io.yukkuric.botania_overpowered.BotaniaOPConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

public class BotaniaOPConfigForge implements BotaniaOPConfig.CommonAccess {
    public static BotaniaOPConfigForge INSTANCE;
    private static final String desc_MekasuitConversionRatio = "how many mana points each FE point equals";
    public static double MekasuitConversionRatio() {
        return INSTANCE.cfg_MekasuitConversionRatio.get();
    }
    private static final String desc_MekasuitHelmetAcceptsAncientWill = "... as what Terrasteel Helmet does";
    public static boolean MekasuitHelmetAcceptsAncientWill() {
        return INSTANCE.cfg_MekasuitHelmetAcceptsAncientWill.get();
    }
    public boolean showManaAmount() {
        return cfg_showManaAmount.get();
    }
    public boolean skipDandelifeonClearBoard() {
        return cfg_skipDandelifeonClearBoard.get();
    }
    public int ruleLifeGameNew() {
        return cfg_ruleLifeGameNew.get();
    }
    public int ruleLifeGameKeep() {
        return cfg_ruleLifeGameKeep.get();
    }
    public boolean skipEntropinnyumDuperCheck() {
        return cfg_skipEntropinnyumDuperCheck.get();
    }
    public boolean enableEntropinnyumUnderwater() {
        return cfg_enableEntropinnyumUnderwater.get();
    }
    public boolean skipNarslimmusNaturalCheck() {
        return cfg_skipNarslimmusNaturalCheck.get();
    }
    public boolean instantBurnFuel() {
        return cfg_instantBurnFuel.get();
    }
    public boolean extraCapacityForFuel() {
        return cfg_extraCapacityForFuel.get();
    }
    public boolean breaksMaxBurningTimeLimit() {
        return cfg_breaksMaxBurningTimeLimit.get();
    }
    public boolean heatsBlazeBurner() {
        return cfg_heatsBlazeBurner.get();
    }
    public boolean enchantedSoilEnablesSuperHeat() {
        return cfg_enchantedSoilEnablesSuperHeat.get();
    }
    public boolean enablesManaPylonPump() {
        return cfg_enablesManaPylonPump.get();
    }
    public int pylonPumpMaxRange() {
        return cfg_pylonPumpMaxRange.get();
    }
    public int pylonPumpSpeed() {
        return cfg_pylonPumpSpeed.get();
    }
    public double pylonPumpLossRatio() {
        return cfg_pylonPumpLossRatio.get();
    }
    public boolean enablesPylonPumpFx() {
        return cfg_enablesPylonPumpFx.get();
    }
    public int pylonPumpFxStrength() {
        return cfg_pylonPumpFxStrength.get();
    }
    public boolean enablesPassiveThermalily() {
        return cfg_enablesPassiveThermalily.get();
    }
    public boolean enchantBooks() {
        return cfg_enchantBooks.get();
    }
    public boolean treatEnchantedItemAsBook() {
        return cfg_treatEnchantedItemAsBook.get();
    }
    public boolean acceptsAllInsideBook() {
        return cfg_acceptsAllInsideBook.get();
    }
    public boolean ignoresCompatibleCheck() {
        return cfg_ignoresCompatibleCheck.get();
    }
    public boolean doFinalEnchantmentSplit() {
        return cfg_doFinalEnchantmentSplit.get();
    }
    public boolean removesDamageCap() {
        return cfg_removesDamageCap.get();
    }
    public int decreasesInvulTimeByAttacking() {
        return cfg_decreasesInvulTimeByAttacking.get();
    }
    public boolean allowsFakePlayer() {
        return cfg_allowsFakePlayer.get();
    }
    public boolean terraBladeBeamInheritsItemDamage() {
        return cfg_terraBladeBeamInheritsItemDamage.get();
    }
    public int multiplyFEGeneratorOutput() {
        return cfg_multiplyFEGeneratorOutput.get();
    }
    public ForgeConfigSpec.BooleanValue
            cfg_showManaAmount,
            cfg_skipDandelifeonClearBoard,
            cfg_skipEntropinnyumDuperCheck,
            cfg_enableEntropinnyumUnderwater,
            cfg_skipNarslimmusNaturalCheck,
            cfg_instantBurnFuel,
            cfg_extraCapacityForFuel,
            cfg_breaksMaxBurningTimeLimit,
            cfg_heatsBlazeBurner,
            cfg_enchantedSoilEnablesSuperHeat,
            cfg_enablesManaPylonPump,
            cfg_enablesPylonPumpFx,
            cfg_enablesPassiveThermalily,
            cfg_enchantBooks,
            cfg_treatEnchantedItemAsBook,
            cfg_acceptsAllInsideBook,
            cfg_ignoresCompatibleCheck,
            cfg_doFinalEnchantmentSplit,
            cfg_removesDamageCap,
            cfg_allowsFakePlayer,
            cfg_terraBladeBeamInheritsItemDamage,
            cfg_MekasuitHelmetAcceptsAncientWill;
    public ForgeConfigSpec.IntValue
            cfg_ruleLifeGameNew,
            cfg_ruleLifeGameKeep,
            cfg_pylonPumpMaxRange,
            cfg_pylonPumpSpeed,
            cfg_pylonPumpFxStrength,
            cfg_decreasesInvulTimeByAttacking,
            cfg_multiplyFEGeneratorOutput;
    public ForgeConfigSpec.DoubleValue
            cfg_pylonPumpLossRatio,
            cfg_MekasuitConversionRatio;

    public BotaniaOPConfigForge(ForgeConfigSpec.Builder builder) {
        builder.push("Display");
        cfg_showManaAmount = builder.comment(desc_showManaAmount).define("showManaAmount", true);
        builder.pop();

        builder.push("Dandelifeon");
        cfg_skipDandelifeonClearBoard = builder.comment(desc_skipDandelifeonClearBoard).define("skipDandelifeonClearBoard", true);
        cfg_ruleLifeGameNew = builder.comment(desc_ruleLifeGameNew).defineInRange("ruleLifeGameNew", 8, 0, 255);
        cfg_ruleLifeGameKeep = builder.comment(desc_ruleLifeGameKeep).defineInRange("ruleLifeGameKeep", 12, 0, 255);
        builder.pop();

        builder.push("Entropinnyum");
        cfg_skipEntropinnyumDuperCheck = builder.comment(desc_skipEntropinnyumDuperCheck).define("skipEntropinnyumDuperCheck", true);
        cfg_enableEntropinnyumUnderwater = builder.comment(desc_enableEntropinnyumUnderwater).define("enableEntropinnyumUnderwater", true);
        builder.pop();

        builder.push("Narslimmus");
        cfg_skipNarslimmusNaturalCheck = builder.comment(desc_skipNarslimmusNaturalCheck).define("skipNarslimmusNaturalCheck", true);
        builder.pop();

        builder.push("Endoflame");
        cfg_instantBurnFuel = builder.comment(desc_instantBurnFuel).define("instantBurnFuel", true);
        cfg_extraCapacityForFuel = builder.comment(desc_extraCapacityForFuel).define("extraCapacityForFuel", true);
        cfg_breaksMaxBurningTimeLimit = builder.comment(desc_breaksMaxBurningTimeLimit).define("breaksMaxBurningTimeLimit", true);
        builder.pop();

        builder.push("Exoflame");
        cfg_heatsBlazeBurner = builder.comment(desc_heatsBlazeBurner).define("heatsBlazeBurner", true);
        cfg_enchantedSoilEnablesSuperHeat = builder.comment(desc_enchantedSoilEnablesSuperHeat).define("enchantedSoilEnablesSuperHeat", true);
        builder.pop();

        builder.push("ManaPylonPump");
        cfg_enablesManaPylonPump = builder.comment(desc_enablesManaPylonPump).define("enablesManaPylonPump", true);
        cfg_pylonPumpMaxRange = builder.comment(desc_pylonPumpMaxRange).defineInRange("pylonPumpMaxRange", 64, 1, 256);
        cfg_pylonPumpSpeed = builder.comment(desc_pylonPumpSpeed).defineInRange("pylonPumpSpeed", 10000, 1, Integer.MAX_VALUE);
        cfg_pylonPumpLossRatio = builder.comment(desc_pylonPumpLossRatio).defineInRange("pylonPumpLossRatio", 0.1, 0, 1);
        cfg_enablesPylonPumpFx = builder.comment(desc_enablesPylonPumpFx).define("enablesPylonPumpFx", true);
        cfg_pylonPumpFxStrength = builder.comment(desc_pylonPumpFxStrength).defineInRange("pylonPumpFxStrength", 3, 1, 10);
        builder.pop();

        builder.push("Thermalily");
        cfg_enablesPassiveThermalily = builder.comment(desc_enablesPassiveThermalily).define("enablesPassiveThermalily", true);
        builder.pop();

        builder.push("ManaEnchanter");
        cfg_enchantBooks = builder.comment(desc_enchantBooks).define("enchantBooks", true);
        cfg_treatEnchantedItemAsBook = builder.comment(desc_treatEnchantedItemAsBook).define("treatEnchantedItemAsBook", true);
        cfg_acceptsAllInsideBook = builder.comment(desc_acceptsAllInsideBook).define("acceptsAllInsideBook", true);
        cfg_ignoresCompatibleCheck = builder.comment(desc_ignoresCompatibleCheck).define("ignoresCompatibleCheck", true);
        cfg_doFinalEnchantmentSplit = builder.comment(desc_doFinalEnchantmentSplit).define("doFinalEnchantmentSplit", true);
        builder.pop();

        builder.push("GaiaGuardian");
        cfg_removesDamageCap = builder.comment(desc_removesDamageCap).define("removesDamageCap", true);
        cfg_decreasesInvulTimeByAttacking = builder.comment(desc_decreasesInvulTimeByAttacking).defineInRange("decreasesInvulTimeByAttacking", 10, 0, 900);
        cfg_allowsFakePlayer = builder.comment(desc_allowsFakePlayer).define("allowsFakePlayer", true);
        builder.pop();

        builder.push("Misc");
        cfg_terraBladeBeamInheritsItemDamage = builder.comment(desc_terraBladeBeamInheritsItemDamage).define("terraBladeBeamInheritsItemDamage", true);
        cfg_multiplyFEGeneratorOutput = builder.comment(desc_multiplyFEGeneratorOutput).defineInRange("multiplyFEGeneratorOutput", 100, 1, 114514);
        builder.pop();

        builder.push("Interop.Mekanism");
        cfg_MekasuitConversionRatio = builder.comment(desc_MekasuitConversionRatio).defineInRange("MekasuitConversionRatio", 1, 0, 1e10);
        cfg_MekasuitHelmetAcceptsAncientWill = builder.comment(desc_MekasuitHelmetAcceptsAncientWill).define("MekasuitHelmetAcceptsAncientWill", true);
        builder.pop();

        INSTANCE = this;
        BotaniaOPConfig.bindConfig(this);
    }

    private static final Pair<BotaniaOPConfigForge, ForgeConfigSpec> CFG_REGISTRY;

    static {
        CFG_REGISTRY = new ForgeConfigSpec.Builder().configure(BotaniaOPConfigForge::new);
    }

    public static void register(ModLoadingContext ctx) {
        ctx.registerConfig(ModConfig.Type.COMMON, CFG_REGISTRY.getValue());
    }
}
