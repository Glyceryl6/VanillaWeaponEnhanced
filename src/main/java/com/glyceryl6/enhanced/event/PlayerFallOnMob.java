package com.glyceryl6.enhanced.event;

import com.glyceryl6.enhanced.enchantments.ModEnchantments;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class PlayerFallOnMob {

    @SubscribeEvent
    public void onPlayerFallingOnMob(LivingFallEvent event) {
        LivingEntity livingEntity = event.getEntityLiving();
        if (livingEntity instanceof Player player) {
            AABB aabb = player.getBoundingBox().inflate(0.2F);
            List<Entity> list = player.level.getEntities(player, aabb, EntitySelector.pushableBy(player));
            int i = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.FALLING_INJURE_MOB.get(), player);
            float fallDistance = event.getDistance();
            float multiplier = event.getDamageMultiplier();
            if (i > 0 && fallDistance >= 2.0F && !list.isEmpty()) {
                for (Entity entity : list) {
                    if (entity instanceof LivingEntity && !(entity instanceof Player)) {
                        entity.causeFallDamage(fallDistance, (multiplier + i) / 2, DamageSource.ANVIL);
                        this.doPush(entity, player);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerDamage(LivingDamageEvent event) {
        LivingEntity livingEntity = event.getEntityLiving();
        DamageSource source = event.getSource();
        if (livingEntity instanceof Player player && source == DamageSource.FALL) {
            List<Entity> list = player.level.getEntities(player, player.getBoundingBox(), EntitySelector.pushableBy(player));
            int i = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.FALLING_INJURE_MOB.get(), player);
            int j = player.level.getGameRules().getInt(GameRules.RULE_MAX_ENTITY_CRAMMING);
            if (i > 0 && !list.isEmpty()) {
                if (list.size() >= j) {
                    event.setCanceled(true);
                } else {
                    event.setAmount(event.getAmount() / (i * list.size()));
                }
            }
        }
    }

    protected void doPush(Entity entity, LivingEntity livingEntity) {
        entity.push(livingEntity);
    }

}
