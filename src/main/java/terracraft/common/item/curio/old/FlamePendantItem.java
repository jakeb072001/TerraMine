package terracraft.common.item.curio.old;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import terracraft.common.init.ModItems;
import terracraft.common.trinkets.TrinketsHelper;
import terracraft.mixin.accessors.DamageSourceAccessor;

import java.util.Random;

public class FlamePendantItem extends PendantItem {

	public FlamePendantItem() {
		super(FlamePendantItem::applyEffects);
	}

	private static void applyEffects(LivingEntity user, Entity attacker, Random random) {
		if (user != null && attacker != null && TrinketsHelper.isEquipped(ModItems.FLAME_PENDANT, user) && random.nextFloat() < 0.4f) {
			attacker.setSecondsOnFire(10);
			//noinspection ConstantConditions
			DamageSource setFireSource = ((DamageSourceAccessor) (new EntityDamageSource("onFire", user))).terracraft$callSetIsFire();
			attacker.hurt(setFireSource, 2);
		}
	}
}
