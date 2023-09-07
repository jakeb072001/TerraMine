package terramine.common.utility.equipmentchecks;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import terramine.common.item.armor.TerrariaArmor;

import java.util.Objects;

public class ArmorSetCheck {

    public static boolean isSetEquipped(LivingEntity livingEntity, String armorType) {
        boolean isEquipped = false;

        for (ItemStack item : livingEntity.getArmorSlots()) {
            if (item.getItem() instanceof TerrariaArmor armorItem) {
                isEquipped = Objects.equals(armorItem.getArmorType(), armorType);
                if (!isEquipped) {
                    break;
                }
            } else {
                return false;
            }
        }

        return isEquipped;
    }
}
