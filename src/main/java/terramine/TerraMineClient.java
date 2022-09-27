package terramine;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import terramine.client.render.ModEntityBlockRenderers;
import terramine.client.render.trinket.CurioRenderers;
import terramine.common.init.*;
import terramine.common.utility.KeyBindingsHandler;

@Environment(EnvType.CLIENT)
public class TerraMineClient implements ClientModInitializer {
	private static final ModelResourceLocation UMBRELLA_HELD_MODEL = new ModelResourceLocation(TerraMine.id("umbrella_in_hand"), "inventory");

	@Override
	public void onInitializeClient() {
		// Adds Built-In ResourcePack
		FabricLoader.getInstance().getModContainer(TerraMine.MOD_ID).ifPresent(container -> {
			ResourceManagerHelper.registerBuiltinResourcePack(TerraMine.id("terramine_ctm"), container, "TerraMine CTM", ResourcePackActivationType.NORMAL);
		});

		// Held Umbrella model
		ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(UMBRELLA_HELD_MODEL));

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
		ModEntityBlockRenderers.register();

		// Entity models register
		ModLayerDefinitions.registerAll();
		ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new CurioRenderers());

		// Particle register
		ModParticles.registerClient();

		// Screen Handler
		ModScreenHandler.register();
	}
}
