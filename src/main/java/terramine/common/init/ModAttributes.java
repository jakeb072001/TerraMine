package terramine.common.init;

import net.minecraft.core.Registry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.player.Player;
import terramine.TerraMine;

public class ModAttributes {
    public static final Attribute RANGER_ATTACK_DAMAGE = register("ranger_attack_damage", new RangedAttribute("attribute.name.ranger_attack_damage", 1.0, 0.0, 2048.0).setSyncable(true));
    public static final Attribute MAGIC_ATTACK_DAMAGE = register("magic_attack_damage", new RangedAttribute("attribute.name.magic_attack_damage", 1.0, 0.0, 2048.0).setSyncable(true));
    public static final Attribute MAGIC_ATTACK_SPEED = register("magic_attack_speed", new RangedAttribute("attribute.name.magic_attack_speed", 1.0, 0.0, 2048.0).setSyncable(true));

    public static float damageMultiplier(Entity entity) {
        if (entity instanceof Player player) {
            if (player.getAttributes().hasAttribute(ModAttributes.MAGIC_ATTACK_DAMAGE)) {
                return (float) player.getAttribute(ModAttributes.MAGIC_ATTACK_DAMAGE).getValue();
            }
        }

        return 1f;
    }

    private static Attribute register(String string, Attribute attribute) {
        return Registry.register(Registry.ATTRIBUTE, TerraMine.id(string), attribute);
    }
}
