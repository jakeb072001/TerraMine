package terramine.common.potions;

import net.minecraft.core.Registry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;

public class TerrariaPotion extends Potion {
    private TerrariaPotion extended = null;
    private TerrariaPotion empowered = null;
    private boolean registered = false;

    protected TerrariaPotion(String name, MobEffectInstance statusEffect) {
        super(name, statusEffect);
    }

    public TerrariaPotion getExtended() {
        return extended;
    }

    public void setExtended(TerrariaPotion extended) {
        this.extended = extended;
    }

    public TerrariaPotion getEmpowered() {
        return empowered;
    }

    public void setEmpowered(TerrariaPotion empowered) {
        this.empowered = empowered;
    }

    public int registerTree(String namespace, String base) {
        if (registered) return 0;
        Registry.register(Registry.POTION, namespace + ":" + base, this);
        registered = true;
        int registeredTotal = 1;
        if (extended != null) {
            registeredTotal += extended.registerTree(namespace, base + "_long");
        }

        if (empowered != null) {
            registeredTotal += empowered.registerTree(namespace, base + "_strong");
        }

        return registeredTotal;
    }

    public static class TerrariaPotionInstant extends TerrariaPotion {

        public TerrariaPotionInstant(String name, MobEffect statusEffect, int amplifier) {
            super(name, new MobEffectInstance(statusEffect, 0, amplifier, false, true));
        }

        @Override
        public boolean hasInstantEffects() {
            return true;
        }

        public static TerrariaPotionInstant generateAll(String name, MobEffect statusEffect) {
            TerrariaPotionInstant base = new TerrariaPotionInstant(name, statusEffect, 0);
            base.setEmpowered(new TerrariaPotionInstant(name, statusEffect, 1));
            return base;
        }

    }

    public static class TerrariaPotionTimed extends TerrariaPotion {

        public TerrariaPotionTimed(String name, MobEffect statusEffect, int length, int amplifier) {
            super(name, new MobEffectInstance(statusEffect, length, amplifier, false, true));
        }

        @Override
        public boolean hasInstantEffects() {
            return false;
        }

        public static TerrariaPotionTimed generateAll(String name, MobEffect statusEffect, int lengthNormal, int lengthExtended, int lengthEmpowered) {
            TerrariaPotionTimed base = new TerrariaPotionTimed(name, statusEffect, lengthNormal, 0);
            base.setEmpowered(new TerrariaPotionTimed(name, statusEffect, lengthEmpowered, 1));
            base.setExtended(new TerrariaPotionTimed(name, statusEffect, lengthExtended, 0));
            return base;
        }

        public static TerrariaPotionTimed generateWithLengthened(String name, MobEffect statusEffect, int lengthNormal, int lengthExtended) {
            TerrariaPotionTimed base = new TerrariaPotionTimed(name, statusEffect, lengthNormal, 0);
            base.setEmpowered(null);
            base.setExtended(new TerrariaPotionTimed(name, statusEffect, lengthExtended, 0));
            return base;
        }

    }
}
