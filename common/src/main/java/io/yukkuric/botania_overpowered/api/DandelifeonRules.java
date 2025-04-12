package io.yukkuric.botania_overpowered.api;

import io.yukkuric.botania_overpowered.BotaniaOPConfig;

import java.util.HashSet;
import java.util.List;
import java.util.function.Function;

public class DandelifeonRules {
    private static RuleFunc NEW, KEEP;
    private static RuleFunc NEW_CFG, KEEP_CFG;
    public static void keepAlive() {
    }

    // interface
    public static boolean CheckRuleNew(int count) {
        // init cache
        if (NEW_CFG == null) NEW_CFG = GenFromBitset(BotaniaOPConfig.ruleLifeGameNew());
        if (NEW == null) NEW = NEW_CFG;
        return NEW.apply(count);
    }
    public static boolean CheckRuleKeep(int count) {
        // init cache
        if (KEEP_CFG == null) KEEP_CFG = GenFromBitset(BotaniaOPConfig.ruleLifeGameKeep());
        if (KEEP == null) KEEP = KEEP_CFG;
        return KEEP.apply(count);
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
