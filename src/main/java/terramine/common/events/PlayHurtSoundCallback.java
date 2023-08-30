package terramine.common.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.player.Player;

/**
 * Callback for playing hurt sound on both server and client for a LivingEntity
 */
public interface PlayHurtSoundCallback {

	Event<PlayHurtSoundCallback> EVENT = EventFactory.createArrayBacked(PlayHurtSoundCallback.class,
			(listeners) -> (player, volume, pitch) -> {
				for (PlayHurtSoundCallback listener : listeners) {
					listener.play(player, volume, pitch);
				}
			});

	void play(Player player, float volume, float pitch);
}
