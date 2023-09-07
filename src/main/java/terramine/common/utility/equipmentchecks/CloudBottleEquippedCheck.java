package terramine.common.utility.equipmentchecks;

import net.minecraft.world.entity.player.Player;
import terramine.common.init.ModItems;
import terramine.common.misc.AccessoriesHelper;

public class CloudBottleEquippedCheck {
    public static boolean isEquipped(Player player) {
        return AccessoriesHelper.isEquipped(ModItems.CLOUD_IN_A_BOTTLE, player) || AccessoriesHelper.isEquipped(ModItems.CLOUD_IN_A_BALLOON, player)
                || AccessoriesHelper.isEquipped(ModItems.BLUE_HORSESHOE_BALLOON, player) || AccessoriesHelper.isEquipped(ModItems.BUNDLE_OF_BALLOONS, player);
    }
}
