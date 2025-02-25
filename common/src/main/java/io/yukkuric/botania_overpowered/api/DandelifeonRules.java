package io.yukkuric.botania_overpowered.api;

import io.yukkuric.botania_overpowered.BotaniaOPConfig;

import java.util.HashSet;
import java.util.List;
import java.util.function.Function;

public class DandelifeonRules {
    public static RuleFunc NEW, KEEP;
    public static void keepAlive() {
    }

    static {
        BotaniaOPConfig.bindConfigLoaded(cfg -> {
            if (NEW == null) NEW = GenFromBitset(cfg.ruleLifeGameNew());
            if (KEEP == null) KEEP = GenFromBitset(cfg.ruleLifeGameKeep());
        });
    }

    public static RuleFunc GenFromNums(Integer... targets) {
        var targetSet = new HashSet<>(List.of(targets));
        return targetSet::contains;
    }
    public static RuleFunc GenFromBitset(int bitset) {
        var targetSet = new HashSet<>();
        for (int i = 0, ptr = 1; i < 8; i++, ptr *= 2) {
            if ((ptr & bitset) == ptr) targetSet.add(i);
        }
        return targetSet::contains;
    }

    public interface RuleFunc extends Function<Integer, Boolean> {
    }

    public record LifeUpdate(int x, int z, int newLife, int oldLife) {
    }
}
