package terracraft.common.item.curio.old;

import terracraft.common.init.Items;
import terracraft.common.trinkets.TrinketsHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.Random;

public class ThornPendantItem extends PendantItem {

	public ThornPendantItem() {
		super(ThornPendantItem::applyEffects);
	}

	private static void applyEffects(LivingEntity user, Entity attacker, Random random) {
		if (user != null && attacker != null && TrinketsHelper.isEquipped(Items.THORN_PENDANT, user) && random.nextFloat() < 0.5f) {
			attacker.hurt(DamageSource.thorns(user), 2 + random.nextInt(5));
		}
	}
}
