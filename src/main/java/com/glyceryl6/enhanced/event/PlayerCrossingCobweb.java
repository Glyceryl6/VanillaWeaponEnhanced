package com.glyceryl6.enhanced.event;

import com.glyceryl6.enhanced.enchantments.ModEnchantments;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.WebBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@SuppressWarnings("deprecation")
public class PlayerCrossingCobweb {
    
    @SubscribeEvent
    public void playerStuckInCobweb(PlayerEvent event) {
        Player player = event.getPlayer();
        if (player != null && EnchantmentHelper.getEnchantmentLevel(ModEnchantments.COBWEB_CROSSING.get(), player) > 0 && !player.isSpectator()) {
            AABB aabb = player.getBoundingBox();
            BlockPos blockPos = new BlockPos(aabb.minX + 0.001D, aabb.minY + 0.001D, aabb.minZ + 0.001D);
            BlockPos blockPos1 = new BlockPos(aabb.maxX - 0.001D, aabb.maxY - 0.001D, aabb.maxZ - 0.001D);
            if (player.level.hasChunksAt(blockPos, blockPos1)) {
                BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
                for(int i = blockPos.getX(); i <= blockPos1.getX(); ++i) {
                    for(int j = blockPos.getY(); j <= blockPos1.getY(); ++j) {
                        for(int k = blockPos.getZ(); k <= blockPos1.getZ(); ++k) {
                            mutableBlockPos.set(i, j, k);
                            BlockState blockState = player.level.getBlockState(mutableBlockPos);
                            float ySpeed = player.isCrouching() ? -1.0F : (player.jumping ? 1.0F : player.yya);
                            Vec3 vec3 = new Vec3(player.xxa, ySpeed, player.zza);
                            try {
                                if (blockState.getBlock() instanceof WebBlock) {
                                    player.moveRelative(player.isSprinting() ? 0.6F : 0.4F, vec3);
                                }
                            } catch (Throwable throwable) {
                                CrashReport crashReport = CrashReport.forThrowable(throwable, "Colliding entity with block");
                                CrashReportCategory crashReportCategory = crashReport.addCategory("Block being collided with");
                                CrashReportCategory.populateBlockDetails(crashReportCategory, player.level, mutableBlockPos, blockState);
                                throw new ReportedException(crashReport);
                            }
                        }
                    }
                }
            }
        }
    }
    
}