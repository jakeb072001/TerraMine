package terracraft.common.block.chests;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import terracraft.TerraCraft;
import terracraft.common.entity.block.ChestEntity;
import terracraft.common.entity.block.GoldChestEntity;
import terracraft.common.init.ModBlockEntityType;

import java.util.function.Supplier;

public class GoldChestBlock extends BaseChest {

    public GoldChestBlock(Properties properties, Supplier<BlockEntityType<? extends ChestBlockEntity>> supplier) {
        super(properties, supplier);
    }

    @Override
    public ResourceLocation getTexture() {
        return TerraCraft.id("block/chests/gold/gold_chest");
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new GoldChestEntity(blockPos, blockState);
    }

    public BlockEntityType<? extends GoldChestEntity> blockEntityType() {
        return (BlockEntityType)this.blockEntityType.get();
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return level.isClientSide ? BaseChest.createTickerHelper(blockEntityType, this.blockEntityType(), ChestEntity::clientTick) : null;
    }
}
