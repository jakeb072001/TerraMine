package terracraft.common.init;

import terracraft.client.render.entity.model.DemonEyeModel;
import terracraft.client.render.entity.model.FallingStarModel;
import terracraft.client.render.entity.model.MimicChestLayerModel;
import terracraft.client.render.entity.model.MimicModel;
import terracraft.client.render.trinket.model.ArmsModel;
import terracraft.client.render.trinket.model.BeltModel;
import terracraft.client.render.trinket.model.HeadModel;
import terracraft.client.render.trinket.model.LegsModel;
import terracraft.client.render.trinket.model.NecklaceModel;
import terracraft.client.render.trinket.model.ScarfModel;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;

public class LayerDefinitions {

    public static void registerAll() {
        register(ModelLayers.MIMIC, MimicModel::createLayer);
        register(ModelLayers.MIMIC_OVERLAY, MimicChestLayerModel::createLayer);

        register(ModelLayers.FALLING_STAR, FallingStarModel::createLayer);

        register(ModelLayers.DEMON_EYE, DemonEyeModel::createLayer);

        register(ModelLayers.DRINKING_HAT, layer(HeadModel.createDrinkingHat(), 64, 32));
        register(ModelLayers.SNORKEL, layer(HeadModel.createSnorkel(), 64, 32));
        register(ModelLayers.NIGHT_VISION_GOGGLES, layer(HeadModel.createNightVisionGoggles(), 32, 32));
        register(ModelLayers.SUPERSTITIOUS_HAT, layer(HeadModel.createSuperstitiousHat(), 64, 32));
        register(ModelLayers.VILLAGER_HAT, layer(HeadModel.createVillagerHat(), 32, 32));

        register(ModelLayers.SCARF, layer(ScarfModel.createScarf(), 64, 32));
        register(ModelLayers.CROSS_NECKLACE, layer(NecklaceModel.createCrossNecklace(), 64, 48));
        register(ModelLayers.PANIC_NECKLACE, layer(NecklaceModel.createPanicNecklace(), 64, 48));
        register(ModelLayers.PENDANT, layer(NecklaceModel.createPendant(), 64, 48));
        register(ModelLayers.CHARM_OF_SINKING, layer(NecklaceModel.createCharmOfSinking(), 64, 48));

        register(ModelLayers.CLOUD_IN_A_BOTTLE, layer(BeltModel.createCloudInABottle(), 32, 32));
        register(ModelLayers.OBSIDIAN_SKULL, layer(BeltModel.createObsidianSkull(), 32, 32));
        register(ModelLayers.ANTIDOTE_VESSEL, layer(BeltModel.createAntidoteVessel(), 32, 32));
        register(ModelLayers.UNIVERSAL_ATTRACTOR, layer(BeltModel.createUniversalAttractor(), 32, 32));
        register(ModelLayers.CRYSTAL_HEART, layer(BeltModel.createCrystalHeart(), 32, 32));
        register(ModelLayers.HELIUM_FLAMINGO, layer(BeltModel.createHeliumFlamingo(), 64, 64));

        register(ModelLayers.CLAWS, layer(ArmsModel.createClaws(false), 32, 16));
        register(ModelLayers.SLIM_CLAWS, layer(ArmsModel.createClaws(true), 32, 16));
        register(ModelLayers.GLOVE, layer(ArmsModel.createSleevedArms(false), 32, 32));
        register(ModelLayers.SLIM_GLOVE, layer(ArmsModel.createSleevedArms(true), 32, 32));
        register(ModelLayers.GOLDEN_HOOK, layer(ArmsModel.createGoldenHook(false), 64, 32));
        register(ModelLayers.SLIM_GOLDEN_HOOK, layer(ArmsModel.createGoldenHook(true), 64, 32));

        register(ModelLayers.AQUA_DASHERS, layer(LegsModel.createAquaDashers(), 32, 32));
        register(ModelLayers.BUNNY_HOPPERS, layer(LegsModel.createBunnyHoppers(), 64, 32));
        register(ModelLayers.KITTY_SLIPPERS, layer(LegsModel.createKittySlippers(), 64, 32));
        register(ModelLayers.RUNNING_SHOES, layer(LegsModel.createRunningShoes(), 32, 32));
        register(ModelLayers.STEADFAST_SPIKES, layer(LegsModel.createSteadfastSpikes(), 64, 32));
        register(ModelLayers.FLIPPERS, layer(LegsModel.createFlippers(), 64, 64));

        register(ModelLayers.WHOOPEE_CUSHION, layer(HeadModel.createWhoopeeCushion(), 32, 16));
    }

    private static EntityModelLayerRegistry.TexturedModelDataProvider layer(MeshDefinition mesh, int textureWidth, int textureHeight) {
        return () -> LayerDefinition.create(mesh, textureWidth, textureHeight);
    }

    private static void register(ModelLayerLocation location, EntityModelLayerRegistry.TexturedModelDataProvider provider) {
        EntityModelLayerRegistry.registerModelLayer(location, provider);
    }
}
