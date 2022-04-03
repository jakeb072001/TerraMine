package terracraft.mixin.item.goldenhook;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import terracraft.common.init.ModItems;
import terracraft.common.trinkets.TrinketsHelper;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

	@Shadow @Nullable protected Player lastHurtByPlayer;

	public LivingEntityMixin(EntityType<?> type, Level world) {
		super(type, world);
	}

	@ModifyArg(method = "dropExperience", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ExperienceOrb;award(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/phys/Vec3;I)V"))
	private int modifyXp(int originalXp) {
		//noinspection ConstantConditions
		if ((Object) this instanceof Player || !TrinketsHelper.isEquipped(ModItems.GOLDEN_HOOK, this.lastHurtByPlayer)) {
			return originalXp;
		}

		int experienceBonus = (int) (originalXp * 0.75);
		return originalXp + experienceBonus;
	}
}
