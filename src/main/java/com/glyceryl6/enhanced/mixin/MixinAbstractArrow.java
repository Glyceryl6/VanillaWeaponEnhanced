package com.glyceryl6.enhanced.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrow.class)
public abstract class MixinAbstractArrow extends Projectile {

    @Shadow public int life;
    @Shadow protected abstract void tickDespawn();

    protected MixinAbstractArrow(EntityType<? extends Projectile> type, Level level) {
        super(type, level);
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/Projectile;tick()V", shift = At.Shift.AFTER))
    public void tick(CallbackInfo ci) {
        if (!this.level.isClientSide) {
            this.tickDespawn();
            if (this.isNoGravity() && this.life > 400) {
                this.setNoGravity(false);
            }
        }
    }

}