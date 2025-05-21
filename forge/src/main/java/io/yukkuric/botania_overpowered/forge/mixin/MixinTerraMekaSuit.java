package io.yukkuric.botania_overpowered.forge.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.yukkuric.botania_overpowered.forge.BotaniaOPConfigForge;
import mekanism.client.render.armor.MekaSuitArmor;
import mekanism.common.item.gear.ItemMekaSuitArmor;
import mekanism.common.lib.Color;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vazkii.botania.api.item.AncientWillContainer;
import vazkii.botania.client.core.handler.MiscellaneousModels;
import vazkii.botania.common.item.BotaniaItems;
import vazkii.botania.common.item.equipment.armor.terrasteel.TerrasteelHelmItem;

import java.util.List;
import java.util.Locale;

@Mixin(ItemMekaSuitArmor.class)
public class MixinTerraMekaSuit extends ArmorItem implements AncientWillContainer {
    public MixinTerraMekaSuit(ArmorMaterial material, EquipmentSlot slot, Properties properties) {
        super(material, slot, properties);
    }
    // recipe
    @Override
    public void addAncientWill(ItemStack stack, AncientWillType willType) {
        if (!BotaniaOPConfigForge.MekasuitHelmetAcceptsAncientWill()) return;
        ((TerrasteelHelmItem) BotaniaItems.terrasteelHelm).addAncientWill(stack, willType);
    }
    @Override
    public boolean hasAncientWill(ItemStack stack, AncientWillType willType) {
        if (!BotaniaOPConfigForge.MekasuitHelmetAcceptsAncientWill() || slot != EquipmentSlot.HEAD) return true;
        return ((TerrasteelHelmItem) BotaniaItems.terrasteelHelm).hasAncientWill(stack, willType);
    }

    // tooltip
    @Inject(method = "m_7373_", at = @At("RETURN"), remap = false)
    void addTooltipWhenShift(@NotNull ItemStack stack, Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag, CallbackInfo ci) {
        if (!BotaniaOPConfigForge.MekasuitHelmetAcceptsAncientWill() || slot != EquipmentSlot.HEAD || !Screen.hasShiftDown())
            return;
        for (var sub : AncientWillType.values()) {
            if (this.hasAncientWill(stack, sub)) {
                tooltip.add(Component.translatable("botania.armorset.will_" + sub.name().toLowerCase(Locale.ROOT) + ".desc").withStyle(ChatFormatting.GRAY));
            }
        }
    }

    // attack
    @Mixin(TerrasteelHelmItem.class)
    public static class TerraHelmHooks {
        private static final EquipmentSlot[] armorSlots = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
        private static boolean hasTerraMekaSuitSet(Player player) {
            if (!BotaniaOPConfigForge.MekasuitHelmetAcceptsAncientWill()) return false;
            for (var slot : armorSlots)
                if (!(player.getItemBySlot(slot).getItem() instanceof ItemMekaSuitArmor)) return false;
            return true;
        }

        @Inject(method = "hasTerraArmorSet", at = @At("HEAD"), remap = false, cancellable = true)
        private static void MekasuitIsTerrasteel(Player player, CallbackInfoReturnable<Boolean> cir) {
            if (hasTerraMekaSuitSet(player)) cir.setReturnValue(true);
        }

        @WrapOperation(method = {"getCritDamageMult", "onEntityAttacked"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getItem()Lnet/minecraft/world/item/Item;"))
        private static Item wrapInstanceCheck(ItemStack instance, Operation<Item> original) {
            return BotaniaItems.terrasteelHelm;
        }
    }

    // render
    // https://github.com/VazkiiMods/Botania/blob/1.20.x/Xplat/src/main/java/vazkii/botania/client/render/entity/TerrasteelHelmetLayer.java
    @Mixin(MekaSuitArmor.class)
    public static class AncientWillRendererExt {
        @Shadow(remap = false)
        @Final
        private EquipmentSlot type;
        @Inject(method = "renderMekaSuit", at = @At("RETURN"), remap = false)
        void renderWillModel(HumanoidModel<? extends LivingEntity> baseModel, @NotNull PoseStack matrix, @NotNull MultiBufferSource renderer, int light, int overlayLight, Color color, float partialTicks, boolean hasEffect, LivingEntity entity, CallbackInfo ci) {
            if (type != EquipmentSlot.HEAD || !BotaniaOPConfigForge.MekasuitHelmetAcceptsAncientWill()) return;
            var helm = entity.getItemBySlot(EquipmentSlot.HEAD);
            if (!TerrasteelHelmItem.hasAnyWill(helm)) return;
            matrix.pushPose();
            baseModel.head.translateAndRotate(matrix);
            matrix.translate(-0.2, -0.15, -0.3);
            matrix.scale(0.4F, -0.4F, -0.4F);
            BakedModel model = MiscellaneousModels.INSTANCE.terrasteelHelmWillModel;
            VertexConsumer buffer = renderer.getBuffer(Sheets.cutoutBlockSheet());
            Minecraft.getInstance().getBlockRenderer().getModelRenderer().renderModel(matrix.last(), buffer, (BlockState) null, model, 1.0F, 1.0F, 1.0F, light, OverlayTexture.NO_OVERLAY);
            matrix.popPose();
        }
    }
}
