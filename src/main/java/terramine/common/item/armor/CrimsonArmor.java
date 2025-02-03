package terramine.common.item.armor;

import com.google.common.collect.ImmutableMultimap;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.Level;
import terramine.TerraMine;

public class CrimsonArmor extends TerrariaArmor {
    private int timer;

    public CrimsonArmor(String armorType, ArmorMaterial holder, ArmorType type, Properties properties) {
        super(armorType, holder, type, properties);

        ImmutableMultimap.Builder<Holder<Attribute>, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(TerraMine.id("crimson_attack_damage"), 0.02, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        attributeModifiers = builder.build();
    }

    @Override
    public void setBonusEffect(LivingEntity livingEntity, Level level) {
        if (livingEntity instanceof Player player) {
            timer += 1;
            if (timer >= 50) {
                player.heal(0.5f);
                timer = 0;
            }
        } else { // don't know if this does anything, but maybe allows other non-player entities to heal
            timer += 1;
            if (timer >= 50) {
                livingEntity.heal(0.5f);
                timer = 0;
            }
        }
    }
}
