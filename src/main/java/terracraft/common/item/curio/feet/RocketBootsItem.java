package terracraft.common.item.curio.feet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import terracraft.common.init.ModItems;
import terracraft.common.init.ModSoundEvents;
import terracraft.common.item.curio.TrinketTerrariaItem;
import terracraft.common.trinkets.TrinketsHelper;
import terracraft.common.utility.RocketBootHelper;

import java.util.Random;

public class RocketBootsItem extends TrinketTerrariaItem {

	public RocketBootHelper rocketHelper = new RocketBootHelper();
	public double speed = 0.08D;

	public RocketBootsItem() {
		rocketHelper.setSoundSettings(ModSoundEvents.ROCKET_BOOTS, 1.5f, 1f);
		rocketHelper.setParticleSettings(ParticleTypes.FLAME, ParticleTypes.SMOKE);
	}

	@Override
	public void curioTick(LivingEntity player, ItemStack stack) {
		rocketHelper.rocketFly(true, speed, 1, player, this);
	}
}
