package terracraft.mixin.item.frostsparkboots;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terracraft.common.init.ModItems;
import terracraft.common.init.ModSoundEvents;
import terracraft.common.trinkets.TrinketsHelper;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Inject(method = "playStepSound", at = @At("HEAD"))
    private void playWaterStepSound(BlockPos pos, BlockState blockState, CallbackInfo callbackInfo) {
        if (isRunningWithFrostsparkBoots()) {
            ((LivingEntity) (Object) this).playSound(ModSoundEvents.SPEEDBOOTS_RUN, 0.5F, 1);
        }
    }

    @Unique
    private boolean isRunningWithFrostsparkBoots() {
        // noinspection ConstantConditions
        return (Object) this instanceof LivingEntity entity && TrinketsHelper.isEquipped(ModItems.FROSTSPARK_BOOTS, entity) && !TrinketsHelper.isEquipped(ModItems.TERRASPARK_BOOTS, entity) && entity.isSprinting();
    }
}
