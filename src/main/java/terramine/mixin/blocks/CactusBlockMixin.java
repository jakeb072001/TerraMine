package terramine.mixin.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import terramine.common.init.ModBlocks;

@Mixin(CactusBlock.class)
public class CactusBlockMixin {
    @Inject(method = "canSurvive", at = @At("RETURN"), cancellable = true)
    private void cactusSurviveCorruptSand(BlockState blockState, LevelReader levelReader, BlockPos blockPos, CallbackInfoReturnable<Boolean> cir) {
        boolean cactusSurvive = true;
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockState blockState2 = levelReader.getBlockState(blockPos.relative(direction));
            if (!blockState2.isSolid() && !levelReader.getFluidState(blockPos.relative(direction)).is(FluidTags.LAVA)) continue;
            cactusSurvive = false;
        }

        BlockState blockState3 = levelReader.getBlockState(blockPos.below());
        cir.setReturnValue(((cir.getReturnValue() || blockState3.is(ModBlocks.CORRUPTED_SAND) || blockState3.is(ModBlocks.CRIMSON_SAND)) && !levelReader.getFluidState(blockPos.above()).is(FluidTags.LAVA) && cactusSurvive));
    }
}
