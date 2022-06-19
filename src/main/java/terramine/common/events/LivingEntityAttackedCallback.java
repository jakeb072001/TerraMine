package terramine.common.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

/**
 * Callback for applying Trinket effects when the wearer was attacked by another entity
 */
public interface LivingEntityAttackedCallback {

	Event<LivingEntityAttackedCallback> EVENT = EventFactory.createArrayBacked(LivingEntityAttackedCallback.class,
			(listeners) -> (entity, attacker, random) -> {
				if (entity != null && attacker != null) {
					for (LivingEntityAttackedCallback listener : listeners) {
						listener.attack(entity, attacker, random);
					}
				}
			});

	void attack(LivingEntity entity, Entity attacker, RandomSource random);
}
