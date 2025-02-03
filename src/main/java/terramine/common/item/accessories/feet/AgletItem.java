package terramine.common.item.accessories.feet;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import terramine.TerraMine;
import terramine.common.item.accessories.AccessoryTerrariaItem;

public class AgletItem extends AccessoryTerrariaItem {

	public static final AttributeModifier SPEED_BOOST_MODIFIER = new AttributeModifier(TerraMine.id("aglet_movement_speed"), 0.05, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

	public AgletItem(ResourceKey<Item> key) {
		super(key);
	}

	@Override
	public void onEquip(ItemStack stack, Player player) {
		AttributeInstance movementSpeed = player.getAttribute(Attributes.MOVEMENT_SPEED);

		if (movementSpeed == null) {
			TerraMine.LOGGER.debug("Entity {} missing entity attribute(s)", this);
			return;
		}
		addModifier(movementSpeed, SPEED_BOOST_MODIFIER);
	}

    @Override
	public void onUnequip(ItemStack stack, Player player) {
		AttributeInstance movementSpeed = player.getAttribute(Attributes.MOVEMENT_SPEED);

		if (movementSpeed == null) {
			TerraMine.LOGGER.debug("Entity {} missing entity attribute(s)", this);
			return;
		}
		removeModifier(movementSpeed, SPEED_BOOST_MODIFIER);
	}
}
