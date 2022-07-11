package terramine.common.item.curio.back;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import terramine.common.item.curio.TrinketTerrariaItem;
import terramine.common.utility.RocketBootHelper;

public class WingsItem extends TrinketTerrariaItem {

	public RocketBootHelper rocketHelper = new RocketBootHelper();
	public double speed;
	public int flightTime;

	public WingsItem(double speed, int flightTime, SoundEvent sound) {
		rocketHelper.setSoundSettings(sound, 1.5f, 1f);
		this.speed = speed;
		this.flightTime = flightTime;
	}

	@Override
	public void curioTick(LivingEntity player, ItemStack stack) {
		rocketHelper.wingFly(speed, 7, 15, player);
	}
}
