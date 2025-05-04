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
    public boolean heatsBlazeBurner() {
        return cfg_heatsBlazeBurner.get();
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
    public ForgeConfigSpec.BooleanValue cfg_showManaAmount, cfg_skipDandelifeonClearBoard, cfg_skipEntropinnyumDuperCheck, cfg_enableEntropinnyumUnderwater, cfg_skipNarslimmusNaturalCheck, cfg_heatsBlazeBurner, cfg_enablesManaPylonPump, cfg_enablesPylonPumpFx;
    public ForgeConfigSpec.IntValue cfg_ruleLifeGameNew, cfg_ruleLifeGameKeep, cfg_pylonPumpMaxRange, cfg_pylonPumpSpeed, cfg_pylonPumpFxStrength;
    public ForgeConfigSpec.DoubleValue cfg_pylonPumpLossRatio, cfg_MekasuitConversionRatio;

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

        builder.push("Exoflame");
        cfg_heatsBlazeBurner = builder.comment(desc_heatsBlazeBurner).define("heatsBlazeBurner", true);
        builder.pop();

        builder.push("ManaPylonPump");
        cfg_enablesManaPylonPump = builder.comment(desc_enablesManaPylonPump).define("enablesManaPylonPump", true);
        cfg_pylonPumpMaxRange = builder.comment(desc_pylonPumpMaxRange).defineInRange("pylonPumpMaxRange", 64, 1, 256);
        cfg_pylonPumpSpeed = builder.comment(desc_pylonPumpSpeed).defineInRange("pylonPumpSpeed", 10000, 1, Integer.MAX_VALUE);
        cfg_pylonPumpLossRatio = builder.comment(desc_pylonPumpLossRatio).defineInRange("pylonPumpLossRatio", 0.1, 0, 1);
        cfg_enablesPylonPumpFx = builder.comment(desc_enablesPylonPumpFx).define("enablesPylonPumpFx", true);
        cfg_pylonPumpFxStrength = builder.comment(desc_pylonPumpFxStrength).defineInRange("pylonPumpFxStrength", 3, 1, 10);
        builder.pop();

        builder.push("Interop");
        cfg_MekasuitConversionRatio = builder.comment(desc_MekasuitConversionRatio).defineInRange("MekasuitConversionRatio", 1, 0, 1e10);
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
