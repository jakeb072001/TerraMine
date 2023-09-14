package terramine.common.item.accessories.hands;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import terramine.common.item.accessories.AccessoryTerrariaItem;

public class ShinyRedBalloonItem extends AccessoryTerrariaItem {

	@Override
	public void onEquip(ItemStack stack, Player player) {
		player.addEffect(new MobEffectInstance(MobEffects.JUMP, -1, 1, true, false));
	}

	@Override
	public void onUnequip(ItemStack stack, Player player) {
		player.removeEffect(MobEffects.JUMP);
	}
}
