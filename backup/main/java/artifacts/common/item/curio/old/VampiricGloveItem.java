package terracraft.common.item.curio.old;

import terracraft.common.events.LivingEntityDamagedCallback;
import terracraft.common.init.Items;
import terracraft.common.item.curio.TrinketArtifactItem;
import terracraft.common.trinkets.TrinketsHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class VampiricGloveItem extends TrinketArtifactItem {

	public VampiricGloveItem() {
        LivingEntityDamagedCallback.EVENT.register(VampiricGloveItem::onLivingDamage);
	}

	private static void onLivingDamage(LivingEntity entity, DamageSource source, float amount) {
		if (source.getEntity() instanceof LivingEntity attacker) {
			Entity src = source.getDirectEntity();
			float damageDealt = Math.min(amount, entity.getHealth());
			if (src == attacker && damageDealt > 4 && TrinketsHelper.isEquipped(Items.VAMPIRIC_GLOVE, attacker)) {
				attacker.heal(Math.min(2, amount / 4));
			}
		}
	}
}
