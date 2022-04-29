package terracraft.common.utility;

import net.minecraft.world.entity.LivingEntity;
import terracraft.common.init.ModItems;
import terracraft.common.trinkets.TrinketsHelper;

public class CloudBottleEquippedCheck {
    public static boolean isEquipped(LivingEntity entity) {
        return TrinketsHelper.isEquipped(ModItems.CLOUD_IN_A_BOTTLE, entity) || TrinketsHelper.isEquipped(ModItems.CLOUD_IN_A_BALLOON, entity)
                || TrinketsHelper.isEquipped(ModItems.BLUE_HORSESHOE_BALLOON, entity) || TrinketsHelper.isEquipped(ModItems.BUNDLE_OF_BALLOONS, entity);
    }
}
