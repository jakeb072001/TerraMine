package terramine.common.block;

import net.fabricmc.fabric.api.mininglevel.v1.MiningLevelManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DungeonBlock extends Block {
    public static final BooleanProperty PLACED = BooleanProperty.create("player_placed");

    public DungeonBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(PLACED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PLACED);
    }

    /**
     * Checks if the block was placed by the Player. Creative mode placement should still be indestructible for development.
     * If the livingEntity is not null then also act as placed, just to support other entities that may be able to place blocks.
     */
    @Override
    public void setPlacedBy(@NotNull Level level, @NotNull BlockPos blockPos, @NotNull BlockState blockState, @Nullable LivingEntity livingEntity, @NotNull ItemStack itemStack) {
        if (livingEntity instanceof Player player) {
            if (!player.isCreative()) {
                if (blockState.getValues().containsKey(PLACED)) {
                    level.setBlock(blockPos, blockState.setValue(PLACED, true), 3);
                }
            }
        } else if (livingEntity != null) {
            if (blockState.getValues().containsKey(PLACED)) {
                level.setBlock(blockPos, blockState.setValue(PLACED, true), 3);
            }
        }
    }

    /**
     * Stops block from dropping if mined by hand (can't use requiresCorrectToolForDrop, using tag for indestructible mining tool)
     */
    @Override
    public void playerDestroy(@NotNull Level level, @NotNull Player player, @NotNull BlockPos blockPos, @NotNull BlockState blockState, @Nullable BlockEntity blockEntity, @NotNull ItemStack itemStack) {
        ItemStack cei = player.getMainHandItem();

        if (cei.getItem() instanceof DiggerItem) {
            super.playerDestroy(level, player, blockPos, blockState, blockEntity, itemStack);
        }
    }

    /**
     * Makes Dungeon Bricks indestructible when below y-60 unless mined with Netherite or better Pickaxe.
     * Also allows the block to be mined if placed by a player.
     */
    @Override
    @Deprecated
    public float getDestroyProgress(@NotNull BlockState state, @NotNull Player player, @NotNull BlockGetter getter, @NotNull BlockPos pos) {
        ItemStack cei = player.getMainHandItem();

        if (state.getValue(PLACED) || pos.getY() >= 60) {
            return super.getDestroyProgress(state, player, getter, pos);
        } else if (cei.getItem() instanceof DiggerItem test && test.getTier().getLevel() >= MiningLevelManager.getRequiredMiningLevel(state)) {
            return super.getDestroyProgress(state, player, getter, pos);
        }

        return 0;
    }
}
