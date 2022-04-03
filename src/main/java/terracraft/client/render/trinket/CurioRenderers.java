package terracraft.client.render.trinket;

import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import terracraft.TerraCraft;
import terracraft.client.render.trinket.model.*;
import terracraft.client.render.trinket.renderer.*;
import terracraft.common.init.ModItems;
import terracraft.common.init.ModModelLayers;

public class CurioRenderers implements SimpleSynchronousResourceReloadListener {

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
        // head
        TrinketRendererRegistry.registerRenderer(ModItems.PLASTIC_DRINKING_HAT, new CurioRenderer("drinking_hat/plastic_drinking_hat", new HeadModel(bakeLayer(ModModelLayers.DRINKING_HAT))));
        TrinketRendererRegistry.registerRenderer(ModItems.NOVELTY_DRINKING_HAT, new CurioRenderer("drinking_hat/novelty_drinking_hat", new HeadModel(bakeLayer(ModModelLayers.DRINKING_HAT))));
        TrinketRendererRegistry.registerRenderer(ModItems.SNORKEL, new CurioRenderer("snorkel", new HeadModel(bakeLayer(ModModelLayers.SNORKEL), RenderType::entityTranslucent)));
        TrinketRendererRegistry.registerRenderer(ModItems.NIGHT_VISION_GOGGLES, new GlowingCurioRenderer("night_vision_goggles", new HeadModel(bakeLayer(ModModelLayers.NIGHT_VISION_GOGGLES))));
        TrinketRendererRegistry.registerRenderer(ModItems.SUPERSTITIOUS_HAT, new CurioRenderer("superstitious_hat", new HeadModel(bakeLayer(ModModelLayers.SUPERSTITIOUS_HAT), RenderType::entityCutoutNoCull)));
        TrinketRendererRegistry.registerRenderer(ModItems.VILLAGER_HAT, new CurioRenderer("villager_hat", new HeadModel(bakeLayer(ModModelLayers.VILLAGER_HAT))));

        // necklace
        TrinketRendererRegistry.registerRenderer(ModItems.LUCKY_SCARF, new CurioRenderer("scarf/lucky_scarf", new ScarfModel(bakeLayer(ModModelLayers.SCARF), RenderType::entityCutoutNoCull)));
        TrinketRendererRegistry.registerRenderer(ModItems.SCARF_OF_INVISIBILITY, new CurioRenderer("scarf/scarf_of_invisibility",  new ScarfModel(bakeLayer(ModModelLayers.SCARF), RenderType::entityTranslucent)));
        TrinketRendererRegistry.registerRenderer(ModItems.CROSS_NECKLACE, new CurioRenderer("cross_necklace", new NecklaceModel(bakeLayer(ModModelLayers.CROSS_NECKLACE))));
        TrinketRendererRegistry.registerRenderer(ModItems.PANIC_NECKLACE, new CurioRenderer("panic_necklace", new NecklaceModel(bakeLayer(ModModelLayers.PANIC_NECKLACE))));
        TrinketRendererRegistry.registerRenderer(ModItems.SHOCK_PENDANT, new CurioRenderer("pendant/shock_pendant", new NecklaceModel(bakeLayer(ModModelLayers.PENDANT))));
        TrinketRendererRegistry.registerRenderer(ModItems.FLAME_PENDANT, new CurioRenderer("pendant/flame_pendant", new NecklaceModel(bakeLayer(ModModelLayers.PENDANT))));
        TrinketRendererRegistry.registerRenderer(ModItems.THORN_PENDANT, new CurioRenderer("pendant/thorn_pendant", new NecklaceModel(bakeLayer(ModModelLayers.PENDANT))));
        TrinketRendererRegistry.registerRenderer(ModItems.CHARM_OF_SINKING, new CurioRenderer("charm_of_sinking", new NecklaceModel(bakeLayer(ModModelLayers.CHARM_OF_SINKING))));

        // belt
        TrinketRendererRegistry.registerRenderer(ModItems.CLOUD_IN_A_BOTTLE, new BeltCurioRenderer("cloud_in_a_bottle", BeltModel.createCloudInABottleModel()));
        TrinketRendererRegistry.registerRenderer(ModItems.OBSIDIAN_SKULL, new BeltCurioRenderer("obsidian_skull", BeltModel.createObsidianSkullModel()));
        TrinketRendererRegistry.registerRenderer(ModItems.ANTIDOTE_VESSEL, new BeltCurioRenderer("antidote_vessel", BeltModel.createAntidoteVesselModel()));
        TrinketRendererRegistry.registerRenderer(ModItems.UNIVERSAL_ATTRACTOR, new BeltCurioRenderer("universal_attractor", BeltModel.createUniversalAttractorModel()));
        TrinketRendererRegistry.registerRenderer(ModItems.CRYSTAL_HEART, new BeltCurioRenderer("crystal_heart", BeltModel.createCrystalHeartModel()));
        TrinketRendererRegistry.registerRenderer(ModItems.HELIUM_FLAMINGO, new CurioRenderer("helium_flamingo", BeltModel.createHeliumFlamingoModel()));

        // hands
        TrinketRendererRegistry.registerRenderer(ModItems.DIGGING_CLAWS, new GloveCurioRenderer("claws/digging_claws", "claws/digging_claws", ArmsModel.createClawsModel(false), ArmsModel.createClawsModel(true)));
        TrinketRendererRegistry.registerRenderer(ModItems.FERAL_CLAWS, new GloveCurioRenderer("claws/feral_claws", "claws/feral_claws", ArmsModel.createClawsModel(false), ArmsModel.createClawsModel(true)));
        TrinketRendererRegistry.registerRenderer(ModItems.POWER_GLOVE, new GloveCurioRenderer("power_glove", ArmsModel.createGloveModel(false), ArmsModel.createGloveModel(true)));
        TrinketRendererRegistry.registerRenderer(ModItems.FIRE_GAUNTLET, new GlowingGloveCurioRenderer("fire_gauntlet", ArmsModel.createGloveModel(false), ArmsModel.createGloveModel(true)));
        TrinketRendererRegistry.registerRenderer(ModItems.POCKET_PISTON, new GloveCurioRenderer("pocket_piston", ArmsModel.createGloveModel(false), ArmsModel.createGloveModel(true)));
        TrinketRendererRegistry.registerRenderer(ModItems.VAMPIRIC_GLOVE, new GloveCurioRenderer("vampiric_glove", ArmsModel.createGloveModel(false), ArmsModel.createGloveModel(true)));
        TrinketRendererRegistry.registerRenderer(ModItems.GOLDEN_HOOK, new GloveCurioRenderer("golden_hook/golden_hook_default", "golden_hook/golden_hook_slim", ArmsModel.createGoldenHookModel(false), ArmsModel.createGoldenHookModel(true)));

        // feet
        TrinketRendererRegistry.registerRenderer(ModItems.AQUA_DASHERS, new CurioRenderer("aqua_dashers", new LegsModel(bakeLayer(ModModelLayers.AQUA_DASHERS))));
        TrinketRendererRegistry.registerRenderer(ModItems.KITTY_SLIPPERS, new CurioRenderer("kitty_slippers", new LegsModel(bakeLayer(ModModelLayers.KITTY_SLIPPERS))));
        TrinketRendererRegistry.registerRenderer(ModItems.RUNNING_SHOES, new CurioRenderer("running_shoes", new LegsModel(bakeLayer(ModModelLayers.RUNNING_SHOES))));
        TrinketRendererRegistry.registerRenderer(ModItems.STEADFAST_SPIKES, new CurioRenderer("steadfast_spikes", new LegsModel(bakeLayer(ModModelLayers.STEADFAST_SPIKES))));
        TrinketRendererRegistry.registerRenderer(ModItems.FLIPPERS, new CurioRenderer("flippers", new LegsModel(bakeLayer(ModModelLayers.FLIPPERS))));

        // curio
        TrinketRendererRegistry.registerRenderer(ModItems.WHOOPEE_CUSHION, new CurioRenderer("whoopee_cushion", new HeadModel(bakeLayer(ModModelLayers.WHOOPEE_CUSHION))));
    }

    public static ModelPart bakeLayer(ModelLayerLocation layerLocation) {
        return Minecraft.getInstance().getEntityModels().bakeLayer(layerLocation);
    }

    @Override
    public ResourceLocation getFabricId() {
        return TerraCraft.id("trinket_renderers");
    }
}
