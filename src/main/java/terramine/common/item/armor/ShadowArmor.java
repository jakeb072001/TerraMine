package terramine.common.item.armor;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;

public class ShadowArmor extends TerrariaArmor {
    public ShadowArmor(String armorType, ArmorMaterial armorMaterial, EquipmentSlot equipmentSlot, Properties properties) {
        super(armorType, armorMaterial, equipmentSlot, properties);
    }

    // todo: give effects to the player for each piece of armor and another effect if all pieces are equipped, probably use mixin or interface.
}
