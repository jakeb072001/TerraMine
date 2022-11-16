package terramine.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HellstoneBlock extends HotFloorBlock {
    public HellstoneBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void playerDestroy(@NotNull Level level, @NotNull Player player, @NotNull BlockPos blockPos, @NotNull BlockState blockState, @Nullable BlockEntity blockEntity, @NotNull ItemStack itemStack) {
        super.playerDestroy(level, player, blockPos, blockState, blockEntity, itemStack);

        if (level.dimensionType().ultraWarm()) {
            level.setBlockAndUpdate(blockPos, Blocks.LAVA.defaultBlockState());
        }
    }
}
