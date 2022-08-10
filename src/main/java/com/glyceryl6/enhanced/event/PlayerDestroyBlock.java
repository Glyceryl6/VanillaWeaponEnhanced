package com.glyceryl6.enhanced.event;

import com.glyceryl6.enhanced.enchantments.ModEnchantments;
import com.glyceryl6.enhanced.tags.ModBlockTags;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PlayerDestroyBlock {

    @SubscribeEvent
    public void playerMineBlock(TickEvent.WorldTickEvent event) {
        Minecraft mc = Minecraft.getInstance();
        HitResult hitResult = mc.hitResult;
        Player player = mc.player;
        Level level = event.world;
        boolean leftClick = mc.screen == null && mc.options.keyAttack.isDown() && mc.mouseHandler.isMouseGrabbed();
        if (player != null && !level.isClientSide && mc.gameMode != null && hitResult != null && hitResult.getType() == HitResult.Type.BLOCK && leftClick) {
            BlockHitResult blockHitResult = (BlockHitResult)hitResult;
            BlockPos blockPos = blockHitResult.getBlockPos();
            BlockState blockState = level.getBlockState(blockPos);
            ItemStack itemStack = player.getItemInHand(player.getUsedItemHand());
            if (blockState.is(ModBlockTags.UNBREAKABLE_BLOCK) && itemStack.getItem() instanceof PickaxeItem) {
                int universalDiggerLevel = EnchantmentHelper.getItemEnchantmentLevel(ModEnchantments.UNIVERSAL_DIGGER.get(), itemStack);
                float blockEfficiency = EnchantmentHelper.getBlockEfficiency(player);
                if (universalDiggerLevel > 0) {
                    float progressTime = 80.0F / (universalDiggerLevel + blockEfficiency * 0.5F);
                    if (mc.gameMode.destroyTicks > progressTime) {
                        ItemStack itemStack1 = new ItemStack(blockState.getBlock());
                        level.destroyBlock(blockPos, true, player);
                        mc.gameMode.destroyTicks = 0.0F;
                        itemStack.hurtAndBreak(1, player, (player1) -> player1.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                        if (level.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && !level.restoringBlockSnapshots) {
                            ItemEntity itemEntity = new ItemEntity(level, blockPos.getX(), blockPos.getY(), blockPos.getZ(), itemStack1);
                            itemEntity.setDefaultPickUpDelay();
                            level.addFreshEntity(itemEntity);
                        }
                    }
                }
            }
        }
    }

}