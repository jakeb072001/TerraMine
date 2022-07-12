package terramine.common.utility.equipmentchecks;

import net.minecraft.world.entity.LivingEntity;
import terramine.common.init.ModItems;
import terramine.common.trinkets.TrinketsHelper;

public class RocketBootsEquippedCheck {
    public static boolean isEquipped(LivingEntity entity) {
        return TrinketsHelper.isEquipped(ModItems.ROCKET_BOOTS, entity) || TrinketsHelper.isEquipped(ModItems.SPECTRE_BOOTS, entity)
                || TrinketsHelper.isEquipped(ModItems.FAIRY_BOOTS, entity) || TrinketsHelper.isEquipped(ModItems.LIGHTNING_BOOTS, entity)
                || TrinketsHelper.isEquipped(ModItems.FROSTSPARK_BOOTS, entity) || TrinketsHelper.isEquipped(ModItems.TERRASPARK_BOOTS, entity);
    }
}
