package com.nekogan.teasofficial.datagen.recipes;

import com.nekogan.teasofficial.item.VajraItem;
import com.nekogan.teasofficial.registry.ModRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class VajraRepices extends RecipeProvider {
    public VajraRepices(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput consumer) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModRegistry.VAJRA_ITEM.get())
                .requires(Items.NETHERITE_PICKAXE)
                .requires(Items.NETHER_STAR)
                .requires(Items.CONDUIT)
                .requires(Items.SCULK_CATALYST)
                .unlockedBy("has_log", has(ItemTags.LOGS))
                .save(consumer);
    }
}
