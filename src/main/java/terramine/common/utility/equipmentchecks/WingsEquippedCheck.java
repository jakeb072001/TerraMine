package terramine.common.utility.equipmentchecks;

import net.minecraft.world.entity.LivingEntity;
import terramine.common.init.ModItems;
import terramine.common.trinkets.TrinketsHelper;

public class WingsEquippedCheck {
    public static boolean isEquipped(LivingEntity entity) {
        return TrinketsHelper.isEquipped(ModItems.FLEDGLING_WINGS, entity) || TrinketsHelper.isEquipped(ModItems.ANGEL_WINGS, entity)
                || TrinketsHelper.isEquipped(ModItems.DEMON_WINGS, entity) || TrinketsHelper.isEquipped(ModItems.LEAF_WINGS, entity);
    }
}
