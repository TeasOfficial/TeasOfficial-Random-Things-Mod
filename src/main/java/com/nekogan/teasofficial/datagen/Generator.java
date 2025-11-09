package com.nekogan.teasofficial.datagen;

import com.nekogan.teasofficial.Main;
import com.nekogan.teasofficial.datagen.recipes.VajraRepices;
import com.nekogan.teasofficial.datagen.server.BlocksTag;
import com.nekogan.teasofficial.datagen.server.ItemsTag;
import net.minecraft.data.DataGenerator;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = Main.MODID)
public class Generator{
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        var pack = generator.getVanillaPack(true);
        var registries = event.getLookupProvider();
        var existingFileHelper = event.getExistingFileHelper();

        var bT = pack
                .addProvider(packOutput -> new BlocksTag(packOutput, registries, existingFileHelper));

        pack.addProvider(packOutput ->
                new ItemsTag(
                        packOutput,
                        registries,
                        bT.contentsGetter(),
                        existingFileHelper
                ));

        pack.addProvider(packOutput ->
                new VajraRepices(
                        packOutput,
                        registries
                ));


    }
}