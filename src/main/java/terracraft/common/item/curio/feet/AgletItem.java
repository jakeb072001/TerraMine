package terracraft.common.item.curio.feet;

import dev.emi.trinkets.api.SlotReference;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import terracraft.TerraCraft;
import terracraft.common.item.curio.TrinketTerrariaItem;

import java.util.UUID;

public class AgletItem extends TrinketTerrariaItem {

	public static final AttributeModifier SPEED_BOOST_MODIFIER = new AttributeModifier(UUID.fromString("ac7ab816-2b08-46b6-879d-e5dea34ff307"),
			"terracraft:aglet_movement_speed", 0.05, AttributeModifier.Operation.MULTIPLY_TOTAL);

	@Override
	public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
		AttributeInstance movementSpeed = entity.getAttribute(Attributes.MOVEMENT_SPEED);

		if (movementSpeed == null) {
			TerraCraft.LOGGER.debug("Entity {} missing entity attribute(s)", this);
			return;
		}
		addModifier(movementSpeed, SPEED_BOOST_MODIFIER);
	}

    @Override
	public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
		AttributeInstance movementSpeed = entity.getAttribute(Attributes.MOVEMENT_SPEED);

		if (movementSpeed == null) {
			TerraCraft.LOGGER.debug("Entity {} missing entity attribute(s)", this);
			return;
		}
		removeModifier(movementSpeed, SPEED_BOOST_MODIFIER);
	}
}
