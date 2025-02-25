package io.yukkuric.botania_overpowered.fabric;

import io.github.fablabsmc.fablabs.api.fiber.v1.builder.ConfigTreeBuilder;
import io.github.fablabsmc.fablabs.api.fiber.v1.exception.ValueDeserializationException;
import io.github.fablabsmc.fablabs.api.fiber.v1.serialization.FiberSerialization;
import io.github.fablabsmc.fablabs.api.fiber.v1.serialization.JanksonValueSerializer;
import io.github.fablabsmc.fablabs.api.fiber.v1.tree.ConfigTree;
import io.github.fablabsmc.fablabs.api.fiber.v1.tree.PropertyMirror;
import io.yukkuric.botania_overpowered.BotaniaOP;
import io.yukkuric.botania_overpowered.BotaniaOPConfig;
import vazkii.botania.api.BotaniaAPI;

import java.io.*;
import java.nio.file.*;

import static io.github.fablabsmc.fablabs.api.fiber.v1.schema.type.derived.ConfigTypes.BOOLEAN;

public class BotaniaOPConfigFabric implements BotaniaOPConfig.CommonAccess {
    public static final BotaniaOPConfigFabric INSTANCE = new BotaniaOPConfigFabric();

    public final PropertyMirror<Boolean> cfg_showManaAmount = PropertyMirror.create(BOOLEAN);
    public final PropertyMirror<Boolean> cfg_skipDandelifeonClearBoard = PropertyMirror.create(BOOLEAN);
    public final PropertyMirror<Boolean> cfg_skipEntropinnyumDuperCheck = PropertyMirror.create(BOOLEAN);
    public final PropertyMirror<Boolean> cfg_enableEntropinnyumUnderwater = PropertyMirror.create(BOOLEAN);
    public final PropertyMirror<Boolean> cfg_skipNarslimmusNaturalCheck = PropertyMirror.create(BOOLEAN);

    @Override
    public boolean showManaAmount() {
        return cfg_showManaAmount.getValue();
    }
    @Override
    public boolean skipDandelifeonClearBoard() {
        return cfg_skipDandelifeonClearBoard.getValue();
    }
    @Override
    public boolean skipEntropinnyumDuperCheck() {
        return cfg_skipEntropinnyumDuperCheck.getValue();
    }
    @Override
    public boolean enableEntropinnyumUnderwater() {
        return cfg_enableEntropinnyumUnderwater.getValue();
    }
    @Override
    public boolean skipNarslimmusNaturalCheck() {
        return cfg_skipNarslimmusNaturalCheck.getValue();
    }

    public ConfigTree build(ConfigTreeBuilder builder) {
        builder.fork("display")
                .beginValue("showManaAmount", BOOLEAN, true)
                .withComment(desc_showManaAmount).finishValue(cfg_showManaAmount::mirror)
                .finishBranch();

        builder.fork("features")

                .fork("Dandelifeon")
                .beginValue("skipDandelifeonClearBoard", BOOLEAN, true)
                .withComment(desc_skipDandelifeonClearBoard).finishValue(cfg_skipDandelifeonClearBoard::mirror)
                .finishBranch()

                .fork("Entropinnyum")
                .beginValue("skipEntropinnyumDuperCheck", BOOLEAN, true)
                .withComment(desc_skipEntropinnyumDuperCheck).finishValue(cfg_skipEntropinnyumDuperCheck::mirror)
                .beginValue("enableEntropinnyumUnderwater", BOOLEAN, true)
                .withComment(desc_enableEntropinnyumUnderwater).finishValue(cfg_enableEntropinnyumUnderwater::mirror)
                .finishBranch()

                .fork("Narslimmus")
                .beginValue("skipNarslimmusNaturalCheck", BOOLEAN, true)
                .withComment(desc_skipNarslimmusNaturalCheck).finishValue(cfg_skipNarslimmusNaturalCheck::mirror)
                .finishBranch()

                .finishBranch();
        return builder.build();
    }

    public static void setup() {
        // make dir
        try {
            Files.createDirectory(Paths.get("config"));
        } catch (FileAlreadyExistsException ignored) {
        } catch (IOException e) {
            BotaniaAPI.LOGGER.warn("Failed to make config dir", e);
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
                BotaniaAPI.LOGGER.error("Error loading config from {} {}", cfgPath, e);
                try {
                    cfgPath.toFile().delete();
                } catch (Throwable ee) {
                    BotaniaAPI.LOGGER.error("Error removing invalid config {} {}", cfgPath, ee);
                }
            }
            // write back for format update
            try (OutputStream s = new BufferedOutputStream(Files.newOutputStream(cfgPath, StandardOpenOption.WRITE, StandardOpenOption.CREATE))) {
                FiberSerialization.serialize(config, s, serializer);
            } catch (IOException e) {
                BotaniaAPI.LOGGER.error("Error writing back config", e);
            }
        }
        BotaniaOPConfig.bindConfig(INSTANCE);
    }
}
