package io.yukkuric.botania_overpowered.api;

import java.util.HashSet;
import java.util.List;
import java.util.function.Function;

public class DandelifeonRules {
    public static RuleFunc NEW, KEEP;

    static {
        NEW = GenFromNums(3);
        KEEP = GenFromNums(2, 3);
    }

    public static RuleFunc GenFromNums(Integer... targets) {
        var targetSet = new HashSet<>(List.of(targets));
        return targetSet::contains;
    }

    public interface RuleFunc extends Function<Integer, Boolean> {
    }

    public record LifeUpdate(int x, int z, int newLife, int oldLife) {
    }
}
