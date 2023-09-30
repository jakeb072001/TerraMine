package terramine.client.render.accessory;

import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import terramine.TerraMine;
import terramine.client.render.AccessoryRenderRegistry;
import terramine.client.render.AccessoryRenderer;
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
        register(ModItems.DIVING_HELMET, new BaseAccessoryRenderer("diving_helmet", new HeadModel(bakeLayer(ModModelLayers.DIVING_HELMET), RenderType::entityTranslucent)));

        // necklace
        register(ModItems.CROSS_NECKLACE, new BaseAccessoryRenderer("cross_necklace", new NecklaceModel(bakeLayer(ModModelLayers.CROSS_NECKLACE))));
        register(ModItems.PANIC_NECKLACE, new BaseAccessoryRenderer("panic_necklace", new NecklaceModel(bakeLayer(ModModelLayers.PANIC_NECKLACE))));

        // belt
        register(ModItems.CLOUD_IN_A_BOTTLE, new BeltAccessoryRenderer("cloud_in_a_bottle", BeltModel.createCloudInABottleModel()));
        register(ModItems.OBSIDIAN_SKULL, new BeltAccessoryRenderer("obsidian_skull", BeltModel.createObsidianSkullModel()));
        register(ModItems.TREASURE_MAGNET, new BeltAccessoryRenderer("universal_attractor", BeltModel.createUniversalAttractorModel()));

        // hands
        register(ModItems.FERAL_CLAWS, new GloveAccessoryRenderer("claws/feral_claws", "claws/feral_claws", ArmsModel.createClawsModel(false), ArmsModel.createClawsModel(true)));
        register(ModItems.POWER_GLOVE, new GloveAccessoryRenderer("power_glove", ArmsModel.createGloveModel(false), ArmsModel.createGloveModel(true)));
        register(ModItems.FIRE_GAUNTLET, new GlowingGloveAccessoryRenderer("fire_gauntlet", ArmsModel.createGloveModel(false), ArmsModel.createGloveModel(true)));

        // feet
        register(ModItems.LAVA_WADERS, new BaseAccessoryRenderer("aqua_dashers", new LegsModel(bakeLayer(ModModelLayers.AQUA_DASHERS))));
        register(ModItems.HERMES_BOOTS, new BaseAccessoryRenderer("running_shoes", new LegsModel(bakeLayer(ModModelLayers.RUNNING_SHOES))));
        register(ModItems.FLIPPERS, new BaseAccessoryRenderer("flippers", new LegsModel(bakeLayer(ModModelLayers.FLIPPERS))));

        // curio
        register(ModItems.WHOOPEE_CUSHION, new BaseAccessoryRenderer("whoopee_cushion", new HeadModel(bakeLayer(ModModelLayers.WHOOPEE_CUSHION))));
    }

    public static ModelPart bakeLayer(ModelLayerLocation layerLocation) {
        return Minecraft.getInstance().getEntityModels().bakeLayer(layerLocation);
    }

    private void register(Item item, AccessoryRenderer renderer) {
        AccessoryRenderRegistry.registerRenderer(item, renderer);
    }

    @Override
    public ResourceLocation getFabricId() {
        return TerraMine.id("accessory_renderers");
    }
}
