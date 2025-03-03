package terramine.common.block.fluids;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import terramine.common.entity.misc.ShimmerItemEntity;
import terramine.common.init.ModEntities;
import terramine.common.utility.ShimmerConversionRegistry;
import terramine.common.utility.UncraftingHelper;

import java.util.List;

// todo: make look a bit fancier, also need to make items float better
public class ShimmerFluidBlock extends LiquidBlock {
    public ShimmerFluidBlock(FlowingFluid flowingFluid, Properties properties) {
        super(flowingFluid, properties);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        handleShimmer(level, pos, entity);
    }

    public static void handleShimmer(Level level, BlockPos pos, Entity entity) {
        if (entity instanceof Player player) {
            if (!player.isCreative() && !player.isSpectator()) {
                player.setDeltaMovement(player.getDeltaMovement().multiply(1.0, 0.5, 1.0));

                BlockPos below = pos.below();
                BlockState currentState = level.getBlockState(pos);
                BlockState belowState = level.getBlockState(below);

                if ((belowState.isAir() && currentState.isAir()) || below.getY() <= level.getMinY()) {
                    player.setDeltaMovement(player.getDeltaMovement().multiply(1.0, 0.0, 1.0));
                }
            }
        }

        if (entity instanceof ItemEntity itemEntity && !(entity instanceof ShimmerItemEntity)) {
            ItemStack stack = itemEntity.getItem();

            if (ShimmerConversionRegistry.hasConversion(stack)) {
                Item convertedItem = ShimmerConversionRegistry.getConvertedItem(stack);
                ItemStack newStack = new ItemStack(convertedItem, stack.getCount());

                ShimmerItemEntity newItemEntity = new ShimmerItemEntity(ModEntities.SHIMMER_ITEM, level);
                newItemEntity.setValues(level, itemEntity.getX(), itemEntity.getY(), itemEntity.getZ(), newStack);
                newItemEntity.setInvulnerable(true);
                level.addFreshEntity(newItemEntity);

                itemEntity.discard();
            } else if (level instanceof ServerLevel serverLevel) {
                List<ItemStack> uncraftedItems = UncraftingHelper.uncraft(stack, serverLevel);
                if (!uncraftedItems.isEmpty()) {
                    for (ItemStack uncrafted : uncraftedItems) {
                        ShimmerItemEntity newItemEntity = new ShimmerItemEntity(ModEntities.SHIMMER_ITEM, level);
                        newItemEntity.setValues(level, itemEntity.getX(), itemEntity.getY(), itemEntity.getZ(), uncrafted);
                        newItemEntity.setInvulnerable(true);
                        level.addFreshEntity(newItemEntity);
                    }
                    itemEntity.discard();
                }
            }
        }
    }
}
