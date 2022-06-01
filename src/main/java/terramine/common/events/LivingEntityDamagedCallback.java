package terramine.common.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

/**
 * Callback for applying Trinket effects when the wearer was damaged, same injection point as Forge's LivingDamagedEvent
 */
public interface LivingEntityDamagedCallback {

	Event<LivingEntityDamagedCallback> EVENT = EventFactory.createArrayBacked(LivingEntityDamagedCallback.class,
			(listeners) -> (entity, attacker, random) -> {
				if (entity != null && attacker != null) {
					for (LivingEntityDamagedCallback listener : listeners) {
						listener.damage(entity, attacker, random);
					}
				}
			});

	void damage(LivingEntity entity, DamageSource source, float amount);
}
