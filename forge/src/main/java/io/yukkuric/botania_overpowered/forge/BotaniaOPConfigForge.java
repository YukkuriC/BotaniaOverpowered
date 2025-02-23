package io.yukkuric.botania_overpowered.forge;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

public class BotaniaOPConfigForge {
    public static BotaniaOPConfigForge INSTANCE;
    public static final String DESCRIP_MEKASUIT_RATIO = "How many mana points each FE point equals";

    public static double MekasuitConversionRatio() {
        return INSTANCE.cfgMekasuitConversionRatio.get();
    }

    public ForgeConfigSpec.DoubleValue cfgMekasuitConversionRatio;

    public BotaniaOPConfigForge(ForgeConfigSpec.Builder builder) {
        cfgMekasuitConversionRatio = builder.comment(DESCRIP_MEKASUIT_RATIO).defineInRange("MekasuitConversionRatio", 1, 0, 1e10);
        INSTANCE = this;
    }

    private static final Pair<BotaniaOPConfigForge, ForgeConfigSpec> CFG_REGISTRY;

    static {
        CFG_REGISTRY = new ForgeConfigSpec.Builder().configure(BotaniaOPConfigForge::new);
    }

    public static void register(ModLoadingContext ctx) {
        ctx.registerConfig(ModConfig.Type.COMMON, CFG_REGISTRY.getValue());
    }
}
