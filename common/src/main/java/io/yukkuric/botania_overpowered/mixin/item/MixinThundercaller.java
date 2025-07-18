package io.yukkuric.botania_overpowered.mixin.item;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.yukkuric.botania_overpowered.BotaniaOPConfig;
import io.yukkuric.botania_overpowered.helpers.WeaponHelpers;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vazkii.botania.common.item.equipment.tool.ThundercallerItem;

import java.util.Map;
import java.util.WeakHashMap;

@Mixin(ThundercallerItem.class)
public class MixinThundercaller {
    private static final Map<LivingEntity, Integer> jumpByAttacker = new WeakHashMap<>();

    @Inject(method = "hurtEnemy", remap = false, at = @At("HEAD"))
    void onStartEachAttack(ItemStack stack, LivingEntity entity, LivingEntity attacker, CallbackInfoReturnable<Boolean> cir) {
        jumpByAttacker.put(attacker, 0);
    }

    @WrapOperation(method = "hurtEnemy", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    boolean wrapLightning(LivingEntity instance, DamageSource source, float amount, Operation<Boolean> original,
                           @Local(ordinal = 1, argsOnly = true) LivingEntity attacker
    ) {
        if (!BotaniaOPConfig.chainLightningInheritsItemDamage())
            return original.call(instance, source, amount);
        var dmg = WeaponHelpers.getAttackDamage(attacker, instance);
        var jumped = jumpByAttacker.get(attacker);
        if (jumped == null) jumped = 0;
        jumpByAttacker.put(attacker, jumped + 1);
        return WeaponHelpers.wrapAttackTargetWithWeapon(attacker, instance, source, original, dmg - jumped);
    }
}
