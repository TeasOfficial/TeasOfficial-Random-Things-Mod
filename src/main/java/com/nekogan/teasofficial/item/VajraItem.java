package com.nekogan.teasofficial.item;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class VajraItem extends DiggerItem {
    private static final Map<Block, BlockState> FLATTENABLES = new HashMap<>();

    static {
        FLATTENABLES.put(Blocks.GRASS_BLOCK, Blocks.DIRT_PATH.defaultBlockState());
        FLATTENABLES.put(Blocks.DIRT, Blocks.DIRT_PATH.defaultBlockState());
        FLATTENABLES.put(Blocks.PODZOL, Blocks.DIRT_PATH.defaultBlockState());
        FLATTENABLES.put(Blocks.COARSE_DIRT, Blocks.DIRT_PATH.defaultBlockState());
        FLATTENABLES.put(Blocks.MYCELIUM, Blocks.DIRT_PATH.defaultBlockState());
    }

    public VajraItem(Tier tier, TagKey<Block> effectiveBlocks, Properties properties) {
        super(tier, effectiveBlocks, properties);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        ItemStack stack = context.getItemInHand();
        BlockState state = level.getBlockState(pos);

        if (player != null && isShieldConflicting(context)) {
            return InteractionResult.PASS;
        }

        Optional<BlockState> newState = tryToolActions(level, pos, player, state, context);
        if (newState.isPresent()) {
            level.setBlock(pos, newState.get(), 11);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, newState.get()));
            stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(context.getHand()));
            if (player instanceof ServerPlayer serverPlayer) {
                net.minecraft.advancements.CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayer, pos, stack);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return super.useOn(context);
    }

    private boolean isShieldConflicting(UseOnContext context) {
        Player player = context.getPlayer();
        return player != null
                && context.getHand() == InteractionHand.MAIN_HAND
                && player.getOffhandItem().is(Items.SHIELD)
                && !player.isSecondaryUseActive();
    }

    private Optional<BlockState> tryToolActions(Level level, BlockPos pos, Player player, BlockState state, UseOnContext context) {
        Optional<BlockState> stripped = Optional.ofNullable(state.getToolModifiedState(context, ItemAbilities.AXE_STRIP, false));
        if (stripped.isPresent()) {
            level.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
            return stripped;
        }

        Optional<BlockState> scraped = Optional.ofNullable(state.getToolModifiedState(context, ItemAbilities.AXE_SCRAPE, false));
        if (scraped.isPresent()) {
            level.playSound(player, pos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0F, 1.0F);
            return scraped;
        }

        Optional<BlockState> waxed = Optional.ofNullable(state.getToolModifiedState(context, ItemAbilities.AXE_WAX_OFF, false));
        if (waxed.isPresent()) {
            level.playSound(player, pos, SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS, 1.0F, 1.0F);
            return waxed;
        }

        if (FLATTENABLES.containsKey(state.getBlock())) {
            BlockState flattened = FLATTENABLES.get(state.getBlock());
            level.playSound(player, pos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
            return Optional.of(flattened);
        }

        Optional<BlockState> doused = Optional.ofNullable(state.getToolModifiedState(context, ItemAbilities.SHOVEL_DOUSE, false));
        if (doused.isPresent()) {
            level.playSound(player, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
            return doused;
        }

        return Optional.empty();
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ItemAbility ability) {
        return ItemAbilities.DEFAULT_AXE_ACTIONS.contains(ability)
                || ItemAbilities.DEFAULT_SHOVEL_ACTIONS.contains(ability);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        float destroyTime;
        try {
            BlockBehaviour.Properties props = state.getBlock().properties();
            Field destroyTimeField = null;
            destroyTimeField = BlockBehaviour.Properties.class.getDeclaredField("destroyTime");
            destroyTimeField.setAccessible(true);
            destroyTime = destroyTimeField.getFloat(props);
            if (destroyTime == 0.0F) {
                destroyTime = 1.0F;
            }
        } catch (Exception e) {
            destroyTime = 1.0F;
        }
        return destroyTime * 5.5F;
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack stack = super.getDefaultInstance();

        stack.enchant(Minecraft.getInstance().level.registryAccess().holderOrThrow(Enchantments.FORTUNE), 4);
        return stack;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return false;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip.teasofficial.vajra.1"));
        tooltip.add(Component.translatable(""));
        tooltip.add(Component.translatable("tooltip.teasofficial.vajra.2"));
        tooltip.add(Component.translatable("tooltip.teasofficial.unbreakable"));
    }

}