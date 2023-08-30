package terramine.common.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nullable;

/**
 * Callback for increasing potion duration with accessories
 */
public interface LivingEntityPotionEffectCallback {

	Event<LivingEntityPotionEffectCallback> EVENT = EventFactory.createArrayBacked(LivingEntityPotionEffectCallback.class,
			(listeners) -> (entity, newEffect, oldEffect, source) -> {
				if (entity != null) {
					for (LivingEntityPotionEffectCallback listener : listeners) {
						listener.onPotionAdded(entity, newEffect, oldEffect, source);
					}
				}
			});

	void onPotionAdded(LivingEntity entity, MobEffectInstance newEffect, MobEffectInstance oldEffect, @Nullable Entity source);
}
