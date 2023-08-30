package terramine.common.utility.equipmentchecks;

import net.minecraft.world.entity.LivingEntity;
import terramine.common.init.ModItems;
import terramine.common.misc.AccessoriesHelper;

public class WingsEquippedCheck {
    public static boolean isEquipped(LivingEntity entity) {
        return AccessoriesHelper.isEquipped(ModItems.FLEDGLING_WINGS, entity) || AccessoriesHelper.isEquipped(ModItems.ANGEL_WINGS, entity)
                || AccessoriesHelper.isEquipped(ModItems.DEMON_WINGS, entity) || AccessoriesHelper.isEquipped(ModItems.LEAF_WINGS, entity);
    }
}
