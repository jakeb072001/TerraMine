package terracraft.mixin.item.diggingclaws;

import net.fabricmc.fabric.api.mininglevel.v1.MiningLevelManager;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import terracraft.common.init.ModItems;
import terracraft.common.item.curio.belt.AncientChiselItem;
import terracraft.common.item.curio.old.DiggingClawsItem;
import terracraft.common.trinkets.TrinketsHelper;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {

	protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level world) {
		super(entityType, world);
	}

	@Inject(method = "hasCorrectToolForDrops", at = @At("HEAD"), cancellable = true)
	private void increaseBaseMiningLevel(BlockState block, CallbackInfoReturnable<Boolean> info) {
		if (TrinketsHelper.isEquipped(ModItems.DIGGING_CLAWS, this)
				&& DiggingClawsItem.NEW_BASE_MINING_LEVEL >= MiningLevelManager.getRequiredMiningLevel(block)) {
			info.setReturnValue(true);
		}
	}

	/**
	 * This is identical to the forge version but might not be ideal
	 * It adds the speed after the vanilla method does all the calculations on the base modifier such as haste and underwater
	 */
	// TODO: identical terracraft-forge behaviour but could do this on the speed mutliplier instead of end result
	@Inject(method = "getDestroySpeed", at = @At("RETURN"), cancellable = true)
	private void increaseMiningSpeed(BlockState block, CallbackInfoReturnable<Float> info) {
		if (TrinketsHelper.isEquipped(ModItems.DIGGING_CLAWS, this)) {
			info.setReturnValue(info.getReturnValueF() + DiggingClawsItem.MINING_SPEED_INCREASE);
		} else if (TrinketsHelper.isEquipped(ModItems.ANCIENT_CHISEL, this)) {
			info.setReturnValue(info.getReturnValueF() + AncientChiselItem.MINING_SPEED_INCREASE);
		}
	}
}
