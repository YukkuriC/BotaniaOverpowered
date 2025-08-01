package io.yukkuric.botania_overpowered.fabric;

import io.github.fablabsmc.fablabs.api.fiber.v1.builder.ConfigTreeBuilder;
import io.github.fablabsmc.fablabs.api.fiber.v1.exception.ValueDeserializationException;
import io.github.fablabsmc.fablabs.api.fiber.v1.serialization.FiberSerialization;
import io.github.fablabsmc.fablabs.api.fiber.v1.serialization.JanksonValueSerializer;
import io.github.fablabsmc.fablabs.api.fiber.v1.tree.ConfigTree;
import io.github.fablabsmc.fablabs.api.fiber.v1.tree.PropertyMirror;
import io.yukkuric.botania_overpowered.BotaniaOP;
import io.yukkuric.botania_overpowered.BotaniaOPConfig;

import java.io.*;
import java.nio.file.*;

import static io.github.fablabsmc.fablabs.api.fiber.v1.schema.type.derived.ConfigTypes.*;

public class BotaniaOPConfigFabric implements BotaniaOPConfig.CommonAccess {
    public static final BotaniaOPConfigFabric INSTANCE = new BotaniaOPConfigFabric();
    public final PropertyMirror<Boolean> cfg_showManaAmount = PropertyMirror.create(BOOLEAN);
    public final PropertyMirror<Boolean> cfg_keepRedString = PropertyMirror.create(BOOLEAN);
    public final PropertyMirror<Boolean> cfg_skipDandelifeonClearBoard = PropertyMirror.create(BOOLEAN);
    public final PropertyMirror<Integer> cfg_ruleLifeGameNew = PropertyMirror.create(INTEGER);
    public final PropertyMirror<Integer> cfg_ruleLifeGameKeep = PropertyMirror.create(INTEGER);
    public final PropertyMirror<Boolean> cfg_skipEntropinnyumDuperCheck = PropertyMirror.create(BOOLEAN);
    public final PropertyMirror<Boolean> cfg_enableEntropinnyumUnderwater = PropertyMirror.create(BOOLEAN);
    public final PropertyMirror<Boolean> cfg_skipNarslimmusNaturalCheck = PropertyMirror.create(BOOLEAN);
    public final PropertyMirror<Boolean> cfg_instantBurnFuel = PropertyMirror.create(BOOLEAN);
    public final PropertyMirror<Boolean> cfg_extraCapacityForFuel = PropertyMirror.create(BOOLEAN);
    public final PropertyMirror<Boolean> cfg_breaksMaxBurningTimeLimit = PropertyMirror.create(BOOLEAN);
    public final PropertyMirror<Boolean> cfg_heatsBlazeBurner = PropertyMirror.create(BOOLEAN);
    public final PropertyMirror<Boolean> cfg_enchantedSoilEnablesSuperHeat = PropertyMirror.create(BOOLEAN);
    public final PropertyMirror<Boolean> cfg_enablesManaPylonPump = PropertyMirror.create(BOOLEAN);
    public final PropertyMirror<Integer> cfg_pylonPumpMaxRange = PropertyMirror.create(INTEGER);
    public final PropertyMirror<Integer> cfg_pylonPumpSpeed = PropertyMirror.create(INTEGER);
    public final PropertyMirror<Double> cfg_pylonPumpLossRatio = PropertyMirror.create(DOUBLE);
    public final PropertyMirror<Boolean> cfg_enablesPylonPumpFx = PropertyMirror.create(BOOLEAN);
    public final PropertyMirror<Integer> cfg_pylonPumpFxStrength = PropertyMirror.create(INTEGER);
    public final PropertyMirror<Boolean> cfg_enablesPassiveThermalily = PropertyMirror.create(BOOLEAN);
    public final PropertyMirror<Boolean> cfg_enchantBooks = PropertyMirror.create(BOOLEAN);
    public final PropertyMirror<Boolean> cfg_treatEnchantedItemAsBook = PropertyMirror.create(BOOLEAN);
    public final PropertyMirror<Boolean> cfg_acceptsAllInsideBook = PropertyMirror.create(BOOLEAN);
    public final PropertyMirror<Boolean> cfg_ignoresCompatibleCheck = PropertyMirror.create(BOOLEAN);
    public final PropertyMirror<Boolean> cfg_doFinalEnchantmentSplit = PropertyMirror.create(BOOLEAN);
    public final PropertyMirror<Boolean> cfg_removesDamageCap = PropertyMirror.create(BOOLEAN);
    public final PropertyMirror<Integer> cfg_decreasesInvulTimeByAttacking = PropertyMirror.create(INTEGER);
    public final PropertyMirror<Boolean> cfg_allowsFakePlayer = PropertyMirror.create(BOOLEAN);
    public final PropertyMirror<Boolean> cfg_terraBladeBeamInheritsItemDamage = PropertyMirror.create(BOOLEAN);
    public final PropertyMirror<Boolean> cfg_chainLightningInheritsItemDamage = PropertyMirror.create(BOOLEAN);
    public final PropertyMirror<Boolean> cfg_fallingStarInheritsItemDamage = PropertyMirror.create(BOOLEAN);
    public final PropertyMirror<Boolean> cfg_fallingStarSkipsNonLiving = PropertyMirror.create(BOOLEAN);
    public final PropertyMirror<Integer> cfg_multiplyFEGeneratorOutput = PropertyMirror.create(INTEGER);
    public boolean showManaAmount() {
        return cfg_showManaAmount.getValue();
    }
    public boolean keepRedString() {
        return cfg_keepRedString.getValue();
    }
    public boolean skipDandelifeonClearBoard() {
        return cfg_skipDandelifeonClearBoard.getValue();
    }
    public int ruleLifeGameNew() {
        return cfg_ruleLifeGameNew.getValue();
    }
    public int ruleLifeGameKeep() {
        return cfg_ruleLifeGameKeep.getValue();
    }
    public boolean skipEntropinnyumDuperCheck() {
        return cfg_skipEntropinnyumDuperCheck.getValue();
    }
    public boolean enableEntropinnyumUnderwater() {
        return cfg_enableEntropinnyumUnderwater.getValue();
    }
    public boolean skipNarslimmusNaturalCheck() {
        return cfg_skipNarslimmusNaturalCheck.getValue();
    }
    public boolean instantBurnFuel() {
        return cfg_instantBurnFuel.getValue();
    }
    public boolean extraCapacityForFuel() {
        return cfg_extraCapacityForFuel.getValue();
    }
    public boolean breaksMaxBurningTimeLimit() {
        return cfg_breaksMaxBurningTimeLimit.getValue();
    }
    public boolean heatsBlazeBurner() {
        return cfg_heatsBlazeBurner.getValue();
    }
    public boolean enchantedSoilEnablesSuperHeat() {
        return cfg_enchantedSoilEnablesSuperHeat.getValue();
    }
    public boolean enablesManaPylonPump() {
        return cfg_enablesManaPylonPump.getValue();
    }
    public int pylonPumpMaxRange() {
        return cfg_pylonPumpMaxRange.getValue();
    }
    public int pylonPumpSpeed() {
        return cfg_pylonPumpSpeed.getValue();
    }
    public double pylonPumpLossRatio() {
        return cfg_pylonPumpLossRatio.getValue();
    }
    public boolean enablesPylonPumpFx() {
        return cfg_enablesPylonPumpFx.getValue();
    }
    public int pylonPumpFxStrength() {
        return cfg_pylonPumpFxStrength.getValue();
    }
    public boolean enablesPassiveThermalily() {
        return cfg_enablesPassiveThermalily.getValue();
    }
    public boolean enchantBooks() {
        return cfg_enchantBooks.getValue();
    }
    public boolean treatEnchantedItemAsBook() {
        return cfg_treatEnchantedItemAsBook.getValue();
    }
    public boolean acceptsAllInsideBook() {
        return cfg_acceptsAllInsideBook.getValue();
    }
    public boolean ignoresCompatibleCheck() {
        return cfg_ignoresCompatibleCheck.getValue();
    }
    public boolean doFinalEnchantmentSplit() {
        return cfg_doFinalEnchantmentSplit.getValue();
    }
    public boolean removesDamageCap() {
        return cfg_removesDamageCap.getValue();
    }
    public int decreasesInvulTimeByAttacking() {
        return cfg_decreasesInvulTimeByAttacking.getValue();
    }
    public boolean allowsFakePlayer() {
        return cfg_allowsFakePlayer.getValue();
    }
    public boolean terraBladeBeamInheritsItemDamage() {
        return cfg_terraBladeBeamInheritsItemDamage.getValue();
    }
    public boolean chainLightningInheritsItemDamage() {
        return cfg_chainLightningInheritsItemDamage.getValue();
    }
    public boolean fallingStarInheritsItemDamage() {
        return cfg_fallingStarInheritsItemDamage.getValue();
    }
    public boolean fallingStarSkipsNonLiving() {
        return cfg_fallingStarSkipsNonLiving.getValue();
    }
    public int multiplyFEGeneratorOutput() {
        return cfg_multiplyFEGeneratorOutput.getValue();
    }

    public ConfigTree build(ConfigTreeBuilder builder) {
        builder.fork("Display")
                .beginValue("showManaAmount", BOOLEAN, true)
                .withComment(desc_showManaAmount).finishValue(cfg_showManaAmount::mirror)
                .beginValue("keepRedString", BOOLEAN, false)
                .withComment(desc_keepRedString).finishValue(cfg_keepRedString::mirror)
                .finishBranch();
        builder.fork("Dandelifeon")
                .beginValue("skipDandelifeonClearBoard", BOOLEAN, true)
                .withComment(desc_skipDandelifeonClearBoard).finishValue(cfg_skipDandelifeonClearBoard::mirror)
                .beginValue("ruleLifeGameNew", INTEGER, 8)
                .withComment(desc_ruleLifeGameNew).finishValue(cfg_ruleLifeGameNew::mirror)
                .beginValue("ruleLifeGameKeep", INTEGER, 12)
                .withComment(desc_ruleLifeGameKeep).finishValue(cfg_ruleLifeGameKeep::mirror)
                .finishBranch();
        builder.fork("Entropinnyum")
                .beginValue("skipEntropinnyumDuperCheck", BOOLEAN, true)
                .withComment(desc_skipEntropinnyumDuperCheck).finishValue(cfg_skipEntropinnyumDuperCheck::mirror)
                .beginValue("enableEntropinnyumUnderwater", BOOLEAN, true)
                .withComment(desc_enableEntropinnyumUnderwater).finishValue(cfg_enableEntropinnyumUnderwater::mirror)
                .finishBranch();
        builder.fork("Narslimmus")
                .beginValue("skipNarslimmusNaturalCheck", BOOLEAN, true)
                .withComment(desc_skipNarslimmusNaturalCheck).finishValue(cfg_skipNarslimmusNaturalCheck::mirror)
                .finishBranch();
        builder.fork("Endoflame")
                .beginValue("instantBurnFuel", BOOLEAN, true)
                .withComment(desc_instantBurnFuel).finishValue(cfg_instantBurnFuel::mirror)
                .beginValue("extraCapacityForFuel", BOOLEAN, true)
                .withComment(desc_extraCapacityForFuel).finishValue(cfg_extraCapacityForFuel::mirror)
                .beginValue("breaksMaxBurningTimeLimit", BOOLEAN, true)
                .withComment(desc_breaksMaxBurningTimeLimit).finishValue(cfg_breaksMaxBurningTimeLimit::mirror)
                .finishBranch();
        builder.fork("Exoflame")
                .beginValue("heatsBlazeBurner", BOOLEAN, true)
                .withComment(desc_heatsBlazeBurner).finishValue(cfg_heatsBlazeBurner::mirror)
                .beginValue("enchantedSoilEnablesSuperHeat", BOOLEAN, true)
                .withComment(desc_enchantedSoilEnablesSuperHeat).finishValue(cfg_enchantedSoilEnablesSuperHeat::mirror)
                .finishBranch();
        builder.fork("ManaPylonPump")
                .beginValue("enablesManaPylonPump", BOOLEAN, true)
                .withComment(desc_enablesManaPylonPump).finishValue(cfg_enablesManaPylonPump::mirror)
                .beginValue("pylonPumpMaxRange", INTEGER, 64)
                .withComment(desc_pylonPumpMaxRange).finishValue(cfg_pylonPumpMaxRange::mirror)
                .beginValue("pylonPumpSpeed", INTEGER, 10000)
                .withComment(desc_pylonPumpSpeed).finishValue(cfg_pylonPumpSpeed::mirror)
                .beginValue("pylonPumpLossRatio", DOUBLE, 0.1)
                .withComment(desc_pylonPumpLossRatio).finishValue(cfg_pylonPumpLossRatio::mirror)
                .beginValue("enablesPylonPumpFx", BOOLEAN, true)
                .withComment(desc_enablesPylonPumpFx).finishValue(cfg_enablesPylonPumpFx::mirror)
                .beginValue("pylonPumpFxStrength", INTEGER, 3)
                .withComment(desc_pylonPumpFxStrength).finishValue(cfg_pylonPumpFxStrength::mirror)
                .finishBranch();
        builder.fork("Thermalily")
                .beginValue("enablesPassiveThermalily", BOOLEAN, true)
                .withComment(desc_enablesPassiveThermalily).finishValue(cfg_enablesPassiveThermalily::mirror)
                .finishBranch();
        builder.fork("ManaEnchanter")
                .beginValue("enchantBooks", BOOLEAN, true)
                .withComment(desc_enchantBooks).finishValue(cfg_enchantBooks::mirror)
                .beginValue("treatEnchantedItemAsBook", BOOLEAN, true)
                .withComment(desc_treatEnchantedItemAsBook).finishValue(cfg_treatEnchantedItemAsBook::mirror)
                .beginValue("acceptsAllInsideBook", BOOLEAN, true)
                .withComment(desc_acceptsAllInsideBook).finishValue(cfg_acceptsAllInsideBook::mirror)
                .beginValue("ignoresCompatibleCheck", BOOLEAN, true)
                .withComment(desc_ignoresCompatibleCheck).finishValue(cfg_ignoresCompatibleCheck::mirror)
                .beginValue("doFinalEnchantmentSplit", BOOLEAN, true)
                .withComment(desc_doFinalEnchantmentSplit).finishValue(cfg_doFinalEnchantmentSplit::mirror)
                .finishBranch();
        builder.fork("GaiaGuardian")
                .beginValue("removesDamageCap", BOOLEAN, true)
                .withComment(desc_removesDamageCap).finishValue(cfg_removesDamageCap::mirror)
                .beginValue("decreasesInvulTimeByAttacking", INTEGER, 10)
                .withComment(desc_decreasesInvulTimeByAttacking).finishValue(cfg_decreasesInvulTimeByAttacking::mirror)
                .beginValue("allowsFakePlayer", BOOLEAN, true)
                .withComment(desc_allowsFakePlayer).finishValue(cfg_allowsFakePlayer::mirror)
                .finishBranch();
        builder.fork("Weapon")
                .beginValue("terraBladeBeamInheritsItemDamage", BOOLEAN, true)
                .withComment(desc_terraBladeBeamInheritsItemDamage).finishValue(cfg_terraBladeBeamInheritsItemDamage::mirror)
                .beginValue("chainLightningInheritsItemDamage", BOOLEAN, true)
                .withComment(desc_chainLightningInheritsItemDamage).finishValue(cfg_chainLightningInheritsItemDamage::mirror)
                .beginValue("fallingStarInheritsItemDamage", BOOLEAN, true)
                .withComment(desc_fallingStarInheritsItemDamage).finishValue(cfg_fallingStarInheritsItemDamage::mirror)
                .beginValue("fallingStarSkipsNonLiving", BOOLEAN, true)
                .withComment(desc_fallingStarSkipsNonLiving).finishValue(cfg_fallingStarSkipsNonLiving::mirror)
                .finishBranch();
        builder.fork("Misc")
                .beginValue("multiplyFEGeneratorOutput", INTEGER, 100)
                .withComment(desc_multiplyFEGeneratorOutput).finishValue(cfg_multiplyFEGeneratorOutput::mirror)
                .finishBranch();
        return builder.build();
    }

    public static void setup() {
        // make dir
        try {
            Files.createDirectory(Paths.get("config"));
        } catch (FileAlreadyExistsException ignored) {
        } catch (IOException e) {
            BotaniaOP.LOGGER.warn("Failed to make config dir", e);
        }

        var serializer = new JanksonValueSerializer(false);
        var config = INSTANCE.build(ConfigTree.builder());
        // setup config ritual
        // from https://github.com/VazkiiMods/Botania/blob/1.20.x/Fabric/src/main/java/vazkii/botania/fabric/FiberBotaniaConfig.java
        var cfgPath = Paths.get("config", BotaniaOP.MOD_ID + "-common.json5");
        {
            var cfgFile = cfgPath.toFile();
            // read file
            try {
                if (cfgFile.exists()) {
                    try (InputStream s = new BufferedInputStream(Files.newInputStream(cfgPath, StandardOpenOption.READ, StandardOpenOption.CREATE))) {
                        FiberSerialization.deserialize(config, s, serializer);
                    }
                }
            } catch (IOException | ValueDeserializationException e) {
                BotaniaOP.LOGGER.error("Error loading config from {}", cfgPath, e);
                try {
                    cfgPath.toFile().delete();
                } catch (Throwable ee) {
                    BotaniaOP.LOGGER.error("Error removing invalid config {}", cfgPath, ee);
                }
            }
            // write back for format update
            try (OutputStream s = new BufferedOutputStream(Files.newOutputStream(cfgPath, StandardOpenOption.WRITE, StandardOpenOption.CREATE))) {
                FiberSerialization.serialize(config, s, serializer);
            } catch (IOException e) {
                BotaniaOP.LOGGER.error("Error writing back config {}", cfgPath, e);
            }
        }
        BotaniaOPConfig.bindConfig(INSTANCE);
    }
}
