package terramine.mixin.item.accessories.ancientchisel;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import terramine.common.init.ModItems;
import terramine.common.init.ModMobEffects;
import terramine.common.misc.AccessoriesHelper;

@Mixin(BlockBehaviour.class)
public class BlockBehaviourMixin {

    @Inject(at = @At("RETURN"), method = "getDestroyProgress", cancellable = true)
    private void modifyBlockBreakSpeed(BlockState state, Player player, BlockGetter world, BlockPos pos, CallbackInfoReturnable<Float> info) {
        float speed = info.getReturnValue();
        if (AccessoriesHelper.isEquipped(ModItems.ANCIENT_CHISEL, player)) {
            speed = (float) (speed * 1.25);
        }
        if (player.hasEffect(ModMobEffects.MINING_SPEED)) {
            speed = (float) (speed * 1.25);
        }
        if (celestialStoneCheck(player)) {
            speed = (float) (speed * 1.15);
        }
        info.setReturnValue(speed);
    }

    private boolean celestialStoneCheck(Player player) {
        return ((AccessoriesHelper.isEquipped(ModItems.SUN_STONE, player) && !player.level().isNight()) || (AccessoriesHelper.isEquipped(ModItems.MOON_STONE, player) && player.level().isNight())
                || AccessoriesHelper.isEquipped(ModItems.CELESTIAL_STONE, player) || AccessoriesHelper.isEquipped(ModItems.CELESTIAL_SHELL, player));
    }
}
