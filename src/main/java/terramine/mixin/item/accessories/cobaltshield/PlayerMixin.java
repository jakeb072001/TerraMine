package terramine.mixin.item.accessories.cobaltshield;

import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terramine.common.init.ModItems;
import terramine.common.item.accessories.AccessoryTerrariaItem;
import terramine.common.misc.AccessoriesHelper;

import java.util.UUID;

@Mixin(Player.class)
public abstract class PlayerMixin {

	@Unique
	private static final AttributeModifier KNOCKBACK_RESISTANCE = new AttributeModifier(UUID.fromString("fc169abb-68f0-4068-9b27-cad2e5d9cf7f"),
			"shield_knockback_resistance", 1, AttributeModifier.Operation.ADDITION);

	@Unique
	private static final AttributeModifier ARMOR_ADD_ONE = new AttributeModifier(UUID.fromString("e89ae2cc-57ce-4ee9-817a-0f883b339001"),
			"cobalt_shield_armor_one", 1, AttributeModifier.Operation.ADDITION);

	@Unique
	private static final AttributeModifier ARMOR_ADD_TWO = new AttributeModifier(UUID.fromString("248c3717-9eff-456b-86a3-caa7d3743e2f"),
			"obsidian_shield_armor_two", 2, AttributeModifier.Operation.ADDITION);
	
	@Inject(method = "tick", at = @At("TAIL"))
	private void shieldAttributes(CallbackInfo info) {
		Player self = (Player) (Object) this;
		AttributeInstance knockbackResist = self.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
		AttributeInstance armorAdd = self.getAttribute(Attributes.ARMOR);

		if (knockbackResist != null && armorAdd != null) {
			if (self.getMainHandItem().is(ModItems.COBALT_SHIELD) || self.getOffhandItem().is(ModItems.COBALT_SHIELD) || AccessoriesHelper.isEquipped(ModItems.COBALT_SHIELD, self)) {
				AccessoryTerrariaItem.addModifier(knockbackResist, KNOCKBACK_RESISTANCE);
				AccessoryTerrariaItem.addModifier(armorAdd, ARMOR_ADD_ONE);
			} else {
				AccessoryTerrariaItem.removeModifier(knockbackResist, KNOCKBACK_RESISTANCE);
				AccessoryTerrariaItem.removeModifier(armorAdd, ARMOR_ADD_ONE);
			}
			if (self.getMainHandItem().is(ModItems.OBSIDIAN_SHIELD) || self.getOffhandItem().is(ModItems.OBSIDIAN_SHIELD) || AccessoriesHelper.isEquipped(ModItems.OBSIDIAN_SHIELD, self)) {
				AccessoryTerrariaItem.addModifier(knockbackResist, KNOCKBACK_RESISTANCE);
				AccessoryTerrariaItem.addModifier(armorAdd, ARMOR_ADD_TWO);
			} else {
				AccessoryTerrariaItem.removeModifier(knockbackResist, KNOCKBACK_RESISTANCE);
				AccessoryTerrariaItem.removeModifier(armorAdd, ARMOR_ADD_TWO);
			}
		}
	}
}
