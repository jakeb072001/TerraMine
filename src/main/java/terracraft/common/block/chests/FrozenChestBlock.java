package terracraft.common.block.chests;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import terracraft.TerraCraft;
import terracraft.common.entity.block.ChestEntity;
import terracraft.common.entity.block.FrozenChestEntity;

import java.util.function.Supplier;

public class FrozenChestBlock extends BaseChest {
    boolean trapped;

    public FrozenChestBlock(Properties properties, boolean trapped, Supplier<BlockEntityType<? extends ChestBlockEntity>> supplier) {
        super(properties, trapped, supplier);
        this.trapped = trapped;
    }

    @Override
    public ResourceLocation getTexture() {
        return TerraCraft.id("block/chests/frozen/frozen_chest");
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        ChestEntity chest = new FrozenChestEntity(blockPos, blockState);
        chest.setTrapped(this.trapped);
        return chest;
    }

    @Override
    public BlockEntityType<? extends FrozenChestEntity> blockEntityType() {
        return (BlockEntityType)this.blockEntityType.get();
    }
}
