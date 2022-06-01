package terramine.common.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.LivingEntity;

/**
 * Callback for playing hurt sound on both server and client for a LivingEntity
 */
public interface PlayHurtSoundCallback {

	Event<PlayHurtSoundCallback> EVENT = EventFactory.createArrayBacked(PlayHurtSoundCallback.class,
			(listeners) -> (entity, volume, pitch) -> {
				for (PlayHurtSoundCallback listener : listeners) {
					listener.play(entity, volume, pitch);
				}
			});

	void play(LivingEntity entity, float volume, float pitch);
}
