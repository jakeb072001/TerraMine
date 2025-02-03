package terramine.common.item.armor;

import com.google.common.collect.ImmutableMultimap;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.Level;
import terramine.TerraMine;
import terramine.common.init.ModAttributes;
import terramine.common.init.ModComponents;

public class MeteorArmor extends TerrariaArmor {

    public MeteorArmor(String armorType, ArmorMaterial holder, ArmorType type, Properties properties) {
        super(armorType, holder, type, properties);

        ImmutableMultimap.Builder<Holder<Attribute>, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(ModAttributes.MAGIC_ATTACK_DAMAGE, new AttributeModifier(TerraMine.id("meteor_magic_damage"), 0.09, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        attributeModifiers = builder.build();
    }

    @Override
    public void setBonusEffect(LivingEntity livingEntity, Level level) {
        if (livingEntity instanceof Player player) {
            ModComponents.SPACE_GUN_FREE.get(player).set(true);
        }
    }

    @Override
    public void removeBonusEffect(LivingEntity livingEntity, Level level) {
        if (livingEntity instanceof Player player) {
            ModComponents.SPACE_GUN_FREE.get(player).set(false);
        }
    }
}
