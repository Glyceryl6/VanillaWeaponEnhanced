package com.glyceryl6.enhanced.enchantments;

import com.glyceryl6.enhanced.VanillaEquipmentEnhanced;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEnchantments {

    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, VanillaEquipmentEnhanced.MOD_ID);

    public static final RegistryObject<Enchantment> FALLING_INJURE_MOB = ENCHANTMENTS.register("falling_injure_mob", () -> new FallingInjureMob(Enchantment.Rarity.UNCOMMON, EquipmentSlot.FEET));
    public static final RegistryObject<Enchantment> ANTIGRAVITY_ARROW = ENCHANTMENTS.register("antigravity_arrow", () -> new AntigravityArrow(Enchantment.Rarity.UNCOMMON, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> UNIVERSAL_DIGGER = ENCHANTMENTS.register("universal_digger", () -> new UniversalDigger(Enchantment.Rarity.UNCOMMON, EquipmentSlot.MAINHAND));
    public static final RegistryObject<Enchantment> COBWEB_CROSSING = ENCHANTMENTS.register("cobweb_crossing", () -> new CobwebCrossing(Enchantment.Rarity.UNCOMMON, EquipmentSlot.FEET));
    public static final RegistryObject<Enchantment> ENDERMAN_HURT = ENCHANTMENTS.register("enderman_hurt", () -> new EnderManHurt(Enchantment.Rarity.UNCOMMON, EquipmentSlot.MAINHAND));

}