package terramine.common.item.accessories.feet;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import terramine.common.init.ModSoundEvents;
import terramine.common.item.accessories.AccessoryTerrariaItem;
import terramine.common.utility.RocketBootHelper;

public class RocketBootsItem extends AccessoryTerrariaItem {

	public RocketBootHelper rocketHelper = new RocketBootHelper();
	public double speed = 0.4D;

	public RocketBootsItem() {
		rocketHelper.setSoundSettings(ModSoundEvents.ROCKET_BOOTS, 1.5f, 1f);
		rocketHelper.setParticleSettings(ParticleTypes.FLAME, ParticleTypes.SMOKE);
	}

	@Override
	public void curioTick(Player player, ItemStack stack) {
		rocketHelper.rocketFly(speed, 1, player);
	}
}
