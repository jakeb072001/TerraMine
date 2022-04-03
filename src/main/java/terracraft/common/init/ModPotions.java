package terracraft.common.init;

import net.minecraft.core.Registry;
import net.minecraft.world.item.alchemy.Potion;
import terracraft.TerraCraft;
import terracraft.common.potions.TerrariaPotion;

public class ModPotions {
    //Timed
    public static final Potion MANA_REGENERATION_POTION = register("mana_regen_potion", new TerrariaPotion.TerrariaPotionTimed("mana_regen_potion", ModMobEffects.MANA_REGEN, (480 * 20), 0));

    //Instant
    public static final Potion LESSER_MANA_POTION = register("lesser_mana_potion", new TerrariaPotion.TerrariaPotionInstant("lesser_mana_potion", ModMobEffects.INSTANT_MANA, 0));
    public static final Potion MANA_POTION = register("mana_potion", new TerrariaPotion.TerrariaPotionInstant("mana_potion", ModMobEffects.INSTANT_MANA, 1));
    public static final Potion GREATER_MANA_POTION = register("greater_mana_potion", new TerrariaPotion.TerrariaPotionInstant("greater_mana_potion", ModMobEffects.INSTANT_MANA, 3));
    public static final Potion SUPER_MANA_POTION = register("super_mana_potion", new TerrariaPotion.TerrariaPotionInstant("super_mana_potion", ModMobEffects.INSTANT_MANA, 5));

    public static Potion register(String name, Potion potion) {
        return Registry.register(Registry.POTION, TerraCraft.id(name), potion);
    }

    private ModPotions() {
    }
}
