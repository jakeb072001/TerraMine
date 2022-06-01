package terramine.mixin.item.spectreboots;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terramine.common.init.ModItems;
import terramine.common.init.ModSoundEvents;
import terramine.common.trinkets.TrinketsHelper;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Inject(method = "playStepSound", at = @At("HEAD"))
    private void playSprintStepSound(BlockPos pos, BlockState blockState, CallbackInfo callbackInfo) {
        if (blockState.getMaterial().isSolid() && isRunningWithSpectreBoots()) {
            ((LivingEntity) (Object) this).playSound(ModSoundEvents.SPEEDBOOTS_RUN, 0.5F, 1);
        }
    }

    @Unique
    private boolean isRunningWithSpectreBoots() {
        // noinspection ConstantConditions
        return (Object) this instanceof LivingEntity entity && (TrinketsHelper.isEquipped(ModItems.SPECTRE_BOOTS, entity) || TrinketsHelper.isEquipped(ModItems.FAIRY_BOOTS, entity)) &&
                !TrinketsHelper.isEquipped(ModItems.LIGHTNING_BOOTS, entity) && !TrinketsHelper.isEquipped(ModItems.FROSTSPARK_BOOTS, entity) &&
                        !TrinketsHelper.isEquipped(ModItems.TERRASPARK_BOOTS, entity) && entity.isSprinting();
    }
}
