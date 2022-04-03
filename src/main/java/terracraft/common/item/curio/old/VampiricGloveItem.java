package terracraft.common.item.curio.old;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import terracraft.common.events.LivingEntityDamagedCallback;
import terracraft.common.init.ModItems;
import terracraft.common.item.curio.TrinketTerrariaItem;
import terracraft.common.trinkets.TrinketsHelper;

public class VampiricGloveItem extends TrinketTerrariaItem {

	public VampiricGloveItem() {
        LivingEntityDamagedCallback.EVENT.register(VampiricGloveItem::onLivingDamage);
	}

	private static void onLivingDamage(LivingEntity entity, DamageSource source, float amount) {
		if (source.getEntity() instanceof LivingEntity attacker) {
			Entity src = source.getDirectEntity();
			float damageDealt = Math.min(amount, entity.getHealth());
			if (src == attacker && damageDealt > 4 && TrinketsHelper.isEquipped(ModItems.VAMPIRIC_GLOVE, attacker)) {
				attacker.heal(Math.min(2, amount / 4));
			}
		}
	}
}
