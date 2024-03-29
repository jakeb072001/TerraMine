package terramine.mixin.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.DeadBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import terramine.common.init.ModBlocks;

@Mixin(DeadBushBlock.class)
public class DeadBushBlockMixin {
    @Inject(method = "mayPlaceOn", at = @At("RETURN"), cancellable = true)
    private void deadBushSurviveCorruptSand(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(cir.getReturnValue() || blockState.is(ModBlocks.CORRUPTED_SAND) || blockState.is(ModBlocks.CRIMSON_SAND));
    }
}
