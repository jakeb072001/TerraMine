package terracraft.mixin.item.kittyslippers;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import terracraft.common.init.ModItems;
import terracraft.common.trinkets.TrinketsHelper;

@Mixin(HurtByTargetGoal.class)
public abstract class HurtByTargetGoalMixin extends TargetGoal {

	public HurtByTargetGoalMixin(Mob mob, boolean checkVisibility) {
		super(mob, checkVisibility);
	}

	@Inject(method = "canUse", at = @At("HEAD"), cancellable = true)
	private void cancelRevenge(CallbackInfoReturnable<Boolean> info) {
		LivingEntity attacker = this.mob.getLastHurtByMob();
		if (this.mob.getType() == EntityType.CREEPER && TrinketsHelper.isEquipped(ModItems.KITTY_SLIPPERS, attacker)) {
			info.setReturnValue(false);
		}
	}
}
