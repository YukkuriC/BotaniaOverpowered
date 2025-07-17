package io.yukkuric.botania_overpowered.mixin.item;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.yukkuric.botania_overpowered.BotaniaOPConfig;
import io.yukkuric.botania_overpowered.helpers.WeaponHelpers;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import vazkii.botania.common.item.equipment.tool.terrasteel.TerraBladeItem;

@Mixin(TerraBladeItem.class)
public class MixinTerraBlade {
    @WrapOperation(method = "updateBurst", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    boolean burstWithBlade(LivingEntity instance, DamageSource source, float amount, Operation<Boolean> original, @Local ThrowableProjectile entity) {
        var throwerRaw = entity.getOwner();
        if (!BotaniaOPConfig.terraBladeBeamInheritsItemDamage() || !(throwerRaw instanceof LivingEntity thrower))
            return original.call(instance, source, amount);
        return WeaponHelpers.wrapAttackTargetWithWeapon(thrower, instance, source, original, 0);
    }
}
