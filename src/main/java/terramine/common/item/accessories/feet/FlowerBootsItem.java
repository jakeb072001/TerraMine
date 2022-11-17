package terramine.common.item.accessories.feet;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import terramine.common.item.accessories.TrinketTerrariaItem;
import terramine.common.network.packet.BoneMealPacket;

public class FlowerBootsItem extends TrinketTerrariaItem {
	@Override
	public void curioTick(LivingEntity player, ItemStack stack) {
		if (player != null && player.isSprinting()) {
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
	}
}
