package terracraft.common.init;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import terracraft.client.render.entity.model.DemonEyeModel;
import terracraft.client.render.entity.model.FallingStarModel;
import terracraft.client.render.entity.model.MimicChestLayerModel;
import terracraft.client.render.entity.model.MimicModel;
import terracraft.client.render.trinket.model.*;

public class ModLayerDefinitions {

    public static void registerAll() {
        register(ModModelLayers.MIMIC, MimicModel::createLayer);
        register(ModModelLayers.MIMIC_OVERLAY, MimicChestLayerModel::createLayer);

        register(ModModelLayers.FALLING_STAR, FallingStarModel::createLayer);

        register(ModModelLayers.DEMON_EYE, DemonEyeModel::createLayer);

        register(ModModelLayers.DRINKING_HAT, layer(HeadModel.createDrinkingHat(), 64, 32));
        register(ModModelLayers.SNORKEL, layer(HeadModel.createSnorkel(), 64, 32));
        register(ModModelLayers.NIGHT_VISION_GOGGLES, layer(HeadModel.createNightVisionGoggles(), 32, 32));
        register(ModModelLayers.SUPERSTITIOUS_HAT, layer(HeadModel.createSuperstitiousHat(), 64, 32));
        register(ModModelLayers.VILLAGER_HAT, layer(HeadModel.createVillagerHat(), 32, 32));

        register(ModModelLayers.SCARF, layer(ScarfModel.createScarf(), 64, 32));
        register(ModModelLayers.CROSS_NECKLACE, layer(NecklaceModel.createCrossNecklace(), 64, 48));
        register(ModModelLayers.PANIC_NECKLACE, layer(NecklaceModel.createPanicNecklace(), 64, 48));
        register(ModModelLayers.PENDANT, layer(NecklaceModel.createPendant(), 64, 48));
        register(ModModelLayers.CHARM_OF_SINKING, layer(NecklaceModel.createCharmOfSinking(), 64, 48));

        register(ModModelLayers.CLOUD_IN_A_BOTTLE, layer(BeltModel.createCloudInABottle(), 32, 32));
        register(ModModelLayers.OBSIDIAN_SKULL, layer(BeltModel.createObsidianSkull(), 32, 32));
        register(ModModelLayers.ANTIDOTE_VESSEL, layer(BeltModel.createAntidoteVessel(), 32, 32));
        register(ModModelLayers.UNIVERSAL_ATTRACTOR, layer(BeltModel.createUniversalAttractor(), 32, 32));
        register(ModModelLayers.CRYSTAL_HEART, layer(BeltModel.createCrystalHeart(), 32, 32));
        register(ModModelLayers.HELIUM_FLAMINGO, layer(BeltModel.createHeliumFlamingo(), 64, 64));

        register(ModModelLayers.CLAWS, layer(ArmsModel.createClaws(false), 32, 16));
        register(ModModelLayers.SLIM_CLAWS, layer(ArmsModel.createClaws(true), 32, 16));
        register(ModModelLayers.GLOVE, layer(ArmsModel.createSleevedArms(false), 32, 32));
        register(ModModelLayers.SLIM_GLOVE, layer(ArmsModel.createSleevedArms(true), 32, 32));
        register(ModModelLayers.GOLDEN_HOOK, layer(ArmsModel.createGoldenHook(false), 64, 32));
        register(ModModelLayers.SLIM_GOLDEN_HOOK, layer(ArmsModel.createGoldenHook(true), 64, 32));

        register(ModModelLayers.AQUA_DASHERS, layer(LegsModel.createAquaDashers(), 32, 32));
        register(ModModelLayers.BUNNY_HOPPERS, layer(LegsModel.createBunnyHoppers(), 64, 32));
        register(ModModelLayers.KITTY_SLIPPERS, layer(LegsModel.createKittySlippers(), 64, 32));
        register(ModModelLayers.RUNNING_SHOES, layer(LegsModel.createRunningShoes(), 32, 32));
        register(ModModelLayers.STEADFAST_SPIKES, layer(LegsModel.createSteadfastSpikes(), 64, 32));
        register(ModModelLayers.FLIPPERS, layer(LegsModel.createFlippers(), 64, 64));

        register(ModModelLayers.WHOOPEE_CUSHION, layer(HeadModel.createWhoopeeCushion(), 32, 16));
    }

    private static EntityModelLayerRegistry.TexturedModelDataProvider layer(MeshDefinition mesh, int textureWidth, int textureHeight) {
        return () -> LayerDefinition.create(mesh, textureWidth, textureHeight);
    }

    private static void register(ModelLayerLocation location, EntityModelLayerRegistry.TexturedModelDataProvider provider) {
        EntityModelLayerRegistry.registerModelLayer(location, provider);
    }
}
