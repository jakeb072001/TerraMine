package terracraft.mixin.item.villagerhat;

import terracraft.common.init.Items;
import terracraft.common.trinkets.TrinketsHelper;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Villager.class)
public abstract class VillagerMixin {

    @ModifyVariable(method = "updateSpecialPrices", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/entity/npc/Villager;getPlayerReputation(Lnet/minecraft/world/entity/player/Player;)I"))
    private int increaseReputation(int i, Player player) {
        if (TrinketsHelper.isEquipped(Items.VILLAGER_HAT, player)) {
            i += 100; /*TODO: ModConfig.server.villagerHat.reputationBonus.get();*/
        }
        return i;
    }
}
