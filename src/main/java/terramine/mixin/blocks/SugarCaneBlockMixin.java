package terramine.mixin.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import terramine.common.init.ModBlocks;

@Mixin(SugarCaneBlock.class)
public class SugarCaneBlockMixin {
    @Inject(method = "canSurvive", at = @At("RETURN"), cancellable = true)
    private void cactusSurviveCorruptSand(BlockState blockState, LevelReader levelReader, BlockPos blockPos, CallbackInfoReturnable<Boolean> cir) {
        BlockState blockState2 = levelReader.getBlockState(blockPos.below());
        if (blockState2.is(ModBlocks.CORRUPTED_SAND) || blockState2.is(ModBlocks.CRIMSON_SAND)) {
            BlockPos blockPos2 = blockPos.below();
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                BlockState blockState3 = levelReader.getBlockState(blockPos2.relative(direction));
                FluidState fluidState = levelReader.getFluidState(blockPos2.relative(direction));
                if (!fluidState.is(FluidTags.WATER) && !blockState3.is(Blocks.FROSTED_ICE)) continue;
                cir.setReturnValue(true);
            }
        }
    }
}
