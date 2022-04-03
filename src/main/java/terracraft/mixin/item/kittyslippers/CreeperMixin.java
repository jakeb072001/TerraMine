package terracraft.mixin.item.kittyslippers;

import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terracraft.common.init.ModItems;
import terracraft.common.trinkets.TrinketsHelper;

@Mixin(Creeper.class)
public abstract class CreeperMixin extends Monster {

	protected CreeperMixin(EntityType<? extends Monster> entityType, Level world) {
		super(entityType, world);
	}

	/**
	 * Add flee goal for players with kitty slippers
	 */
	@Inject(method = "registerGoals", at = @At("TAIL"))
	private void addPlayerFleeGoal(CallbackInfo info) {
		this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Player.class,
				(entity) -> TrinketsHelper.isEquipped(ModItems.KITTY_SLIPPERS, entity), 6.0f, 1.0,
				1.2, EntitySelector.NO_CREATIVE_OR_SPECTATOR::test));
	}
}
