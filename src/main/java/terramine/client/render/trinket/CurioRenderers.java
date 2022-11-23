package terramine.client.render.trinket;

import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.NotNull;
import terramine.TerraMine;
import terramine.client.render.trinket.model.*;
import terramine.client.render.trinket.renderer.BeltCurioRenderer;
import terramine.client.render.trinket.renderer.TrinketRenderer;
import terramine.client.render.trinket.renderer.GloveCurioRenderer;
import terramine.client.render.trinket.renderer.GlowingGloveCurioRenderer;
import terramine.common.init.ModItems;
import terramine.common.init.ModModelLayers;

public class CurioRenderers implements SimpleSynchronousResourceReloadListener {

    @Override
    public void onResourceManagerReload(@NotNull ResourceManager resourceManager) {
        // head
        TrinketRendererRegistry.registerRenderer(ModItems.DIVING_HELMET, new TrinketRenderer("diving_helmet", new HeadModel(bakeLayer(ModModelLayers.DIVING_HELMET), RenderType::entityTranslucent)));

        // necklace
        TrinketRendererRegistry.registerRenderer(ModItems.CROSS_NECKLACE, new TrinketRenderer("cross_necklace", new NecklaceModel(bakeLayer(ModModelLayers.CROSS_NECKLACE))));
        TrinketRendererRegistry.registerRenderer(ModItems.PANIC_NECKLACE, new TrinketRenderer("panic_necklace", new NecklaceModel(bakeLayer(ModModelLayers.PANIC_NECKLACE))));

        // belt
        TrinketRendererRegistry.registerRenderer(ModItems.CLOUD_IN_A_BOTTLE, new BeltCurioRenderer("cloud_in_a_bottle", BeltModel.createCloudInABottleModel()));
        TrinketRendererRegistry.registerRenderer(ModItems.OBSIDIAN_SKULL, new BeltCurioRenderer("obsidian_skull", BeltModel.createObsidianSkullModel()));
        TrinketRendererRegistry.registerRenderer(ModItems.TREASURE_MAGNET, new BeltCurioRenderer("universal_attractor", BeltModel.createUniversalAttractorModel()));

        // hands
        TrinketRendererRegistry.registerRenderer(ModItems.FERAL_CLAWS, new GloveCurioRenderer("claws/feral_claws", "claws/feral_claws", ArmsModel.createClawsModel(false), ArmsModel.createClawsModel(true)));
        TrinketRendererRegistry.registerRenderer(ModItems.POWER_GLOVE, new GloveCurioRenderer("power_glove", ArmsModel.createGloveModel(false), ArmsModel.createGloveModel(true)));
        TrinketRendererRegistry.registerRenderer(ModItems.FIRE_GAUNTLET, new GlowingGloveCurioRenderer("fire_gauntlet", ArmsModel.createGloveModel(false), ArmsModel.createGloveModel(true)));

        // feet
        TrinketRendererRegistry.registerRenderer(ModItems.LAVA_WADERS, new TrinketRenderer("aqua_dashers", new LegsModel(bakeLayer(ModModelLayers.AQUA_DASHERS))));
        TrinketRendererRegistry.registerRenderer(ModItems.HERMES_BOOTS, new TrinketRenderer("running_shoes", new LegsModel(bakeLayer(ModModelLayers.RUNNING_SHOES))));
        TrinketRendererRegistry.registerRenderer(ModItems.FLIPPERS, new TrinketRenderer("flippers", new LegsModel(bakeLayer(ModModelLayers.FLIPPERS))));

        // curio
        TrinketRendererRegistry.registerRenderer(ModItems.WHOOPEE_CUSHION, new TrinketRenderer("whoopee_cushion", new HeadModel(bakeLayer(ModModelLayers.WHOOPEE_CUSHION))));
    }

    public static ModelPart bakeLayer(ModelLayerLocation layerLocation) {
        return Minecraft.getInstance().getEntityModels().bakeLayer(layerLocation);
    }

    @Override
    public ResourceLocation getFabricId() {
        return TerraMine.id("trinket_renderers");
    }
}
