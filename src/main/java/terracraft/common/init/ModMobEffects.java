package terracraft.common.init;

import net.minecraft.core.Registry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import terracraft.TerraCraft;
import terracraft.common.potions.effects.mana.InstantManaEffect;
import terracraft.common.potions.effects.mana.ManaRegenEffect;

public class ModMobEffects {
    //Timed
    public static final MobEffect MANA_REGEN = register("mana_regen", new ManaRegenEffect(MobEffectCategory.BENEFICIAL, 0xe13b9e, false));

    //Instant
    public static final MobEffect INSTANT_MANA = register("instant_mana", new InstantManaEffect(MobEffectCategory.BENEFICIAL, 0x262db3, true));

    public static MobEffect register(String name, MobEffect effect) {
        return Registry.register(Registry.MOB_EFFECT, TerraCraft.id(name), effect);
    }
}
