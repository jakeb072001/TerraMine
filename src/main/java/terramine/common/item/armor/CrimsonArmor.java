package terramine.common.item.armor;

import com.google.common.collect.ImmutableMultimap;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.level.Level;

import java.util.UUID;

public class CrimsonArmor extends TerrariaArmor {
    private int timer;

    public CrimsonArmor(String armorType, ArmorMaterial armorMaterial, Type type, Properties properties) {
        super(armorType, armorMaterial, type, properties);

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        UUID uUID = ARMOR_MODIFIER_UUID_PER_SLOT[type.getSlot().getIndex()];
        builder.put(Attributes.ARMOR, new AttributeModifier(uUID, "Armor modifier", this.defense, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uUID, "Armor toughness", this.toughness, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(uUID, "Crimson Attack Damage", 0.02, AttributeModifier.Operation.MULTIPLY_TOTAL));
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
