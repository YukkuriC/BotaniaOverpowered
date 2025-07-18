package io.yukkuric.botania_overpowered.mixin.item;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.yukkuric.botania_overpowered.BotaniaOPConfig;
import io.yukkuric.botania_overpowered.helpers.WeaponHelpers;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import vazkii.botania.common.entity.FallingStarEntity;
import vazkii.botania.common.entity.ThrowableCopyEntity;

@Mixin(FallingStarEntity.class)
public abstract class MixinStarcaller extends ThrowableCopyEntity {
    protected MixinStarcaller(EntityType<? extends ThrowableCopyEntity> entityType, Level level) {
        super(entityType, level);
    }

    @WrapOperation(method = "onHitEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    boolean starStrike(Entity instance, DamageSource source, float amount, Operation<Boolean> original) {
        var ownerRaw = getOwner();
        if (!BotaniaOPConfig.fallingStarInheritsItemDamage() || !(ownerRaw instanceof LivingEntity attacker))
            return original.call(instance, source, amount);
        return WeaponHelpers.wrapAttackTargetWithWeapon(attacker, instance, source, original, 0);
    }
}
