package terramine.common.init;

import net.minecraft.core.Registry;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import terramine.TerraMine;

public class ModAttributes {
    public static final Attribute RANGER_ATTACK_DAMAGE = register("ranger_attack_damage", new RangedAttribute("attribute.name.ranger_attack_damage", 1.0, 0.0, 2048.0).setSyncable(true));
    public static final Attribute MAGIC_ATTACK_DAMAGE = register("magic_attack_damage", new RangedAttribute("attribute.name.magic_attack_damage", 1.0, 0.0, 2048.0).setSyncable(true));
    public static final Attribute MAGIC_ATTACK_SPEED = register("magic_attack_speed", new RangedAttribute("attribute.name.magic_attack_speed", 1.0, 0.0, 2048.0).setSyncable(true));

    private static Attribute register(String string, Attribute attribute) {
        return Registry.register(Registry.ATTRIBUTE, TerraMine.id(string), attribute);
    }
}
