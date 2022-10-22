package terramine.common.init;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import terramine.client.render.entity.model.*;
import terramine.client.render.entity.model.monsters.*;
import terramine.client.render.entity.model.throwables.BombModel;
import terramine.client.render.entity.model.throwables.DynamiteModel;
import terramine.client.render.entity.model.throwables.GrenadeModel;
import terramine.client.render.trinket.model.*;

public class ModLayerDefinitions {

    public static void registerAll() {
        register(ModModelLayers.MIMIC, MimicModel::createLayer);
        register(ModModelLayers.MIMIC_OVERLAY, MimicChestLayerModel::createLayer);

        register(ModModelLayers.FALLING_STAR, FallingStarModel::createLayer);

        register(ModModelLayers.DEMON_EYE, DemonEyeModel::createLayer);
        register(ModModelLayers.EATER_OF_SOULS, EaterOfSoulsModel::createLayer);
        register(ModModelLayers.DEVOURER, DevourerModel::createLayer);
        register(ModModelLayers.CRIMERA, CrimeraModel::createLayer);

        register(ModModelLayers.DYNAMITE, DynamiteModel::createLayer);
        register(ModModelLayers.GRENADE, GrenadeModel::createLayer);
        register(ModModelLayers.BOMB, BombModel::createLayer);

        register(ModModelLayers.DIVING_HELMET, layer(HeadModel.createDivingHelmet(), 64, 32));
        register(ModModelLayers.SUPERSTITIOUS_HAT, layer(HeadModel.createSuperstitiousHat(), 64, 32));
        register(ModModelLayers.VILLAGER_HAT, layer(HeadModel.createVillagerHat(), 32, 32));

        register(ModModelLayers.SCARF, layer(ScarfModel.createScarf(), 64, 32));
        register(ModModelLayers.CROSS_NECKLACE, layer(NecklaceModel.createCrossNecklace(), 64, 48));
        register(ModModelLayers.PANIC_NECKLACE, layer(NecklaceModel.createPanicNecklace(), 64, 48));

        register(ModModelLayers.CLOUD_IN_A_BOTTLE, layer(BeltModel.createCloudInABottle(), 32, 32));
        register(ModModelLayers.OBSIDIAN_SKULL, layer(BeltModel.createObsidianSkull(), 32, 32));
        register(ModModelLayers.UNIVERSAL_ATTRACTOR, layer(BeltModel.createUniversalAttractor(), 32, 32));

        register(ModModelLayers.CLAWS, layer(ArmsModel.createClaws(false), 32, 16));
        register(ModModelLayers.SLIM_CLAWS, layer(ArmsModel.createClaws(true), 32, 16));
        register(ModModelLayers.GLOVE, layer(ArmsModel.createSleevedArms(false), 32, 32));
        register(ModModelLayers.SLIM_GLOVE, layer(ArmsModel.createSleevedArms(true), 32, 32));
        register(ModModelLayers.GOLDEN_HOOK, layer(ArmsModel.createGoldenHook(false), 64, 32));
        register(ModModelLayers.SLIM_GOLDEN_HOOK, layer(ArmsModel.createGoldenHook(true), 64, 32));

        register(ModModelLayers.AQUA_DASHERS, layer(LegsModel.createAquaDashers(), 32, 32));
        register(ModModelLayers.RUNNING_SHOES, layer(LegsModel.createRunningShoes(), 32, 32));
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
