package terramine.common.block.fluids;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import terramine.common.utility.CustomCauldronInteractions;

public class HoneyCauldronBlock extends AbstractCauldronBlock {
    public static final MapCodec<HoneyCauldronBlock> CODEC = simpleCodec(HoneyCauldronBlock::new);
    public static final IntegerProperty LEVEL;

    public @NotNull MapCodec<HoneyCauldronBlock> codec() {
        return CODEC;
    }

    public HoneyCauldronBlock(Properties properties) {
        super(properties, CustomCauldronInteractions.HONEY);
        this.registerDefaultState(this.stateDefinition.any().setValue(LEVEL, 1));
    }

    protected double getContentHeight(BlockState blockState) {
        return (6.0 + (double) blockState.getValue(LEVEL) * 3.0) / 16.0;
    }

    public boolean isFull(BlockState blockState) {
        return blockState.getValue(LEVEL) == 3;
    }

    public static void lowerFillLevel(BlockState blockState, Level level, BlockPos blockPos) {
        int i = blockState.getValue(LEVEL) - 1;
        BlockState blockState2 = i == 0 ? Blocks.CAULDRON.defaultBlockState() : blockState.setValue(LEVEL, i);
        level.setBlockAndUpdate(blockPos, blockState2);
        level.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Context.of(blockState2));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LEVEL);
    }

    protected int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos blockPos) {
        return blockState.getValue(LEVEL);
    }

    static {
        LEVEL = BlockStateProperties.LEVEL_CAULDRON;
    }
}
