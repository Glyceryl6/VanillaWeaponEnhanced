package com.glyceryl6.enhanced.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class UniversalDigger extends Enchantment {

    public UniversalDigger(Rarity rarity, EquipmentSlot... slots) {
        super(rarity, EnchantmentCategory.DIGGER, slots);
    }

    @Override
    public int getMinCost(int i) {
        return 1 + 10 * (i - 1);
    }

    @Override
    public int getMaxCost(int i) {
        return super.getMinCost(i) + 50;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

}