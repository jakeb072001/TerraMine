package terracraft;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.PlayerCloudParticle;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import terracraft.client.render.entity.*;
import terracraft.client.render.trinket.CurioRenderers;
import terracraft.common.init.*;

@Environment(EnvType.CLIENT)
public class TerraCraftClient implements ClientModInitializer {

	private static final ModelResourceLocation UMBRELLA_HELD_MODEL = new ModelResourceLocation(TerraCraft.id("umbrella_in_hand"), "inventory");

	@Override
	public void onInitializeClient() {
		/* Registers our particle client-side.
		 * First argument is our particle's instance, created previously on ExampleMod.
		 * Second argument is the particle's factory. The factory controls how the particle behaves.
		 * In this example, we'll use FlameParticle's Factory.*/
		ParticleFactoryRegistry.getInstance().register(TerraCraft.BLUE_POOF, PlayerCloudParticle.Provider::new);
		ParticleFactoryRegistry.getInstance().register(TerraCraft.GREEN_SPARK, FlameParticle.Provider::new);

		// Adds Built-In ResourcePack
		FabricLoader.getInstance().getModContainer(TerraCraft.MOD_ID).ifPresent(container -> {
			ResourceManagerHelper.registerBuiltinResourcePack(TerraCraft.id("terracraft_ctm"), container, ResourcePackActivationType.NORMAL);
		});

		// Entity Renderers
		EntityRendererRegistry.register(ModEntities.MIMIC, MimicRenderer::new);
		EntityRendererRegistry.register(ModEntities.DEMON_EYE, DemonEyeRenderer::new);
		EntityRendererRegistry.register(ModEntities.FALLING_STAR, FallingStarRenderer::new);
		EntityRendererRegistry.register(ModEntities.MAGIC_MISSILE, MagicMissileRenderer::new);
		EntityRendererRegistry.register(ModEntities.FLAMELASH_MISSILE, FlamelashMissileRenderer::new);
		EntityRendererRegistry.register(ModEntities.RAINBOW_MISSILE, RainbowMissileRenderer::new);

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

		// Entity models
		ModLayerDefinitions.registerAll();
		ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new CurioRenderers());

		// Screen Handler
		ModScreenHandler.register();

		// Held Umbrella model
		ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(UMBRELLA_HELD_MODEL));

		// ModelPredicateProvider for rendering of umbrella blocking
		FabricModelPredicateProviderRegistry.register(ModItems.UMBRELLA, new ResourceLocation("blocking"), (stack, level, entity, i)
				-> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1 : 0);
	}

	public static void registerTextures() {
		ClientSpriteRegistryCallback.event(Sheets.CHEST_SHEET).register((texture, registry) -> {
			registry.register(TerraCraft.id("block/chests/gold/gold_chest"));
			registry.register(TerraCraft.id("block/chests/frozen/frozen_chest"));
			registry.register(TerraCraft.id("block/chests/ivy/ivy_chest"));
			registry.register(TerraCraft.id("block/chests/sandstone/sandstone_chest"));
			registry.register(TerraCraft.id("block/chests/water/water_chest"));
			registry.register(TerraCraft.id("block/chests/skyware/skyware_chest"));
			registry.register(TerraCraft.id("block/chests/shadow/shadow_chest"));
		});
	}
}
