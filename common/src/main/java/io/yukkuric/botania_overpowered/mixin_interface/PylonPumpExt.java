package io.yukkuric.botania_overpowered.mixin_interface;

import net.minecraft.world.phys.Vec3;
import vazkii.botania.api.mana.ManaPool;

public interface PylonPumpExt {
    ManaPool getBoundTarget();
    Vec3 getBoundCenter();
}
