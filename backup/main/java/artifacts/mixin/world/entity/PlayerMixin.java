package terracraft.mixin.world.entity;

import terracraft.common.entity.FallingStarEntity;
import terracraft.common.init.Components;
import terracraft.common.init.Entities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {

	public PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
		super(entityType, level);
	}
	private Random rand = new Random();

	@Inject(method = "tick", at = @At("TAIL"))
	private void manaTickRegen(CallbackInfo ci) {
		Components.MANA_HANDLER.get((Player) (Object) this).update();
		if (level != null && level.dimensionType().bedWorks() && !level.isDay()) { // handles spawning stars randomly during the night, not the best way to do it most likely, but it works for now.
			if (rand.nextInt(16800) <= 21) {
				FallingStarEntity star = Entities.FALLING_STAR.create(level);
				if (star != null && blockPosition().getY() >= 50) {
					star.setPos(blockPosition().getX() + rand.nextInt(12), blockPosition().getY() + 30, blockPosition().getZ() + rand.nextInt(12));
					level.addFreshEntity(star);
				}
			}
		}
	}

	@Inject(method = "aiStep", at = @At("TAIL"))
	private void manaTickRegenMoving(CallbackInfo ci) {
		Components.MANA_HANDLER.get((Player) (Object) this).updateMoving();
	}
}
