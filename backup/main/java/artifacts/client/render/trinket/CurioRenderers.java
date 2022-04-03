package terracraft.client.render.trinket;

import terracraft.Artifacts;
import terracraft.client.render.trinket.model.ArmsModel;
import terracraft.client.render.trinket.model.BeltModel;
import terracraft.client.render.trinket.model.HeadModel;
import terracraft.client.render.trinket.model.LegsModel;
import terracraft.client.render.trinket.model.NecklaceModel;
import terracraft.client.render.trinket.model.ScarfModel;
import terracraft.client.render.trinket.renderer.BeltCurioRenderer;
import terracraft.client.render.trinket.renderer.CurioRenderer;
import terracraft.client.render.trinket.renderer.GloveCurioRenderer;
import terracraft.client.render.trinket.renderer.GlowingCurioRenderer;
import terracraft.client.render.trinket.renderer.GlowingGloveCurioRenderer;
import terracraft.common.init.Items;
import terracraft.common.init.ModelLayers;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

public class CurioRenderers implements SimpleSynchronousResourceReloadListener {

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
        // head
        TrinketRendererRegistry.registerRenderer(Items.PLASTIC_DRINKING_HAT, new CurioRenderer("drinking_hat/plastic_drinking_hat", new HeadModel(bakeLayer(ModelLayers.DRINKING_HAT))));
        TrinketRendererRegistry.registerRenderer(Items.NOVELTY_DRINKING_HAT, new CurioRenderer("drinking_hat/novelty_drinking_hat", new HeadModel(bakeLayer(ModelLayers.DRINKING_HAT))));
        TrinketRendererRegistry.registerRenderer(Items.SNORKEL, new CurioRenderer("snorkel", new HeadModel(bakeLayer(ModelLayers.SNORKEL), RenderType::entityTranslucent)));
        TrinketRendererRegistry.registerRenderer(Items.NIGHT_VISION_GOGGLES, new GlowingCurioRenderer("night_vision_goggles", new HeadModel(bakeLayer(ModelLayers.NIGHT_VISION_GOGGLES))));
        TrinketRendererRegistry.registerRenderer(Items.SUPERSTITIOUS_HAT, new CurioRenderer("superstitious_hat", new HeadModel(bakeLayer(ModelLayers.SUPERSTITIOUS_HAT), RenderType::entityCutoutNoCull)));
        TrinketRendererRegistry.registerRenderer(Items.VILLAGER_HAT, new CurioRenderer("villager_hat", new HeadModel(bakeLayer(ModelLayers.VILLAGER_HAT))));

        // necklace
        TrinketRendererRegistry.registerRenderer(Items.LUCKY_SCARF, new CurioRenderer("scarf/lucky_scarf", new ScarfModel(bakeLayer(ModelLayers.SCARF), RenderType::entityCutoutNoCull)));
        TrinketRendererRegistry.registerRenderer(Items.SCARF_OF_INVISIBILITY, new CurioRenderer("scarf/scarf_of_invisibility",  new ScarfModel(bakeLayer(ModelLayers.SCARF), RenderType::entityTranslucent)));
        TrinketRendererRegistry.registerRenderer(Items.CROSS_NECKLACE, new CurioRenderer("cross_necklace", new NecklaceModel(bakeLayer(ModelLayers.CROSS_NECKLACE))));
        TrinketRendererRegistry.registerRenderer(Items.PANIC_NECKLACE, new CurioRenderer("panic_necklace", new NecklaceModel(bakeLayer(ModelLayers.PANIC_NECKLACE))));
        TrinketRendererRegistry.registerRenderer(Items.SHOCK_PENDANT, new CurioRenderer("pendant/shock_pendant", new NecklaceModel(bakeLayer(ModelLayers.PENDANT))));
        TrinketRendererRegistry.registerRenderer(Items.FLAME_PENDANT, new CurioRenderer("pendant/flame_pendant", new NecklaceModel(bakeLayer(ModelLayers.PENDANT))));
        TrinketRendererRegistry.registerRenderer(Items.THORN_PENDANT, new CurioRenderer("pendant/thorn_pendant", new NecklaceModel(bakeLayer(ModelLayers.PENDANT))));
        TrinketRendererRegistry.registerRenderer(Items.CHARM_OF_SINKING, new CurioRenderer("charm_of_sinking", new NecklaceModel(bakeLayer(ModelLayers.CHARM_OF_SINKING))));

        // belt
        TrinketRendererRegistry.registerRenderer(Items.CLOUD_IN_A_BOTTLE, new BeltCurioRenderer("cloud_in_a_bottle", BeltModel.createCloudInABottleModel()));
        TrinketRendererRegistry.registerRenderer(Items.OBSIDIAN_SKULL, new BeltCurioRenderer("obsidian_skull", BeltModel.createObsidianSkullModel()));
        TrinketRendererRegistry.registerRenderer(Items.ANTIDOTE_VESSEL, new BeltCurioRenderer("antidote_vessel", BeltModel.createAntidoteVesselModel()));
        TrinketRendererRegistry.registerRenderer(Items.UNIVERSAL_ATTRACTOR, new BeltCurioRenderer("universal_attractor", BeltModel.createUniversalAttractorModel()));
        TrinketRendererRegistry.registerRenderer(Items.CRYSTAL_HEART, new BeltCurioRenderer("crystal_heart", BeltModel.createCrystalHeartModel()));
        TrinketRendererRegistry.registerRenderer(Items.HELIUM_FLAMINGO, new CurioRenderer("helium_flamingo", BeltModel.createHeliumFlamingoModel()));

        // hands
        TrinketRendererRegistry.registerRenderer(Items.DIGGING_CLAWS, new GloveCurioRenderer("claws/digging_claws", "claws/digging_claws", ArmsModel.createClawsModel(false), ArmsModel.createClawsModel(true)));
        TrinketRendererRegistry.registerRenderer(Items.FERAL_CLAWS, new GloveCurioRenderer("claws/feral_claws", "claws/feral_claws", ArmsModel.createClawsModel(false), ArmsModel.createClawsModel(true)));
        TrinketRendererRegistry.registerRenderer(Items.POWER_GLOVE, new GloveCurioRenderer("power_glove", ArmsModel.createGloveModel(false), ArmsModel.createGloveModel(true)));
        TrinketRendererRegistry.registerRenderer(Items.FIRE_GAUNTLET, new GlowingGloveCurioRenderer("fire_gauntlet", ArmsModel.createGloveModel(false), ArmsModel.createGloveModel(true)));
        TrinketRendererRegistry.registerRenderer(Items.POCKET_PISTON, new GloveCurioRenderer("pocket_piston", ArmsModel.createGloveModel(false), ArmsModel.createGloveModel(true)));
        TrinketRendererRegistry.registerRenderer(Items.VAMPIRIC_GLOVE, new GloveCurioRenderer("vampiric_glove", ArmsModel.createGloveModel(false), ArmsModel.createGloveModel(true)));
        TrinketRendererRegistry.registerRenderer(Items.GOLDEN_HOOK, new GloveCurioRenderer("golden_hook/golden_hook_default", "golden_hook/golden_hook_slim", ArmsModel.createGoldenHookModel(false), ArmsModel.createGoldenHookModel(true)));

        // feet
        TrinketRendererRegistry.registerRenderer(Items.AQUA_DASHERS, new CurioRenderer("aqua_dashers", new LegsModel(bakeLayer(ModelLayers.AQUA_DASHERS))));
        TrinketRendererRegistry.registerRenderer(Items.KITTY_SLIPPERS, new CurioRenderer("kitty_slippers", new LegsModel(bakeLayer(ModelLayers.KITTY_SLIPPERS))));
        TrinketRendererRegistry.registerRenderer(Items.RUNNING_SHOES, new CurioRenderer("running_shoes", new LegsModel(bakeLayer(ModelLayers.RUNNING_SHOES))));
        TrinketRendererRegistry.registerRenderer(Items.STEADFAST_SPIKES, new CurioRenderer("steadfast_spikes", new LegsModel(bakeLayer(ModelLayers.STEADFAST_SPIKES))));
        TrinketRendererRegistry.registerRenderer(Items.FLIPPERS, new CurioRenderer("flippers", new LegsModel(bakeLayer(ModelLayers.FLIPPERS))));

        // curio
        TrinketRendererRegistry.registerRenderer(Items.WHOOPEE_CUSHION, new CurioRenderer("whoopee_cushion", new HeadModel(bakeLayer(ModelLayers.WHOOPEE_CUSHION))));
    }

    public static ModelPart bakeLayer(ModelLayerLocation layerLocation) {
        return Minecraft.getInstance().getEntityModels().bakeLayer(layerLocation);
    }

    @Override
    public ResourceLocation getFabricId() {
        return Artifacts.id("trinket_renderers");
    }
}
