package terracraft.mixin.item.pendant;

import terracraft.common.init.Items;
import terracraft.common.trinkets.TrinketsHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {

	@Inject(method = "thunderHit", at = @At("HEAD"), cancellable = true)
	private void lightningImmune(ServerLevel world, LightningBolt lightning, CallbackInfo info) {
		//noinspection ConstantConditions
		if ((Entity) (Object) this instanceof LivingEntity entity && TrinketsHelper.isEquipped(Items.SHOCK_PENDANT, entity)) {
			info.cancel();
		}
	}
}
