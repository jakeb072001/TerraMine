package terramine.client.render.accessory;

import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.NotNull;
import terramine.TerraMine;
import terramine.client.render.AccessoryRenderRegistry;
import terramine.client.render.accessory.model.*;
import terramine.client.render.accessory.renderer.BaseAccessoryRenderer;
import terramine.client.render.accessory.renderer.BeltAccessoryRenderer;
import terramine.client.render.accessory.renderer.GloveAccessoryRenderer;
import terramine.client.render.accessory.renderer.GlowingGloveAccessoryRenderer;
import terramine.common.init.ModItems;
import terramine.common.init.ModModelLayers;

public class AccessoryRenderers implements SimpleSynchronousResourceReloadListener {

    // todo: add all the accessories, gonna be a lot of models and textures I need to make, rip
    @Override
    public void onResourceManagerReload(@NotNull ResourceManager resourceManager) {
        // head
        AccessoryRenderRegistry.registerRenderer(ModItems.DIVING_HELMET, new BaseAccessoryRenderer("diving_helmet", new HeadModel(bakeLayer(ModModelLayers.DIVING_HELMET), RenderType::entityTranslucent)));

        // necklace
        AccessoryRenderRegistry.registerRenderer(ModItems.CROSS_NECKLACE, new BaseAccessoryRenderer("cross_necklace", new NecklaceModel(bakeLayer(ModModelLayers.CROSS_NECKLACE))));
        AccessoryRenderRegistry.registerRenderer(ModItems.PANIC_NECKLACE, new BaseAccessoryRenderer("panic_necklace", new NecklaceModel(bakeLayer(ModModelLayers.PANIC_NECKLACE))));

        // belt
        AccessoryRenderRegistry.registerRenderer(ModItems.CLOUD_IN_A_BOTTLE, new BeltAccessoryRenderer("cloud_in_a_bottle", BeltModel.createCloudInABottleModel()));
        AccessoryRenderRegistry.registerRenderer(ModItems.OBSIDIAN_SKULL, new BeltAccessoryRenderer("obsidian_skull", BeltModel.createObsidianSkullModel()));
        AccessoryRenderRegistry.registerRenderer(ModItems.TREASURE_MAGNET, new BeltAccessoryRenderer("universal_attractor", BeltModel.createUniversalAttractorModel()));

        // hands
        AccessoryRenderRegistry.registerRenderer(ModItems.FERAL_CLAWS, new GloveAccessoryRenderer("claws/feral_claws", "claws/feral_claws", ArmsModel.createClawsModel(false), ArmsModel.createClawsModel(true)));
        AccessoryRenderRegistry.registerRenderer(ModItems.POWER_GLOVE, new GloveAccessoryRenderer("power_glove", ArmsModel.createGloveModel(false), ArmsModel.createGloveModel(true)));
        AccessoryRenderRegistry.registerRenderer(ModItems.FIRE_GAUNTLET, new GlowingGloveAccessoryRenderer("fire_gauntlet", ArmsModel.createGloveModel(false), ArmsModel.createGloveModel(true)));

        // feet
        AccessoryRenderRegistry.registerRenderer(ModItems.LAVA_WADERS, new BaseAccessoryRenderer("aqua_dashers", new LegsModel(bakeLayer(ModModelLayers.AQUA_DASHERS))));
        AccessoryRenderRegistry.registerRenderer(ModItems.HERMES_BOOTS, new BaseAccessoryRenderer("running_shoes", new LegsModel(bakeLayer(ModModelLayers.RUNNING_SHOES))));
        AccessoryRenderRegistry.registerRenderer(ModItems.FLIPPERS, new BaseAccessoryRenderer("flippers", new LegsModel(bakeLayer(ModModelLayers.FLIPPERS))));

        // curio
        AccessoryRenderRegistry.registerRenderer(ModItems.WHOOPEE_CUSHION, new BaseAccessoryRenderer("whoopee_cushion", new HeadModel(bakeLayer(ModModelLayers.WHOOPEE_CUSHION))));
    }

    public static ModelPart bakeLayer(ModelLayerLocation layerLocation) {
        return Minecraft.getInstance().getEntityModels().bakeLayer(layerLocation);
    }

    @Override
    public ResourceLocation getFabricId() {
        return TerraMine.id("accessory_renderers");
    }
}
