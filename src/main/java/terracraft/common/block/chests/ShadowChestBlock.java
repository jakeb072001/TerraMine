package terracraft.common.block.chests;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import terracraft.TerraCraft;
import terracraft.common.entity.block.ChestEntity;
import terracraft.common.entity.block.SandstoneChestEntity;
import terracraft.common.entity.block.ShadowChestEntity;

import java.util.function.Supplier;

public class ShadowChestBlock extends BaseChest {
    boolean trapped;

    public ShadowChestBlock(Properties properties, boolean trapped, Supplier<BlockEntityType<? extends ChestBlockEntity>> supplier) {
        super(properties, trapped, supplier);
        this.trapped = trapped;
    }

    @Override
    public ResourceLocation getTexture() {
        return TerraCraft.id("block/chests/shadow/shadow_chest");
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        ChestEntity chest = new ShadowChestEntity(blockPos, blockState);
        chest.setTrapped(this.trapped);
        return chest;
    }

    @Override
    public BlockEntityType<? extends ShadowChestEntity> blockEntityType() {
        return (BlockEntityType)this.blockEntityType.get();
    }
}
