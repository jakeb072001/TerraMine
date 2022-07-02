package terramine.common.block.chests;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import terramine.TerraMine;
import terramine.common.entity.block.ChestEntity;
import terramine.common.entity.block.SafeEntity;

import java.util.function.Supplier;

public class SafeBlock extends BaseChest {
    boolean trapped;
    protected static final VoxelShape SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

    public SafeBlock(Properties properties, boolean trapped, Supplier<BlockEntityType<? extends ChestBlockEntity>> supplier) {
        super(properties, trapped, supplier);
        this.trapped = trapped;
    }

    public VoxelShape getShape(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos, @NotNull CollisionContext collisionContext) {
        return SHAPE;
    }

    @Override
    public ResourceLocation getTexture() {
        return TerraMine.id("block/chests/player/safe/safe");
    }

    @Override
    public RenderShape getRenderShape(@NotNull BlockState blockState) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        ChestEntity chest = new SafeEntity(blockPos, blockState);
        chest.setTrapped(this.trapped);
        return chest;
    }

    @Override
    public BlockEntityType<? extends SafeEntity> blockEntityType() {
        return (BlockEntityType)this.blockEntityType.get();
    }
}
