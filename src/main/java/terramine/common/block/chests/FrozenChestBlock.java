package terramine.common.block.chests;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import terramine.TerraMine;
import terramine.common.entity.block.ChestEntity;
import terramine.common.entity.block.FrozenChestEntity;

import java.util.function.Supplier;

public class FrozenChestBlock extends BaseChest {

    public FrozenChestBlock(Properties properties, boolean trapped, Supplier<BlockEntityType<? extends ChestBlockEntity>> supplier) {
        super(properties, trapped, supplier);
    }

    @Override
    public ResourceLocation getTexture() {
        return TerraMine.id("block/chests/frozen/frozen_chest");
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        ChestEntity chest = new FrozenChestEntity(blockPos, blockState);
        chest.setTrapped(blockState.getValue(TRAPPED));
        return chest;
    }

    @Override
    public BlockEntityType<? extends FrozenChestEntity> blockEntityType() {
        return (BlockEntityType)this.blockEntityType.get();
    }
}
