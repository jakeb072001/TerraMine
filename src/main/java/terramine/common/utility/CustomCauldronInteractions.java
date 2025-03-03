package terramine.common.utility;

import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import terramine.common.block.fluids.HoneyCauldronBlock;
import terramine.common.init.ModBlocks;
import terramine.common.init.ModFluids;
import terramine.common.init.ModItems;

import java.util.Map;

import static net.fabricmc.fabric.api.transfer.v1.fluid.CauldronFluidContent.registerCauldron;
import static net.minecraft.core.cauldron.CauldronInteraction.*;

public class CustomCauldronInteractions {
    public static final CauldronInteraction.InteractionMap SHIMMER;
    public static final CauldronInteraction.InteractionMap HONEY;

    public static void register() {
        // Shimmer
        SHIMMER.map().put(Items.BUCKET, (blockState, level, blockPos, player, interactionHand, itemStack) -> fillBucket(blockState, level, blockPos, player, interactionHand, itemStack, new ItemStack(ModItems.SHIMMER_BUCKET), (blockStateX) -> true, SoundEvents.BUCKET_FILL));
        registerCauldron(ModBlocks.SHIMMER_CAULDRON.BLOCK, ModFluids.SHIMMER, 81000L, null);

        // Honey
        HONEY.map().put(Items.BUCKET, (blockState, level, blockPos, player, interactionHand, itemStack) -> {
            return fillBucket(blockState, level, blockPos, player, interactionHand, itemStack, new ItemStack(ModItems.HONEY_BUCKET), (blockStatex) -> {
                return blockStatex.getValue(HoneyCauldronBlock.LEVEL) == 3;
            }, SoundEvents.BUCKET_FILL);
        });
        HONEY.map().put(Items.GLASS_BOTTLE, (blockState, level, blockPos, player, interactionHand, itemStack) -> {
            if (!level.isClientSide) {
                Item item = itemStack.getItem();
                player.setItemInHand(interactionHand, ItemUtils.createFilledResult(itemStack, player, Items.HONEY_BOTTLE.getDefaultInstance()));
                player.awardStat(Stats.USE_CAULDRON);
                player.awardStat(Stats.ITEM_USED.get(item));
                HoneyCauldronBlock.lowerFillLevel(blockState, level, blockPos);
                level.playSound(null, blockPos, SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.gameEvent(null, GameEvent.FLUID_PICKUP, blockPos);
            }

            return InteractionResult.SUCCESS;
        });
        HONEY.map().put(Items.HONEY_BOTTLE, (blockState, level, blockPos, player, interactionHand, itemStack) -> {
            if (blockState.getValue(HoneyCauldronBlock.LEVEL) == 3) {
                return InteractionResult.TRY_WITH_EMPTY_HAND;
            } else {
                if (!level.isClientSide) {
                    player.setItemInHand(interactionHand, ItemUtils.createFilledResult(itemStack, player, new ItemStack(Items.GLASS_BOTTLE)));
                    player.awardStat(Stats.USE_CAULDRON);
                    player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
                    level.setBlockAndUpdate(blockPos, blockState.cycle(HoneyCauldronBlock.LEVEL));
                    level.playSound(null, blockPos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
                    level.gameEvent(null, GameEvent.FLUID_PLACE, blockPos);
                }

                return InteractionResult.SUCCESS;
            }
        });
        registerCauldron(ModBlocks.HONEY_CAULDRON.BLOCK, ModFluids.HONEY, 27000L, HoneyCauldronBlock.LEVEL);

        addFillInteractions(CauldronInteraction.EMPTY.map());
    }

    private static void addFillInteractions(Map<Item, CauldronInteraction> map) {
        // Shimmer
        map.put(ModItems.SHIMMER_BUCKET, CustomCauldronInteractions::fillShimmerInteraction);

        // Honey
        map.put(ModItems.HONEY_BUCKET, CustomCauldronInteractions::fillHoneyInteraction);
        map.put(Items.HONEY_BOTTLE, (blockState, level, blockPos, player, interactionHand, itemStack) -> {
            if (blockState.is(ModBlocks.HONEY_CAULDRON.BLOCK)) {
                if (blockState.getValue(HoneyCauldronBlock.LEVEL) == 3) {
                    return InteractionResult.TRY_WITH_EMPTY_HAND;
                } else {
                    return fillHoneyWithBottleInteraction(blockState.cycle(HoneyCauldronBlock.LEVEL), level, blockPos, player, interactionHand, itemStack);
                }
            } else {
                return fillHoneyWithBottleInteraction(ModBlocks.HONEY_CAULDRON.BLOCK.defaultBlockState(), level, blockPos, player, interactionHand, itemStack);
            }
        });
    }

    private static InteractionResult fillShimmerInteraction(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, ItemStack itemStack) {
        return isUnderWater(level, blockPos) ? InteractionResult.CONSUME : emptyBucket(level, blockPos, player, interactionHand, itemStack, ModBlocks.SHIMMER_CAULDRON.BLOCK.defaultBlockState(), SoundEvents.BUCKET_EMPTY);
    }

    private static InteractionResult fillHoneyInteraction(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, ItemStack itemStack) {
        return isUnderWater(level, blockPos) ? InteractionResult.CONSUME : emptyBucket(level, blockPos, player, interactionHand, itemStack, ModBlocks.HONEY_CAULDRON.BLOCK.defaultBlockState().setValue(HoneyCauldronBlock.LEVEL, 3), SoundEvents.BUCKET_EMPTY);
    }

    private static InteractionResult fillHoneyWithBottleInteraction(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, ItemStack itemStack) {
        if (isUnderWater(level, blockPos)) {
            return InteractionResult.CONSUME;
        }

        if (!level.isClientSide) {
            player.setItemInHand(interactionHand, ItemUtils.createFilledResult(itemStack, player, new ItemStack(Items.GLASS_BOTTLE)));
            player.awardStat(Stats.USE_CAULDRON);
            player.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
            level.setBlockAndUpdate(blockPos, blockState);
            level.playSound(null, blockPos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.gameEvent(null, GameEvent.FLUID_PLACE, blockPos);
        }

        return InteractionResult.SUCCESS;
    }

    static {
        SHIMMER = newInteractionMap("shimmer");
        HONEY = newInteractionMap("honey");
    }
}
