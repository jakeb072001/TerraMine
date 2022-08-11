package terramine.common.init;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import terramine.TerraMine;

public class ModModelLayers {

    public static final ModelLayerLocation MIMIC = createLocation("mimic");
    public static final ModelLayerLocation MIMIC_OVERLAY = createLocation("mimic", "overlay");

    public static final ModelLayerLocation FALLING_STAR = createLocation("falling_star");

    public static final ModelLayerLocation DEMON_EYE = createLocation("demon_eye");
    public static final ModelLayerLocation EATER_OF_SOULS = createLocation("eater_of_souls");
    public static final ModelLayerLocation CRIMERA = createLocation("crimera");

    public static final ModelLayerLocation DIVING_HELMET = createLocation("diving_helmet");
    public static final ModelLayerLocation SUPERSTITIOUS_HAT = createLocation("superstitious_hat");
    public static final ModelLayerLocation VILLAGER_HAT = createLocation("villager_hat");

    public static final ModelLayerLocation SCARF = createLocation("scarf");
    public static final ModelLayerLocation CROSS_NECKLACE = createLocation("cross_necklace");
    public static final ModelLayerLocation PANIC_NECKLACE = createLocation("panic_necklace");

    public static final ModelLayerLocation CLOUD_IN_A_BOTTLE = createLocation("cloud_in_a_bottle");
    public static final ModelLayerLocation OBSIDIAN_SKULL = createLocation("obsidian_skull");
    public static final ModelLayerLocation UNIVERSAL_ATTRACTOR = createLocation("universal_attractor");

    public static final ModelLayerLocation CLAWS = createLocation("claws");
    public static final ModelLayerLocation SLIM_CLAWS = createLocation("slim_claws");
    public static final ModelLayerLocation GLOVE = createLocation("gloves");
    public static final ModelLayerLocation SLIM_GLOVE = createLocation("slim_gloves");
    public static final ModelLayerLocation GOLDEN_HOOK = createLocation("golden_hook");
    public static final ModelLayerLocation SLIM_GOLDEN_HOOK = createLocation("slim_golden_hook");

    public static final ModelLayerLocation AQUA_DASHERS = createLocation("aqua_dashers");
    public static final ModelLayerLocation RUNNING_SHOES = createLocation("running_shoes");
    public static final ModelLayerLocation FLIPPERS = createLocation("flippers");

    public static final ModelLayerLocation WHOOPEE_CUSHION = createLocation("whoopee_cushion");

    private static ModelLayerLocation createLocation(String model) {
        return createLocation(model, "main");
    }

    @SuppressWarnings("SameParameterValue")
    private static ModelLayerLocation createLocation(String model, String layer) {
        return new ModelLayerLocation(new ResourceLocation(TerraMine.MOD_ID, model), layer);
    }

    public static ModelLayerLocation claws(boolean smallArms) {
        return smallArms ? SLIM_CLAWS : CLAWS;
    }

    public static ModelLayerLocation glove(boolean smallArms) {
        return smallArms ? SLIM_GLOVE : GLOVE;
    }

    public static ModelLayerLocation goldenHook(boolean smallArms) {
        return smallArms ? SLIM_GOLDEN_HOOK : GOLDEN_HOOK;
    }
}
