package terramine.mixin.item.accessories.rangeremblem;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import terramine.common.init.ModAttributes;

@Mixin(AbstractArrow.class)
public abstract class AbstractArrowMixin {

	@ModifyVariable(method = "onHitEntity", at = @At("STORE"), ordinal = 0)
	private int moreArrowDamage(int t, EntityHitResult entityHitResult) {
		Entity owner = ((Projectile)(Object)this).getOwner();
		if (owner instanceof LivingEntity entity) {
			if (entity.getAttributes().hasAttribute(ModAttributes.RANGER_ATTACK_DAMAGE)) {
				t *= (float) entity.getAttribute(ModAttributes.RANGER_ATTACK_DAMAGE).getValue();
			}
		}
		return t;
	}
}
