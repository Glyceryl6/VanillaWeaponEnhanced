package com.glyceryl6.enhanced;

import com.glyceryl6.enhanced.enchantments.ModEnchantments;
import com.glyceryl6.enhanced.event.PlayerCrossingCobweb;
import com.glyceryl6.enhanced.event.PlayerDestroyBlock;
import com.glyceryl6.enhanced.event.PlayerFallOnMob;
import com.glyceryl6.enhanced.event.PlayerShootArrow;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(VanillaEquipmentEnhanced.MOD_ID)
public class VanillaEquipmentEnhanced {

    public static final String MOD_ID = "vanilla_equipment_enhanced";
    IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

    public VanillaEquipmentEnhanced() {
        ModEnchantments.ENCHANTMENTS.register(eventBus);
        MinecraftForge.EVENT_BUS.register(new PlayerFallOnMob());
        MinecraftForge.EVENT_BUS.register(new PlayerShootArrow());
        MinecraftForge.EVENT_BUS.register(new PlayerDestroyBlock());
        MinecraftForge.EVENT_BUS.register(new PlayerCrossingCobweb());
        MinecraftForge.EVENT_BUS.register(this);
    }

}