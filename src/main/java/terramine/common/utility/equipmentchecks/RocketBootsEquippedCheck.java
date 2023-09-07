package terramine.common.utility.equipmentchecks;

import net.minecraft.world.entity.LivingEntity;
import terramine.common.init.ModItems;
import terramine.common.misc.AccessoriesHelper;

public class RocketBootsEquippedCheck {
    public static boolean isEquipped(LivingEntity entity) {
        return AccessoriesHelper.isEquipped(ModItems.ROCKET_BOOTS, entity) || AccessoriesHelper.isEquipped(ModItems.SPECTRE_BOOTS, entity)
                || AccessoriesHelper.isEquipped(ModItems.FAIRY_BOOTS, entity) || AccessoriesHelper.isEquipped(ModItems.LIGHTNING_BOOTS, entity)
                || AccessoriesHelper.isEquipped(ModItems.FROSTSPARK_BOOTS, entity) || AccessoriesHelper.isEquipped(ModItems.TERRASPARK_BOOTS, entity);
    }
}
