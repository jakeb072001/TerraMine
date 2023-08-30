package terramine.common.item.accessories.back;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import terramine.common.item.accessories.AccessoryTerrariaItem;
import terramine.common.utility.RocketBootHelper;
import terramine.common.utility.equipmentchecks.CloudBottleEquippedCheck;
import terramine.common.utility.equipmentchecks.RocketBootsEquippedCheck;

public class WingsItem extends AccessoryTerrariaItem {

	private final RocketBootHelper rocketHelper = new RocketBootHelper();
	private final double speed, glideSpeed;
	private final int flightTime, priority;

	public WingsItem(double speed, double glideSpeed, int flightTime, int priority, SoundEvent sound) {
		RandomSource random = RandomSource.create();
		rocketHelper.setSoundSettings(sound, 1.5f, random.nextInt(8, 12) / 10f);
		this.speed = speed;
		this.glideSpeed = glideSpeed;
		this.flightTime = flightTime;
		this.priority = priority;
	}

	@Override
	public void curioTick(Player player, ItemStack stack) {
		int flightTimeCache;
		double speedCache;
		if (RocketBootsEquippedCheck.isEquipped(player)) {
			flightTimeCache = flightTime + 35;
		} else {
			flightTimeCache = flightTime;
		}
		if (CloudBottleEquippedCheck.isEquipped(player)) {
			speedCache = speed + (speed * 0.30);
		} else {
			speedCache = speed;
		}
		rocketHelper.wingFly(speedCache, glideSpeed, priority, flightTimeCache, player);
	}
}
