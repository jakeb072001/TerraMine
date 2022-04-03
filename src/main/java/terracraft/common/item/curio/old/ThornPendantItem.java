package terracraft.common.item.curio.old;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import terracraft.common.init.ModItems;
import terracraft.common.trinkets.TrinketsHelper;

import java.util.Random;

public class ThornPendantItem extends PendantItem {

	public ThornPendantItem() {
		super(ThornPendantItem::applyEffects);
	}

	private static void applyEffects(LivingEntity user, Entity attacker, Random random) {
		if (user != null && attacker != null && TrinketsHelper.isEquipped(ModItems.THORN_PENDANT, user) && random.nextFloat() < 0.5f) {
			attacker.hurt(DamageSource.thorns(user), 2 + random.nextInt(5));
		}
	}
}
