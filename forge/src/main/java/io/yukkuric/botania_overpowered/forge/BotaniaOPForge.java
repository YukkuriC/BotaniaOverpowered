package io.yukkuric.botania_overpowered.forge;

import com.simibubi.create.content.processing.burner.BlazeBurnerBlockEntity;
import io.yukkuric.botania_overpowered.BotaniaOP;
import io.yukkuric.botania_overpowered.BotaniaOPConfig;
import io.yukkuric.botania_overpowered.forge.client.ManaTooltipForge;
import io.yukkuric.botania_overpowered.create.BurnerExoflameHandler;
import io.yukkuric.botania_overpowered.forge.mekanism.MekasuitManaItem;
import mekanism.common.item.gear.ItemMekaSuitArmor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.*;
import net.minecraftforge.fml.common.Mod;
import vazkii.botania.api.BotaniaForgeCapabilities;
import vazkii.botania.forge.CapabilityUtil;

import java.util.function.Supplier;

@Mod(BotaniaOP.MOD_ID)
public final class BotaniaOPForge extends BotaniaOP {
    final ResourceLocation MEKASUIT_MANA_ID = ResourceLocation.tryBuild(MOD_ID, "mekasuit_mana");
    final ResourceLocation CREATE_EXOFLAME_ID = ResourceLocation.tryBuild(MOD_ID, "create_exoflame_hook");
    static Supplier<Boolean> getShiftKeyDown = () -> false;

    public static boolean IsShiftKeyDown() {
        return getShiftKeyDown.get();
    }

    @Override
    public boolean isModLoaded(String id) {
        return ModList.get().isLoaded(id);
    }

    public BotaniaOPForge() {
        var evBus = MinecraftForge.EVENT_BUS;
        evBus.addListener(ManaTooltipForge::showMana);
        if (isModLoaded("mekanism")) {
            evBus.addGenericListener(ItemStack.class, (AttachCapabilitiesEvent<ItemStack> e) -> {
                var stack = e.getObject();
                if (!(stack.getItem() instanceof ItemMekaSuitArmor)) return;
                e.addCapability(MEKASUIT_MANA_ID, CapabilityUtil.makeProvider(BotaniaForgeCapabilities.MANA_ITEM, new MekasuitManaItem(stack)));
            });
        }
        if (isModLoaded("create")) {
            evBus.addGenericListener(BlockEntity.class, (AttachCapabilitiesEvent<BlockEntity> e) -> {
                var be = e.getObject();
                if (!(be instanceof BlazeBurnerBlockEntity burner) || !(BotaniaOPConfig.heatsBlazeBurner())) return;
                e.addCapability(CREATE_EXOFLAME_ID, CapabilityUtil.makeProvider(BotaniaForgeCapabilities.EXOFLAME_HEATABLE, new BurnerExoflameHandler(burner)));
            });
        }

        var ctx = ModLoadingContext.get();
        BotaniaOPConfigForge.register(ctx);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> BotaniaOPForge::InitClient);
    }

    static void InitClient() {
        getShiftKeyDown = Screen::hasShiftDown;
    }
}
