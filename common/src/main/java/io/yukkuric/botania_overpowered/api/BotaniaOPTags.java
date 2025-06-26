package io.yukkuric.botania_overpowered.api;

import io.yukkuric.botania_overpowered.BotaniaOP;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class BotaniaOPTags {
    static <T> TagKey<T> build(ResourceKey<Registry<T>> target, String path) {
        return TagKey.create(target, ResourceLocation.tryBuild(BotaniaOP.MOD_ID, path));
    }

    public static final TagKey<Block> BLAZEBURNER_TARGET = build(Registries.BLOCK, "blazeburner_target");
}
