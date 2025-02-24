package io.yukkuric.botania_overpowered.mixin;

import io.yukkuric.botania_overpowered.BotaniaOPConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vazkii.botania.api.block_entity.GeneratingFlowerBlockEntity;
import vazkii.botania.common.block.flower.generating.DandelifeonBlockEntity;

import java.util.*;

import static io.yukkuric.botania_overpowered.api.DandelifeonRules.*;
import static io.yukkuric.botania_overpowered.helpers.ReflectionHelpers.WrappedCtor;
import static vazkii.botania.common.block.flower.generating.DandelifeonBlockEntity.MAX_MANA_GENERATIONS;

@Mixin(DandelifeonBlockEntity.class)
public abstract class MixinDandelifeon extends GeneratingFlowerBlockEntity {
    @Shadow
    private int radius;

    @Shadow
    abstract void setBlockForGeneration(BlockPos pos, int cell, int prevCell);

    public MixinDandelifeon(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    private static final String CLASSNAME_TABLE = "vazkii.botania.common.block.flower.generating.DandelifeonBlockEntity$CellTable";
    private static WrappedCtor<ITable> TABLE = new WrappedCtor<>(ITable.class, CLASSNAME_TABLE, int.class, DandelifeonBlockEntity.class);

    @Inject(method = "runSimulation", at = @At("HEAD"), cancellable = true, remap = false)
    private void replaceSimulation(CallbackInfo ci) {
        ci.cancel();
        try {
            replaceSimulationInner();
        } catch (Throwable e) {
            radius = -1;
            var server = level.getServer();
            var stackComp = Component.translatable("bot_op.error.dandelifeon", getBlockPos(), e.toString());
            for (var p : server.getPlayerList().getPlayers()) {
                p.sendSystemMessage(stackComp);
            }
        }
    }

    private void replaceSimulationInner() {
        if (radius <= 0) return;
        var table = TABLE.get(radius, this);
        List<LifeUpdate> changes = new ArrayList<>();

        int diameter = table.getDiameter();
        int allowDist = 1;
        boolean wipe = false;
        for (int i = 0; i < diameter; i++) {
            var xInRange = Math.abs(i - radius) <= allowDist;
            for (int j = 0; j < diameter; j++) {
                int oldLife = table.curStage(i, j);
                int adj = table.countScore(i, j);

                int newLife;
                if (oldLife == DandelifeonBlockEntity.Cell.DEAD && NEW.apply(adj))
                    newLife = table.nextStage(i, j);
                else if (DandelifeonBlockEntity.Cell.isLive(oldLife) && KEEP.apply(adj))
                    newLife = oldLife + 1;
                else newLife = DandelifeonBlockEntity.Cell.DEAD;

                if (xInRange && Math.abs(j - radius) <= allowDist &&
                        DandelifeonBlockEntity.Cell.isLive(newLife)) {
                    if (oldLife == 1) newLife = DandelifeonBlockEntity.Cell.DEAD;
                    else {
                        oldLife = newLife;
                        newLife = DandelifeonBlockEntity.Cell.CONSUME;
                        wipe = true;
                    }
                }

                if (newLife != oldLife) {
                    changes.add(new LifeUpdate(i, j, newLife, oldLife));
                }
            }
        }
        if (wipe && BotaniaOPConfig.skipDandelifeonClearBoard()) wipe = false;

        for (var change : changes) {
            BlockPos pos_ = table.getCenter().offset(-radius + change.x(), 0, -radius + change.z());
            int newLife = change.newLife();
            if (wipe) newLife = DandelifeonBlockEntity.Cell.DEAD;
            setBlockForGeneration(pos_, Math.min(newLife, MAX_MANA_GENERATIONS), change.oldLife());
        }
    }

    @Mixin(targets = {CLASSNAME_TABLE})
    public interface ITable {
        @Accessor("center")
        BlockPos getCenter();

        @Accessor("diameter")
        int getDiameter();

        @Invoker("getAdjCells")
        int countScore(int x, int z);

        @Invoker("getSpawnCellGeneration")
        int nextStage(int x, int z);

        @Invoker("at")
        int curStage(int x, int z);
    }
}
