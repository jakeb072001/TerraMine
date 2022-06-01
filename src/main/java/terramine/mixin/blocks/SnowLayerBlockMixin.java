package terramine.mixin.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import terramine.common.init.ModBlocks;

@Mixin(SnowLayerBlock.class)
public class SnowLayerBlockMixin {
    @Shadow
    public static final IntegerProperty LAYERS = BlockStateProperties.LAYERS;

    @Inject(method = "canSurvive", at = @At("RETURN"), cancellable = true)
    private void snowLayerSurvive(BlockState blockState, LevelReader levelReader, BlockPos blockPos, CallbackInfoReturnable<Boolean> cir) {
        BlockState blockState2 = levelReader.getBlockState(blockPos.below());
        if (blockState2.is(Blocks.ICE) || blockState2.is(Blocks.PACKED_ICE) || blockState2.is(Blocks.BARRIER) || blockState2.is(ModBlocks.CORRUPTED_ICE) || blockState2.is(ModBlocks.CORRUPTED_PACKED_ICE)) {
            cir.setReturnValue(false);
        } else {
            cir.setReturnValue(Block.isFaceFull(blockState2.getCollisionShape(levelReader, blockPos.below()), Direction.UP) || (blockState2.is((Block)(Object)this) || blockState2.is(ModBlocks.CORRUPTED_SNOW_LAYER)) && blockState2.getValue(LAYERS) == 8);
        }
    }
}
