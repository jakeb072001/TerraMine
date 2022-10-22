package terramine.mixin.item.rangeremblem;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import terramine.common.init.ModItems;
import terramine.common.trinkets.TrinketsHelper;

import java.util.Random;

@Mixin(AbstractArrow.class)
public abstract class AbstractArrowMixin {

	@Shadow
	private double baseDamage;

	@Unique
	private final Random random = new Random();

	@ModifyVariable(method = "onHitEntity", at = @At("STORE"), ordinal = 0)
	private int moreArrowDamage(int t, EntityHitResult entityHitResult) {
		Entity owner = ((Projectile)(Object)this).getOwner();
		if (owner instanceof LivingEntity entity && owner != null) {
			if (TrinketsHelper.isEquipped(ModItems.RANGER_EMBLEM, entity) || TrinketsHelper.isEquipped(ModItems.AVENGER_EMBLEM, entity)
					|| TrinketsHelper.isEquipped(ModItems.MECHANICAL_GLOVE, entity) || TrinketsHelper.isEquipped(ModItems.FIRE_GAUNTLET, entity)) {
				float f = (float) ((AbstractArrow) (Object) this).getDeltaMovement().length();
				t = Mth.ceil(Mth.clamp((double)f * this.baseDamage * 1.15, 0.0D, 2.147483647E9D));;
				long l = (long)this.random.nextInt(t / 2 + 2);
				t = (int)Math.min(l + (long)t, 2147483647L);
			}
		}
		return t;
	}
}
