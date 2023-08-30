package terramine.common.item.accessories.feet;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import terramine.common.init.ModParticles;
import terramine.common.init.ModSoundEvents;
import terramine.common.item.accessories.AccessoryTerrariaItem;
import terramine.common.network.packet.BoneMealPacket;
import terramine.common.utility.RocketBootHelper;

public class FairyBootsItem extends AccessoryTerrariaItem {

	public RocketBootHelper rocketHelper = new RocketBootHelper();
	public double speed = 0.4D;

	public FairyBootsItem() {
		rocketHelper.setSoundSettings(ModSoundEvents.SPECTRE_BOOTS, 1f, 1f);
		rocketHelper.setParticleSettings(ModParticles.BLUE_POOF, ParticleTypes.POOF);
	}

	@Override
	public void curioTick(Player player, ItemStack stack) {
		if (player != null && player.isSprinting() && !player.isCrouching()) {
			Level level = player.level;
			BlockPos blockPos = player.getOnPos();
			BlockPos blockCropPos = player.getOnPos().offset(0,1.3,0);
			if (level.getBlockState(blockPos).getBlock() instanceof BonemealableBlock && level.isClientSide()) {
				BoneMealPacket.send(blockPos);
			}
			if (level.getBlockState(blockCropPos).getBlock() instanceof BonemealableBlock && level.isClientSide()) {
				BoneMealPacket.send(blockCropPos);
			}
		}
		rocketHelper.rocketFly(speed, 3, player);
	}
}

