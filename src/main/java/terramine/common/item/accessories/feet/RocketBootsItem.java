package terramine.common.item.accessories.feet;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import terramine.common.init.ModSoundEvents;
import terramine.common.item.accessories.TrinketTerrariaItem;
import terramine.common.utility.RocketBootHelper;

public class RocketBootsItem extends TrinketTerrariaItem {

	public RocketBootHelper rocketHelper = new RocketBootHelper();
	public double speed = 0.4D;

	public RocketBootsItem() {
		rocketHelper.setSoundSettings(ModSoundEvents.ROCKET_BOOTS, 1.5f, 1f);
		rocketHelper.setParticleSettings(ParticleTypes.FLAME, ParticleTypes.SMOKE);
	}

	@Override
	public void curioTick(LivingEntity player, ItemStack stack) {
		rocketHelper.rocketFly(speed, 1, player);
	}
}
