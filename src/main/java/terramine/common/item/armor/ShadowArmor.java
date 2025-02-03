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

public class ShadowArmor extends TerrariaArmor {
    private static final AttributeModifier MOVEMENT_SPEED_BONUS = new AttributeModifier(TerraMine.id("shadow_armor_set_bonus"), 0.15, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

    public ShadowArmor(String armorType, ArmorMaterial holder, ArmorType type, Properties properties) {
        super(armorType, holder, type, properties);

        ImmutableMultimap.Builder<Holder<Attribute>, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(TerraMine.id("shadow_attack_speed"), 0.07, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        attributeModifiers = builder.build();
    }

    @Override
    public void setBonusEffect(LivingEntity livingEntity, Level level) {
        AttributeInstance movementSpeed = livingEntity.getAttribute(Attributes.MOVEMENT_SPEED);
        if (movementSpeed != null) {
            addModifier(movementSpeed, MOVEMENT_SPEED_BONUS);
        }
    }

    @Override
    public void removeBonusEffect(LivingEntity livingEntity, Level level) {
        AttributeInstance movementSpeed = livingEntity.getAttribute(Attributes.MOVEMENT_SPEED);
        if (movementSpeed != null) {
            removeModifier(movementSpeed, MOVEMENT_SPEED_BONUS);
        }
    }
}
