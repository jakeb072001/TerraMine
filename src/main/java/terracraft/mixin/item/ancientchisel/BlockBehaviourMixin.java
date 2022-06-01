package terracraft.mixin.item.ancientchisel;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import terracraft.common.init.ModItems;
import terracraft.common.init.ModMobEffects;
import terracraft.common.trinkets.TrinketsHelper;

@Mixin(BlockBehaviour.class)
public class BlockBehaviourMixin {

    @Inject(at = @At("RETURN"), method = "getDestroyProgress", cancellable = true)
    private void modifyBlockBreakSpeed(BlockState state, Player player, BlockGetter world, BlockPos pos, CallbackInfoReturnable<Float> info) {
        float speed = info.getReturnValue();
        if (TrinketsHelper.isEquipped(ModItems.ANCIENT_CHISEL, player)) {
            speed = (float) (speed * 1.25);
        }
        if (player.hasEffect(ModMobEffects.MINING_SPEED)) {
            speed = (float) (speed * 1.25);
        }
        info.setReturnValue(speed);
    }
}
