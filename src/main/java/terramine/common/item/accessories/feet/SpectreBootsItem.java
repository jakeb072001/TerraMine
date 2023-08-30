package terramine.common.item.accessories.feet;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import terramine.common.init.ModParticles;
import terramine.common.init.ModSoundEvents;
import terramine.common.item.accessories.AccessoryTerrariaItem;
import terramine.common.utility.RocketBootHelper;

public class SpectreBootsItem extends AccessoryTerrariaItem {

	public RocketBootHelper rocketHelper = new RocketBootHelper();
	public double speed = 0.4D;

	public SpectreBootsItem() {
		rocketHelper.setSoundSettings(ModSoundEvents.SPECTRE_BOOTS, 1f, 1f);
		rocketHelper.setParticleSettings(ModParticles.BLUE_POOF, ParticleTypes.POOF);
	}

	@Override
	public void curioTick(Player player, ItemStack stack) {
		rocketHelper.rocketFly(speed, 2, player);
	}
}

