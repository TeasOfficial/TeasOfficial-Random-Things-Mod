package com.nekogan.teasofficial.datagen.server;

import com.nekogan.teasofficial.Main;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class BlocksTag extends IntrinsicHolderTagsProvider<Block> {
    public BlocksTag(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries,
                             ExistingFileHelper existingFileHelper) {
        super(packOutput, Registries.BLOCK, registries, block -> block.builtInRegistryHolder().key(), Main.MODID,
                existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {

    }
}
