package com.nekogan.teasofficial.datagen.server;

import com.nekogan.teasofficial.Main;
import com.nekogan.teasofficial.registry.ModRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ItemsTag extends ItemTagsProvider {
    public ItemsTag(PackOutput packOutPut, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagsProvider.TagLookup<Block>> tagLookup, ExistingFileHelper existingFileHelper) {
        super(packOutPut, lookupProvider,tagLookup, Main.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ItemTags.HOES).add(ModRegistry.VAJRA_ITEM.get());
        tag(ItemTags.SHOVELS).add(ModRegistry.VAJRA_ITEM.get());
        tag(ItemTags.AXES).add(ModRegistry.VAJRA_ITEM.get());
        tag(ItemTags.PICKAXES).add(ModRegistry.VAJRA_ITEM.get());
        tag(ItemTags.SWORDS).add(ModRegistry.VAJRA_ITEM.get());
        tag(ItemTags.SHARP_WEAPON_ENCHANTABLE).add(ModRegistry.VAJRA_ITEM.get());
        tag(ItemTags.WEAPON_ENCHANTABLE).add(ModRegistry.VAJRA_ITEM.get());
        tag(ItemTags.FIRE_ASPECT_ENCHANTABLE).add(ModRegistry.VAJRA_ITEM.get());
        tag(ItemTags.MINING_ENCHANTABLE).add(ModRegistry.VAJRA_ITEM.get());
        tag(ItemTags.MINING_LOOT_ENCHANTABLE).add(ModRegistry.VAJRA_ITEM.get());
    }
}
