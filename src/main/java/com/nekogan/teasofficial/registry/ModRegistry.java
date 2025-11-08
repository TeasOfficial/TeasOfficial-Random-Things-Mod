package com.nekogan.teasofficial.registry;

import com.nekogan.teasofficial.Main;
import com.nekogan.teasofficial.item.FoodsSheep;
import com.nekogan.teasofficial.item.VajraItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.Unbreakable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

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
                        new SimpleTier(BlockTags.INCORRECT_FOR_NETHERITE_TOOL,0, 0.0F, 5.0F, 0, () -> null),
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

    public static final DeferredHolder<Item, Item> HORN_O_PLENTY = ITEMS.register(
            "horn_o_plenty",
            () -> new Item(
                    new Item.Properties().food(FoodsSheep.HORN_O_PLENTY).stacksTo(1)
            ) {
                @Override
                public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
                    tooltip.add(Component.translatable("tooltip.teasofficial.horn_o_plenty.1"));
                }

                // 使用后不消耗物品
                @Override
                public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {
                    if(stack.has(DataComponents.FOOD)) {
                        entity.eat(world, stack.copy());
                    }
                    return stack;
                }
            }
    );

    public static final DeferredHolder<CreativeModeTab,CreativeModeTab> CREATIVE_TAB =
            CREATIVE_TABS.register(
                    "teasmc", () ->
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
