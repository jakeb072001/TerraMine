package terramine.common.item.armor;

import com.google.common.collect.ImmutableMultimap;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.Level;
import terramine.TerraMine;

public class MoltenArmor extends TerrariaArmor {
    private static final AttributeModifier ATTACK_DAMAGE_BONUS = new AttributeModifier(TerraMine.id("molten_armor_set_bonus"), 0.10, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

    public MoltenArmor(String armorType, ArmorMaterial holder, ArmorType type, Properties properties) {
        super(armorType, holder, type, properties);

        ImmutableMultimap.Builder<Holder<Attribute>, AttributeModifier> builder = ImmutableMultimap.builder();
        if (type == ArmorType.HELMET) {
            builder.put(Attributes.LUCK, new AttributeModifier(TerraMine.id("molten_attack_crit_chance"), 0.07, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        }
        if (type == ArmorType.CHESTPLATE) {
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(TerraMine.id("molten_attack_damage"), 0.07, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        }
        if (type == ArmorType.LEGGINGS) {
            builder.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(TerraMine.id("molten_attack_knockback"), 0.07, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        }
        if (type == ArmorType.BOOTS) {
            builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(TerraMine.id("molten_attack_speed"), 0.07, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        }
        attributeModifiers = builder.build();
    }

    @Override
    public void setBonusEffect(LivingEntity livingEntity, Level level) {
        AttributeInstance attackDamage = livingEntity.getAttribute(Attributes.ATTACK_DAMAGE);
        if (attackDamage != null) {
            addModifier(attackDamage, ATTACK_DAMAGE_BONUS);
        }
    }

    @Override
    public void removeBonusEffect(LivingEntity livingEntity, Level level) {
        AttributeInstance attackDamage = livingEntity.getAttribute(Attributes.ATTACK_DAMAGE);
        if (attackDamage != null) {
            removeModifier(attackDamage, ATTACK_DAMAGE_BONUS);
        }
    }
}
