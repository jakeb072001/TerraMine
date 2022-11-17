package terramine.common.item.armor;

import com.google.common.collect.ImmutableMultimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.level.Level;
import terramine.common.init.ModAttributes;
import terramine.common.init.ModComponents;

import java.util.UUID;

public class MeteorArmor extends TerrariaArmor {

    public MeteorArmor(String armorType, ArmorMaterial armorMaterial, EquipmentSlot equipmentSlot, Properties properties) {
        super(armorType, armorMaterial, equipmentSlot, properties);

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        UUID uUID = ARMOR_MODIFIER_UUID_PER_SLOT[equipmentSlot.getIndex()];
        builder.put(Attributes.ARMOR, new AttributeModifier(uUID, "Armor modifier", this.defense, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uUID, "Armor toughness", this.toughness, AttributeModifier.Operation.ADDITION));
        builder.put(ModAttributes.MAGIC_ATTACK_DAMAGE, new AttributeModifier(uUID, "Meteor Magic Damage", 0.09, AttributeModifier.Operation.MULTIPLY_TOTAL));
        attributeModifiers = builder.build();
    }

    @Override
    public void setBonusEffect(LivingEntity livingEntity, Level level) {
        if (livingEntity instanceof Player player) {
            ModComponents.SPACE_GUN_FREE.get(player).set(true); // todo: need to make Space Gun and use this to make it free
        }
    }

    @Override
    public void removeBonusEffect(LivingEntity livingEntity, Level level) {
        if (livingEntity instanceof Player player) {
            ModComponents.SPACE_GUN_FREE.get(player).set(false);
        }
    }
}
