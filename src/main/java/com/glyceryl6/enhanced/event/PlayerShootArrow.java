package com.glyceryl6.enhanced.event;

import com.glyceryl6.enhanced.enchantments.ModEnchantments;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

@SuppressWarnings("unused")
public class PlayerShootArrow {

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onArrowCreated(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof AbstractArrow arrow) {
            if (arrow.getOwner() instanceof Player player) {
                ItemStack itemStack = player.getItemInHand(player.getUsedItemHand());
                if (itemStack.getItem() instanceof ProjectileWeaponItem) {
                    Enchantment enchantment = ModEnchantments.ANTIGRAVITY_ARROW.get();
                    if (EnchantmentHelper.getItemEnchantmentLevel(enchantment, itemStack) > 0) {
                        arrow.setNoGravity(true);
                    }
                }
            }
        }
    }

    //本来想用事件来实现的，但奈何难以达到效果，所以放弃了~
    public void onArrowImpactEnderMan(EntityEvent event) {
        if (event.getEntity() instanceof AbstractArrow arrow) {
            if (arrow.getOwner() instanceof Player player) {
                ItemStack itemStack = player.getItemInHand(player.getUsedItemHand());
                if (itemStack.getItem() instanceof BowItem) {
                    if (EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.ENDERMAN_HURT.get(), itemStack) > 0) {
                        float f = (float)arrow.getDeltaMovement().length();
                        int i = Mth.ceil(Mth.clamp((double)f * arrow.baseDamage, 0.0D, 2.147483647E9D));
                        if (arrow.isCritArrow()) {
                            long j = arrow.level.random.nextInt(i / 2 + 2);
                            i = (int)Math.min(j + (long)i, 2147483647L);
                        }
                        AABB aabb = arrow.getBoundingBox().inflate(1.0F, 1.0F, 1.0F);
                        List<EnderMan> enderManList = arrow.level.getEntitiesOfClass(EnderMan.class, aabb);
                        if (!enderManList.isEmpty()) {
                            for (EnderMan enderMan : enderManList) {
                                if (enderMan.hurt(DamageSource.playerAttack(player), i)) {
                                    if (arrow.isOnFire() && !enderMan.isOnFire()) {
                                        enderMan.setSecondsOnFire(5);
                                    }
                                    arrow.discard();
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    //这是第二个尝试用事件来实现的方法，但是依然没有达到理想的效果，所以又放弃了~
    public void onEnderManHurt(LivingDamageEvent event) {
        LivingEntity livingEntity = event.getEntityLiving();
        DamageSource source = event.getSource();
        Entity entity = source.getDirectEntity();
        if (entity instanceof AbstractArrow arrow) {
            if (arrow.getOwner() instanceof Player player) {
                ItemStack itemStack = player.getItemInHand(player.getUsedItemHand());
                if (EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.ENDERMAN_HURT.get(), itemStack) > 0) {
                    if (livingEntity instanceof EnderMan enderMan) {
                        float f = (float)arrow.getDeltaMovement().length();
                        int i = Mth.ceil(Mth.clamp((double)f * arrow.baseDamage, 0.0D, 2.147483647E9D));
                        if (arrow.isCritArrow()) {
                            long j = arrow.level.random.nextInt(i / 2 + 2);
                            i = (int)Math.min(j + (long)i, 2147483647L);
                        }
                        enderMan.hurt(DamageSource.playerAttack(player), i);
                        if (arrow.isOnFire() && enderMan.getRemainingFireTicks() <= 0) {
                            enderMan.setRemainingFireTicks(5);
                        }
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

}