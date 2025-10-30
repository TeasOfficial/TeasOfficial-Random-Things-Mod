package com.nekogan.teasofficial.registry;

import com.nekogan.teasofficial.Main;
import com.nekogan.teasofficial.item.VajraItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.Unbreakable;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.createItems(Main.MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Main.MODID);
    // 为什么必须要这个？
    @SubscribeEvent
    public static void a(EntityAttributeCreationEvent event){}

    public static final TagKey<Block> VAJRA_TOOLTAG = BlockTags.create(ResourceLocation.withDefaultNamespace("mineable/pickaxe"));

    public static final DeferredHolder<Item, DiggerItem> VAJRA_ITEM = ITEMS.register(
            "vajra",
            () ->
                new VajraItem(
                        Tiers.NETHERITE,
                        VAJRA_TOOLTAG,
                        new Item.Properties()
                                .rarity(Rarity.EPIC)
                                .component(DataComponents.UNBREAKABLE, new Unbreakable(false))
                                .fireResistant()
                                .attributes(DiggerItem.createAttributes(
                                        Tiers.NETHERITE,
                                        20,
                                        10.0F
                                ))
                )
    );

    public static final DeferredHolder<CreativeModeTab,CreativeModeTab> CREATIVE_TAB =
            CREATIVE_TABS.register(
                    "creative_tab", () ->
                            CreativeModeTab.builder()
                                    .title(Component.translatable(Main.tab()))
                                    .icon(() -> VAJRA_ITEM.get().getDefaultInstance())
                                    .displayItems((parameters, output) -> ITEMS.getEntries().stream()
                                            .map(DeferredHolder::get)
                                            .map(Item::getDefaultInstance)
                                            .forEach(output::accept))
                                    .build()
            );
}
