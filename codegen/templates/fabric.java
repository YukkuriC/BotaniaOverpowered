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

{% set data_common = filter_val.side.common -%}
public class BotaniaOPConfigFabric implements BotaniaOPConfig.CommonAccess {
    public static final BotaniaOPConfigFabric INSTANCE = new BotaniaOPConfigFabric();
    {%- for line in data_common %}
    public final PropertyMirror<{{to_java_type(line.type)}}> cfg_{{line.name}} = PropertyMirror.create({{to_java_type(line.type)|upper}});
    {%- endfor %}
    {%- for line in data_common %}
    public {{line.type}} {{line.name}}() {
        return cfg_{{line.name}}.getValue();
    }
    {%- endfor %}

    public ConfigTree build(ConfigTreeBuilder builder) {
        {%- for grp,lines in group_val(data_common,'category') %}
        builder.fork("{{grp}}")
        {%- for line in lines %}
                .beginValue("{{line.name}}", {{to_java_type(line.type)|upper}}, {{line.default.split(',').0}})
                .withComment(desc_{{line.name}}).finishValue(cfg_{{line.name}}::mirror)
        {%- endfor %}
                .finishBranch();
        {%- endfor %}
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
