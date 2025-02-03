package terramine.common.utility.equipmentchecks;

import net.minecraft.world.entity.LivingEntity;
import terramine.common.init.ModItems;
import terramine.common.misc.AccessoriesHelper;

public class ShellEquippedCheck {
    public static boolean isEquipped(LivingEntity entity) {
        return AccessoriesHelper.isEquipped(ModItems.NEPTUNE_SHELL, entity) || AccessoriesHelper.isEquipped(ModItems.MOON_SHELL, entity)
                || AccessoriesHelper.isEquipped(ModItems.CELESTIAL_SHELL, entity);
    }
}
