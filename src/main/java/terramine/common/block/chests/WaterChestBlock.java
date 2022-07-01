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
import terramine.common.entity.block.WaterChestEntity;

import java.util.function.Supplier;

public class WaterChestBlock extends BaseChest {
    boolean trapped;

    public WaterChestBlock(Properties properties, boolean trapped, Supplier<BlockEntityType<? extends ChestBlockEntity>> supplier) {
        super(properties, trapped, supplier);
        this.trapped = trapped;
    }

    @Override
    public ResourceLocation getTexture() {
        return TerraMine.id("block/chests/water/water_chest");
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        ChestEntity chest = new WaterChestEntity(blockPos, blockState);
        chest.setTrapped(this.trapped);
        return chest;
    }

    @Override
    public BlockEntityType<? extends WaterChestEntity> blockEntityType() {
        return (BlockEntityType)this.blockEntityType.get();
    }
}
