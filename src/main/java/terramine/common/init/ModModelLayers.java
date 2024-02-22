package terramine.common.init;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.resources.ResourceLocation;
import terramine.TerraMine;
import terramine.client.render.accessory.model.*;
import terramine.client.render.entity.model.misc.FallingStarModel;
import terramine.client.render.entity.model.misc.MeteoriteModel;
import terramine.client.render.entity.model.mobs.bosses.TestBossModel;
import terramine.client.render.entity.model.mobs.hardmode.MimicChestLayerModel;
import terramine.client.render.entity.model.mobs.hardmode.MimicModel;
import terramine.client.render.entity.model.mobs.prehardmode.CrimeraModel;
import terramine.client.render.entity.model.mobs.prehardmode.DemonEyeModel;
import terramine.client.render.entity.model.mobs.prehardmode.DevourerModel;
import terramine.client.render.entity.model.mobs.prehardmode.EaterOfSoulsModel;
import terramine.client.render.entity.model.projectiles.magic.LaserModel;
import terramine.client.render.entity.model.projectiles.throwables.BombModel;
import terramine.client.render.entity.model.projectiles.throwables.DynamiteModel;
import terramine.client.render.entity.model.projectiles.throwables.GrenadeModel;

public class ModModelLayers {

    // Enemies
    public static final ModelLayerLocation MIMIC = register(createLocation("mimic"), MimicModel::createLayer);
    public static final ModelLayerLocation MIMIC_OVERLAY = register(createLocation("mimic", "overlay"), MimicChestLayerModel::createLayer);
    public static final ModelLayerLocation DEMON_EYE = register(createLocation("demon_eye"), DemonEyeModel::createLayer);
    public static final ModelLayerLocation EATER_OF_SOULS = register(createLocation("eater_of_souls"), EaterOfSoulsModel::createLayer);
    public static final ModelLayerLocation DEVOURER = register(createLocation("devourer"), DevourerModel::createLayer);
    public static final ModelLayerLocation CRIMERA = register(createLocation("crimera"), CrimeraModel::createLayer);

    /**
     * Testing, remove later
     */
    public static final ModelLayerLocation TEST_BOSS = register(createLocation("test_boss"), TestBossModel::createLayer);

    // Misc
    public static final ModelLayerLocation FALLING_STAR = register(createLocation("falling_star"), FallingStarModel::createLayer);
    public static final ModelLayerLocation METEORITE = register(createLocation("meteorite"), MeteoriteModel::createLayer);

    // Projectiles
    public static final ModelLayerLocation LASER = register(createLocation("laser"), LaserModel::createLayer);
    public static final ModelLayerLocation DYNAMITE = register(createLocation("dynamite"), DynamiteModel::createLayer);
    public static final ModelLayerLocation GRENADE = register(createLocation("grenade"), GrenadeModel::createLayer);
    public static final ModelLayerLocation BOMB = register(createLocation("bomb"), BombModel::createLayer);

    // Vanity
    public static final ModelLayerLocation TOP_HAT = register(createLocation("top_hat"), layer(HeadModel.createTopHat(), 64, 32));

    // Accessories
    public static final ModelLayerLocation DIVING_HELMET = register(createLocation("diving_helmet"), layer(HeadModel.createDivingHelmet(), 64, 32));

    public static final ModelLayerLocation CROSS_NECKLACE = register(createLocation("cross_necklace"), layer(NecklaceModel.createCrossNecklace(), 64, 48));
    public static final ModelLayerLocation PANIC_NECKLACE = register(createLocation("panic_necklace"), layer(NecklaceModel.createPanicNecklace(), 64, 48));

    public static final ModelLayerLocation CLOUD_IN_A_BOTTLE = register(createLocation("cloud_in_a_bottle"), layer(BeltModel.createCloudInABottle(), 32, 32));
    public static final ModelLayerLocation OBSIDIAN_SKULL = register(createLocation("obsidian_skull"), layer(BeltModel.createObsidianSkull(), 32, 32));
    public static final ModelLayerLocation UNIVERSAL_ATTRACTOR = register(createLocation("universal_attractor"), layer(BeltModel.createUniversalAttractor(), 32, 32));

    public static final ModelLayerLocation CLAWS = register(createLocation("claws"), layer(ArmsModel.createClaws(false), 32, 16));
    public static final ModelLayerLocation SLIM_CLAWS = register(createLocation("slim_claws"), layer(ArmsModel.createClaws(true), 32, 16));
    public static final ModelLayerLocation GLOVE = register(createLocation("gloves"), layer(ArmsModel.createSleevedArms(false), 32, 32));
    public static final ModelLayerLocation SLIM_GLOVE = register(createLocation("slim_gloves"), layer(ArmsModel.createSleevedArms(true), 32, 32));

    public static final ModelLayerLocation AQUA_DASHERS = register(createLocation("aqua_dashers"), layer(LegsModel.createAquaDashers(), 32, 32));
    public static final ModelLayerLocation RUNNING_SHOES = register(createLocation("running_shoes"), layer(LegsModel.createRunningShoes(), 32, 32));
    public static final ModelLayerLocation FLIPPERS = register(createLocation("flippers"), layer(LegsModel.createFlippers(), 64, 64));

    public static final ModelLayerLocation WHOOPEE_CUSHION = register(createLocation("whoopee_cushion"), layer(HeadModel.createWhoopeeCushion(), 32, 16));

    private static ModelLayerLocation createLocation(String model) {
        return createLocation(model, "main");
    }

    @SuppressWarnings("SameParameterValue")
    private static ModelLayerLocation createLocation(String model, String layer) {
        return new ModelLayerLocation(new ResourceLocation(TerraMine.MOD_ID, model), layer);
    }

    private static ModelLayerLocation register(ModelLayerLocation location, EntityModelLayerRegistry.TexturedModelDataProvider provider) {
        EntityModelLayerRegistry.registerModelLayer(location, provider);
        return location;
    }

    private static EntityModelLayerRegistry.TexturedModelDataProvider layer(MeshDefinition mesh, int textureWidth, int textureHeight) {
        return () -> LayerDefinition.create(mesh, textureWidth, textureHeight);
    }

    public static ModelLayerLocation claws(boolean smallArms) {
        return smallArms ? SLIM_CLAWS : CLAWS;
    }

    public static ModelLayerLocation glove(boolean smallArms) {
        return smallArms ? SLIM_GLOVE : GLOVE;
    }
}
