package terramine;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import terramine.client.render.HeldItemModels;
import terramine.client.render.ModAllEntityRenderers;
import terramine.client.render.trinket.CurioRenderers;
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

		// Held item models
		HeldItemModels.register();

		// Keybinding Handler
		ClientTickEvents.END_CLIENT_TICK.register(KeyBindingsHandler::onClientTick);

		// ModelPredicateProvider for rendering of umbrella and shield blocking
		ItemProperties.register(ModItems.UMBRELLA, new ResourceLocation("blocking"), (stack, level, entity, i)
				-> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1 : 0);
		ItemProperties.register(ModItems.COBALT_SHIELD, new ResourceLocation("blocking"), (stack, level, entity, i)
				-> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1 : 0);
		ItemProperties.register(ModItems.OBSIDIAN_SHIELD, new ResourceLocation("blocking"), (stack, level, entity, i)
				-> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1 : 0);

		// All Entity renderers register
		ModAllEntityRenderers.register();

		// Entity models register
		ModLayerDefinitions.registerAll();
		ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new CurioRenderers());

		// Particle register
		ModParticles.registerClient();

		// Screen Handler
		ModScreenHandler.register();

		// Client Packet Handler
		ServerPacketHandler.registerClient();
	}
}
