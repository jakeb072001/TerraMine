package terracraft.mixin.item.rangeremblem;

import terracraft.common.init.Items;
import terracraft.common.trinkets.TrinketsHelper;
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
			if (TrinketsHelper.isEquipped(Items.RANGER_EMBLEM, entity) || TrinketsHelper.isEquipped(Items.AVENGER_EMBLEM, entity)
					|| TrinketsHelper.isEquipped(Items.MECHANICAL_GLOVE, entity) || TrinketsHelper.isEquipped(Items.FIRE_GAUNTLET, entity)) {
				float f = (float) ((AbstractArrow) (Object) this).getDeltaMovement().length();
				t = Mth.ceil(Mth.clamp((double)f * this.baseDamage * 1.15, 0.0D, 2.147483647E9D));;
				long l = (long)this.random.nextInt(t / 2 + 2);
				t = (int)Math.min(l + (long)t, 2147483647L);
			}
		}
		return t;
	}
}
