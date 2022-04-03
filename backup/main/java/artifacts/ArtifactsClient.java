package terracraft;

import terracraft.client.render.entity.DemonEyeRenderer;
import terracraft.client.render.entity.FallingStarRenderer;
import terracraft.client.render.entity.MagicMissileRenderer;
import terracraft.client.render.entity.MimicRenderer;
import terracraft.client.render.trinket.CurioRenderers;
import terracraft.common.init.ModBlocks;
import terracraft.common.init.Entities;
import terracraft.common.init.Items;
import terracraft.common.init.LayerDefinitions;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.particle.PlayerCloudParticle;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;

@Environment(EnvType.CLIENT)
public class ArtifactsClient implements ClientModInitializer {

	private static final ModelResourceLocation UMBRELLA_HELD_MODEL = new ModelResourceLocation(Artifacts.id("umbrella_in_hand"), "inventory");

	@Override
	public void onInitializeClient() {
		/* Registers our particle client-side.
		 * First argument is our particle's instance, created previously on ExampleMod.
		 * Second argument is the particle's factory. The factory controls how the particle behaves.
		 * In this example, we'll use FlameParticle's Factory.*/
		ParticleFactoryRegistry.getInstance().register(Artifacts.BLUE_POOF, PlayerCloudParticle.Provider::new);
		ParticleFactoryRegistry.getInstance().register(Artifacts.GREEN_SPARK, FlameParticle.Provider::new);

		// Adds Built-In ResourcePack
		FabricLoader.getInstance().getModContainer(Artifacts.MOD_ID).ifPresent(container -> {
			ResourceManagerHelper.registerBuiltinResourcePack(Artifacts.id("terracraft_ctm"), container, ResourcePackActivationType.NORMAL);
		});

		// Entity Renderers
		EntityRendererRegistry.register(Entities.MIMIC, MimicRenderer::new);
		EntityRendererRegistry.register(Entities.DEMON_EYE, DemonEyeRenderer::new);
		EntityRendererRegistry.register(Entities.FALLING_STAR, FallingStarRenderer::new);
		EntityRendererRegistry.register(Entities.MAGIC_MISSILE, MagicMissileRenderer::new);

		// Block RenderLayer
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CORRUPTED_GLASS, RenderType.translucent());
		BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CORRUPTED_ICE, RenderType.translucent());

		// Entity models
		LayerDefinitions.registerAll();
		ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new CurioRenderers());

		// Held Umbrella model
		ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(UMBRELLA_HELD_MODEL));

		// ModelPredicateProvider for rendering of umbrella blocking
		FabricModelPredicateProviderRegistry.register(Items.UMBRELLA, new ResourceLocation("blocking"), (stack, level, entity, i)
				-> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1 : 0);
	}
}
