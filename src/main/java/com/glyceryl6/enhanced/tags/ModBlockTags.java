package com.glyceryl6.enhanced.tags;

import com.glyceryl6.enhanced.VanillaEquipmentEnhanced;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModBlockTags {

    public static final TagKey<Block> UNBREAKABLE_BLOCK = create("unbreakable_blocks");

    public static TagKey<Block> create(String name) {
        return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(VanillaEquipmentEnhanced.MOD_ID, name));
    }

}