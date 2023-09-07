package terramine.common.item.armor.vanity;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import terramine.TerraMine;

public class FamiliarVanity extends VanityArmor {
    public FamiliarVanity(String armorType, ArmorMaterial armorMaterial, EquipmentSlot equipmentSlot, Properties properties) {
        super(armorType, armorMaterial, equipmentSlot, properties);
    }

    @Override
    public String getCustomArmorLocation() {
        return TerraMine.MOD_ID + ":textures/models/vanity/empty.png";
    }
}
