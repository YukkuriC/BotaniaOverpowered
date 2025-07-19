package io.yukkuric.botania_overpowered.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.yukkuric.botania_overpowered.BotaniaOPConfig;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vazkii.botania.common.entity.GaiaGuardianEntity;

import java.util.List;

@Mixin(GaiaGuardianEntity.class)
public abstract class MixinGaiaGuardian extends Mob {
    @Shadow(remap = false)
    private int mobSpawnTicks;
    @Shadow(remap = false)
    @Final
    private static int MOB_SPAWN_TICKS, MOB_SPAWN_WAVE_TIME;
    @Shadow(remap = false)
    public abstract List<Player> getPlayersAround();
    @Shadow(remap = false)
    protected abstract void spawnMobs(List<Player> players);
    @Shadow(remap = false)
    public abstract void setInvulTime(int time);
    protected MixinGaiaGuardian(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    boolean isDying() {
        return (getHealth() / getMaxHealth()) < 0.2f;
    }

    @WrapOperation(method = {"hurt", "actuallyHurt", "getDamageAfterArmorAbsorb"}, at = @At(value = "INVOKE", target = "Ljava/lang/Math;min(FF)F"))
    float noDamageCap(float num_32, float dmg, Operation<Float> original) {
        if (BotaniaOPConfig.removesDamageCap()) {
            // extra check to prevent insta-kill and no rewards
            if (!isDying()) return original.call(getHealth() - 1, dmg);
            return dmg;
        }
        return original.call(num_32, dmg);
    }

    @Inject(method = "hurt", at = @At("RETURN"))
    void decreasesHordeTime(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        // only when damage cancelled & started summoning
        if (cir.getReturnValue() || mobSpawnTicks >= MOB_SPAWN_TICKS) return;
        if (getLevel().isClientSide) return; // fixes #8
        var ticksSkip = BotaniaOPConfig.decreasesInvulTimeByAttacking();
        if (ticksSkip <= 0) return;

        // decrease spawn time
        var oldSpawnTicks = mobSpawnTicks;
        mobSpawnTicks -= ticksSkip;

        // extra spawns
        int oldWaveCount = oldSpawnTicks / MOB_SPAWN_WAVE_TIME, newWaveCount = mobSpawnTicks / MOB_SPAWN_WAVE_TIME;
        if (oldWaveCount <= newWaveCount) return;
        var players = this.getPlayersAround();
        while (oldWaveCount > newWaveCount) {
            oldWaveCount--;
            spawnMobs(players);
        }
    }

    @WrapOperation(method = "spawn", at = @At(value = "INVOKE", target = "Lvazkii/botania/common/helper/PlayerHelper;isTruePlayer(Lnet/minecraft/world/entity/Entity;)Z"))
    private static boolean noFakePlayerCheckStatic(Entity player, Operation<Boolean> original) {
        // however, deployers can't crouch
        if (BotaniaOPConfig.allowsFakePlayer()) return true;
        return original.call(player);
    }
    @WrapOperation(method = {"hurt", "die", "dropFromLootTable"}, at = @At(value = "INVOKE", target = "Lvazkii/botania/common/helper/PlayerHelper;isTruePlayer(Lnet/minecraft/world/entity/Entity;)Z"))
    private boolean noFakePlayerCheck(Entity player, Operation<Boolean> original) {
        return noFakePlayerCheckStatic(player, original);
    }
}
