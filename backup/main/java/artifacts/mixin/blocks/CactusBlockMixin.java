package terracraft.mixin.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CactusBlock.class)
public class CactusBlockMixin {
    @Inject(method = "canSurvive", at = @At("RETURN"), cancellable = true)
    private void cactusSurviveCorruptSand(BlockState blockState, LevelReader levelReader, BlockPos blockPos, CallbackInfoReturnable<Boolean> cir) {
        boolean cactusSurvive = true;
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockState blockState2 = levelReader.getBlockState(blockPos.relative(direction));
            Material material = blockState2.getMaterial();
            if (!material.isSolid() && !levelReader.getFluidState(blockPos.relative(direction)).is(FluidTags.LAVA)) continue;
            cactusSurvive = false;
        }

        BlockState blockState3 = levelReader.getBlockState(blockPos.below());
        cir.setReturnValue(((cir.getReturnValue() || blockState3.is(terracraft.common.init.ModBlocks.CORRUPTED_SAND)) && !levelReader.getBlockState(blockPos.above()).getMaterial().isLiquid() && cactusSurvive));
    }
}
