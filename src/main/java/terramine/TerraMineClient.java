package terramine;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.color.item.ItemTintSources;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.server.packs.PackType;
import terramine.client.render.ModAllEntityRenderers;
import terramine.client.render.accessory.AccessoryRenderers;
import terramine.client.render.color.TerrariaDye;
import terramine.common.init.ModFluids;
import terramine.common.init.ModModelLayers;
import terramine.common.init.ModParticles;
import terramine.common.init.ModScreenHandler;
import terramine.common.network.ServerPacketHandler;
import terramine.common.utility.KeyBindingsHandler;

import static terramine.TerraMine.id;

@Environment(EnvType.CLIENT)
public class TerraMineClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		// Adds Built-In ResourcePack
		FabricLoader.getInstance().getModContainer(TerraMine.MOD_ID).ifPresent(container -> {
			ResourceManagerHelper.registerBuiltinResourcePack(id("terramine_ctm"), container, "TerraMine CTM", ResourcePackActivationType.NORMAL);
		});

		// Client Packet Handler
		ServerPacketHandler.registerClient();

		// Events
		ClientTickEvents.END_CLIENT_TICK.register(KeyBindingsHandler::onClientTick);

		// All Entity renderers register
		ModAllEntityRenderers.register();

		// Entity models register
		ModModelLayers.BOMB.toString();
		ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new AccessoryRenderers());

		// Particle register
		ModParticles.registerClient();

		// Fluid Render register
		FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.STILL_SHIMMER, ModFluids.FLOWING_SHIMMER, new SimpleFluidRenderHandler(
				id("block/fluids/shimmer_still"),
				id("block/fluids/shimmer_flow"),
				id("block/fluids/shimmer_overlay")
		));
		BlockRenderLayerMap.INSTANCE.putFluids(RenderType.translucent(),
				ModFluids.STILL_SHIMMER,
				ModFluids.FLOWING_SHIMMER);

		// Screen Handler
		ModScreenHandler.register();

		// Item Tint register
		ItemTintSources.ID_MAPPER.put(id("terraria_dye"), TerrariaDye.MAP_CODEC);
	}
}
