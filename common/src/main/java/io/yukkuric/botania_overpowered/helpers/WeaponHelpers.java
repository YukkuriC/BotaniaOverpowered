package io.yukkuric.botania_overpowered.helpers;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class WeaponHelpers {
    public static float getAttackDamage(LivingEntity attacker, LivingEntity target) {
        var baseDamage = attacker.getAttributeValue(Attributes.ATTACK_DAMAGE);
        var dmgBonus = EnchantmentHelper.getDamageBonus(attacker.getMainHandItem(), target.getMobType());
        return (float) baseDamage + dmgBonus;
    }

    public static boolean wrapAttackTargetWithWeapon(LivingEntity attacker, LivingEntity target, DamageSource source, Operation<Boolean> original, float overrideDamage) {
        // base damage
        if (overrideDamage <= 0) overrideDamage = getAttackDamage(attacker, target);

        // fire aspect
        var tryFire = false;
        if (EnchantmentHelper.getFireAspect(attacker) > 0 && !target.isOnFire()) {
            target.setSecondsOnFire(1);
            tryFire = true;
        }
        var ret = original.call(target, source, overrideDamage);
        if (!ret && tryFire) {
            target.clearFire();
        }

        return ret;
    }
}
