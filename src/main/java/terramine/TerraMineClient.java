package terramine;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import terramine.client.render.entity.*;
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

		// Block RenderLayer
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CORRUPTED_GLASS, RenderType.translucent());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CORRUPTED_ICE, RenderType.translucent());

		// Block Entity Renderer
		BlockEntityRendererRegistry.register(ModBlockEntityType.GOLD_CHEST, ChestEntityRenderer::new);
		BlockEntityRendererRegistry.register(ModBlockEntityType.FROZEN_CHEST, ChestEntityRenderer::new);
		BlockEntityRendererRegistry.register(ModBlockEntityType.IVY_CHEST, ChestEntityRenderer::new);
		BlockEntityRendererRegistry.register(ModBlockEntityType.SANDSTONE_CHEST, ChestEntityRenderer::new);
		BlockEntityRendererRegistry.register(ModBlockEntityType.WATER_CHEST, ChestEntityRenderer::new);
		BlockEntityRendererRegistry.register(ModBlockEntityType.SKYWARE_CHEST, ChestEntityRenderer::new);
		BlockEntityRendererRegistry.register(ModBlockEntityType.SHADOW_CHEST, ChestEntityRenderer::new);
		registerTextures();

		// Entity Renderers
		EntityRendererRegistry.register(ModEntities.MIMIC, MimicRenderer::new);
		EntityRendererRegistry.register(ModEntities.DEMON_EYE, DemonEyeRenderer::new);
		EntityRendererRegistry.register(ModEntities.FALLING_STAR, FallingStarRenderer::new);
		EntityRendererRegistry.register(ModEntities.MAGIC_MISSILE, MagicMissileRenderer::new);
		EntityRendererRegistry.register(ModEntities.FLAMELASH_MISSILE, FlamelashMissileRenderer::new);
		EntityRendererRegistry.register(ModEntities.RAINBOW_MISSILE, RainbowMissileRenderer::new);

		// Entity models
		ModLayerDefinitions.registerAll();
		ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new CurioRenderers());

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

		// Particle Register
		ModParticles.registerClient();

		// Screen Handler
		ModScreenHandler.register();
	}

	public static void registerTextures() {
		ClientSpriteRegistryCallback.event(Sheets.CHEST_SHEET).register((texture, registry) -> {
			registry.register(TerraMine.id("block/chests/gold/gold_chest"));
			registry.register(TerraMine.id("block/chests/frozen/frozen_chest"));
			registry.register(TerraMine.id("block/chests/ivy/ivy_chest"));
			registry.register(TerraMine.id("block/chests/sandstone/sandstone_chest"));
			registry.register(TerraMine.id("block/chests/water/water_chest"));
			registry.register(TerraMine.id("block/chests/skyware/skyware_chest"));
			registry.register(TerraMine.id("block/chests/shadow/shadow_chest"));
		});
	}
}
