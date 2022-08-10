package com.glyceryl6.enhanced.mixin;

import com.glyceryl6.enhanced.enchantments.ModEnchantments;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnderMan.class)
public class MixinEnderMan extends Monster {

    public MixinEnderMan(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    //由于用事件的方法无法达到理想的效果，故改用Mixin，让末影人能够受到箭的伤害
    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    public void hurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (source.getDirectEntity() instanceof AbstractArrow arrow) {
            if (arrow.getOwner() instanceof Player player) {
                ItemStack itemStack = player.getItemInHand(player.getUsedItemHand());
                if (itemStack.getItem() instanceof ProjectileWeaponItem) {
                    if (EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.ENDERMAN_HURT.get(), itemStack) > 0) {
                        float f = (float)arrow.getDeltaMovement().length();
                        int i = Mth.ceil(Mth.clamp((double)f * arrow.baseDamage, 0.0D, 2.147483647E9D));
                        if (arrow.isCritArrow()) {
                            long j = arrow.level.random.nextInt(i / 2 + 2);
                            i = (int)Math.min(j + (long)i, 2147483647L);
                        }
                        if (arrow.isOnFire() && !this.isOnFire()) {
                            this.setSecondsOnFire(5);
                        }
                        super.hurt(DamageSource.arrow(arrow, arrow.getOwner()), i);
                        arrow.discard();
                        cir.setReturnValue(true);
                    }
                }
            }
        } else {
            source.getDirectEntity();
        }
    }

}
