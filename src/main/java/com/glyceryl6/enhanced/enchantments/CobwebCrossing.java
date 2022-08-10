package com.glyceryl6.enhanced.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class CobwebCrossing extends Enchantment {

    public CobwebCrossing(Rarity rarity, EquipmentSlot... slots) {
        super(rarity, EnchantmentCategory.ARMOR_FEET, slots);
    }

    public int getMinCost(int i) {
        return 20;
    }

    public int getMaxCost(int i) {
        return 50;
    }

    public int getMaxLevel() {
        return 1;
    }

}