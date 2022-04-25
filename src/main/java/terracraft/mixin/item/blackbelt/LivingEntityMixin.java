package terracraft.mixin.item.blackbelt;

import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import terracraft.common.init.ModItems;
import terracraft.common.trinkets.TrinketsHelper;

import java.util.Random;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

	@ModifyVariable(method = "hurt", at = @At("STORE"), ordinal = 0, argsOnly = true)
	private float dodgeAttack(float f) {
		Player self = (Player) (Object) this;
		Vec3 pos = self.position();
		Vec3i motion = self.getMotionDirection().getNormal();
		if (TrinketsHelper.isEquipped(ModItems.BLACK_BELT, self) || TrinketsHelper.isEquipped(ModItems.MASTER_NINJA_GEAR, self)) {
			if (self.getRandom().nextInt(101) <= 10) {
				f = 0;
				if (!self.isLocalPlayer()) {
					((ServerPlayer) self).getLevel().sendParticles(ParticleTypes.POOF, pos.x(), pos.y(), pos.z(), 1, motion.getX() * -1.0D, -1.0D, motion.getZ() * -1.0D, 0.15);
				}
			}
		}
		return f;
	}
}
