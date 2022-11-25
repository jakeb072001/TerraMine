package terramine.client.render;

import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.minecraft.client.resources.model.ModelResourceLocation;
import terramine.TerraMine;

public class HeldItemModels {
    public static final ModelResourceLocation UMBRELLA_HELD_MODEL = new ModelResourceLocation(TerraMine.id("umbrella_in_hand"), "inventory");
    public static final ModelResourceLocation MAGIC_MISSILE_HELD_MODEL = new ModelResourceLocation(TerraMine.id("magic_missile_held"), "inventory");
    public static final ModelResourceLocation FLAMELASH_HELD_MODEL = new ModelResourceLocation(TerraMine.id("flamelash_held"), "inventory");
    public static final ModelResourceLocation RAINBOW_ROD_HELD_MODEL = new ModelResourceLocation(TerraMine.id("rainbow_rod_held"), "inventory");
    public static final ModelResourceLocation SPACE_GUN_HELD_MODEL = new ModelResourceLocation(TerraMine.id("space_gun_held"), "inventory");
    public static final ModelResourceLocation GRENADE_HELD_MODEL = new ModelResourceLocation(TerraMine.id("grenade_held"), "inventory");
    public static final ModelResourceLocation STICKY_GRENADE_HELD_MODEL = new ModelResourceLocation(TerraMine.id("sticky_grenade_held"), "inventory");
    public static final ModelResourceLocation BOUNCY_GRENADE_HELD_MODEL = new ModelResourceLocation(TerraMine.id("bouncy_grenade_held"), "inventory");
    public static final ModelResourceLocation BOMB_HELD_MODEL = new ModelResourceLocation(TerraMine.id("bomb_held"), "inventory");
    public static final ModelResourceLocation STICKY_BOMB_HELD_MODEL = new ModelResourceLocation(TerraMine.id("sticky_bomb_held"), "inventory");
    public static final ModelResourceLocation BOUNCY_BOMB_HELD_MODEL = new ModelResourceLocation(TerraMine.id("bouncy_bomb_held"), "inventory");
    public static final ModelResourceLocation DYNAMITE_HELD_MODEL = new ModelResourceLocation(TerraMine.id("dynamite_held"), "inventory");
    public static final ModelResourceLocation STICKY_DYNAMITE_HELD_MODEL = new ModelResourceLocation(TerraMine.id("sticky_dynamite_held"), "inventory");
    public static final ModelResourceLocation BOUNCY_DYNAMITE_HELD_MODEL = new ModelResourceLocation(TerraMine.id("bouncy_dynamite_held"), "inventory");

    public static void register() {
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(UMBRELLA_HELD_MODEL));
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(MAGIC_MISSILE_HELD_MODEL));
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(FLAMELASH_HELD_MODEL));
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(RAINBOW_ROD_HELD_MODEL));
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(SPACE_GUN_HELD_MODEL));
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(GRENADE_HELD_MODEL));
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(STICKY_GRENADE_HELD_MODEL));
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(BOUNCY_GRENADE_HELD_MODEL));
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(BOMB_HELD_MODEL));
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(STICKY_BOMB_HELD_MODEL));
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(BOUNCY_BOMB_HELD_MODEL));
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(DYNAMITE_HELD_MODEL));
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(STICKY_DYNAMITE_HELD_MODEL));
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(BOUNCY_DYNAMITE_HELD_MODEL));
    }
}
