package terramine;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.color.item.ItemTintSources;
import net.minecraft.server.packs.PackType;
import terramine.client.render.ModAllEntityRenderers;
import terramine.client.render.accessory.AccessoryRenderers;
import terramine.client.render.color.TerrariaDye;
import terramine.common.init.*;
import terramine.common.network.ServerPacketHandler;
import terramine.common.utility.KeyBindingsHandler;

@Environment(EnvType.CLIENT)
public class TerraMineClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		// Adds Built-In ResourcePack
		FabricLoader.getInstance().getModContainer(TerraMine.MOD_ID).ifPresent(container -> {
			ResourceManagerHelper.registerBuiltinResourcePack(TerraMine.id("terramine_ctm"), container, "TerraMine CTM", ResourcePackActivationType.NORMAL);
		});

		// Client Packet Handler
		ServerPacketHandler.registerClient();

		// Keybinding Handler
		ClientTickEvents.END_CLIENT_TICK.register(KeyBindingsHandler::onClientTick);

		// All Entity renderers register
		ModAllEntityRenderers.register();

		// Entity models register
		ModModelLayers.BOMB.toString();
		ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new AccessoryRenderers());

		// Particle register
		ModParticles.registerClient();

		// Screen Handler
		ModScreenHandler.register();

		// Item Tint Register
		ItemTintSources.ID_MAPPER.put(TerraMine.id("terraria_dye"), TerrariaDye.MAP_CODEC);
	}
}
