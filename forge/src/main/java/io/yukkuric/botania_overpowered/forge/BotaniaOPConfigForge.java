package io.yukkuric.botania_overpowered.forge;

import io.yukkuric.botania_overpowered.BotaniaOPConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

public class BotaniaOPConfigForge implements BotaniaOPConfig.CommonAccess {
    public static BotaniaOPConfigForge INSTANCE;
    public static final String DESCRIP_MEKASUIT_RATIO = "How many mana points each FE point equals";

    public static double MekasuitConversionRatio() {
        return INSTANCE.cfgMekasuitConversionRatio.get();
    }

    @Override
    public boolean showManaAmount() {
        return cfg_showManaAmount.get();
    }
    @Override
    public boolean skipDandelifeonClearBoard() {
        return cfg_skipDandelifeonClearBoard.get();
    }
    @Override
    public boolean skipEntropinnyumDuperCheck() {
        return cfg_skipEntropinnyumDuperCheck.get();
    }
    @Override
    public boolean enableEntropinnyumUnderwater() {
        return cfg_enableEntropinnyumUnderwater.get();
    }

    public ForgeConfigSpec.DoubleValue cfgMekasuitConversionRatio;
    public ForgeConfigSpec.BooleanValue cfg_showManaAmount, cfg_skipDandelifeonClearBoard, cfg_skipEntropinnyumDuperCheck, cfg_enableEntropinnyumUnderwater;

    public BotaniaOPConfigForge(ForgeConfigSpec.Builder builder) {
        cfg_showManaAmount = builder.define("showManaAmount", true);
        cfg_skipDandelifeonClearBoard = builder.define("skipDandelifeonClearBoard", true);
        cfg_skipEntropinnyumDuperCheck = builder.define("skipEntropinnyumDuperCheck", true);
        cfg_enableEntropinnyumUnderwater = builder.define("enableEntropinnyumUnderwater", true);

        cfgMekasuitConversionRatio = builder.comment(DESCRIP_MEKASUIT_RATIO).defineInRange("MekasuitConversionRatio", 1, 0, 1e10);
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
