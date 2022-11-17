package terramine.mixin.item.accessories.whoopeecushion;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terramine.common.init.ModItems;
import terramine.common.init.ModSoundEvents;
import terramine.common.trinkets.TrinketsHelper;

@Mixin(Entity.class)
public abstract class EntityMixin {

	@Shadow public Level level;

	@Inject(method = "setShiftKeyDown", at = @At("RETURN"))
	private void playFartSound(boolean sneaking, CallbackInfo info) {
		//noinspection ConstantConditions
		if (sneaking && !this.level.isClientSide() && (Object) this instanceof LivingEntity entity
				&& TrinketsHelper.isEquipped(ModItems.WHOOPEE_CUSHION, entity)
				&& entity.getRandom().nextInt(8) == 0) {

			entity.level.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
					ModSoundEvents.FART, SoundSource.PLAYERS, 1,
					0.9F + entity.getRandom().nextFloat() * 0.2F);
		}
	}
}
