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
    @Override
    public boolean skipNarslimmusNaturalCheck() {
        return cfg_skipNarslimmusNaturalCheck.get();
    }

    public ForgeConfigSpec.DoubleValue cfgMekasuitConversionRatio;
    public ForgeConfigSpec.BooleanValue cfg_showManaAmount, cfg_skipDandelifeonClearBoard, cfg_skipEntropinnyumDuperCheck, cfg_enableEntropinnyumUnderwater, cfg_skipNarslimmusNaturalCheck;

    public BotaniaOPConfigForge(ForgeConfigSpec.Builder builder) {
        builder.push("display");
        cfg_showManaAmount = builder.comment(desc_showManaAmount)
                .define("showManaAmount", true);
        builder.pop();

        builder.push("features");

        builder.push("Dandelifeon");
        cfg_skipDandelifeonClearBoard = builder.comment(desc_skipDandelifeonClearBoard)
                .define("skipDandelifeonClearBoard", true);
        builder.pop();

        builder.push("Entropinnyum");
        cfg_skipEntropinnyumDuperCheck = builder.comment(desc_skipEntropinnyumDuperCheck)
                .define("skipEntropinnyumDuperCheck", true);
        cfg_enableEntropinnyumUnderwater = builder.comment(desc_enableEntropinnyumUnderwater)
                .define("enableEntropinnyumUnderwater", true);
        builder.pop();

        builder.push("Narslimmus");
        cfg_skipNarslimmusNaturalCheck = builder.comment(desc_skipNarslimmusNaturalCheck)
                .define("skipNarslimmusNaturalCheck", true);
        builder.pop();

        builder.push("interop");
        cfgMekasuitConversionRatio = builder.comment(DESCRIP_MEKASUIT_RATIO).defineInRange("MekasuitConversionRatio", 1, 0, 1e10);
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
