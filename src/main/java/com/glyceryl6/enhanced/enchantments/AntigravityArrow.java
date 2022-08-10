package com.glyceryl6.enhanced.enchantments;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class AntigravityArrow extends Enchantment {

    public AntigravityArrow(Rarity rarity, EquipmentSlot... slots) {
        super(rarity, EnchantmentCategory.BOW, slots);
    }

    @Override
    public int getMinCost(int i) {
        return 20;
    }

    @Override
    public int getMaxCost(int i) {
        return 50;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof ProjectileWeaponItem;
    }

}