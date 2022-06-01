package terramine.mixin.item.cobaltshield;

import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terramine.TerraMine;
import terramine.common.init.ModItems;
import terramine.common.item.curio.TrinketTerrariaItem;

@Mixin(Player.class)
public abstract class PlayerMixin {
	
	@Inject(method = "tick", at = @At("TAIL"))
	private void shieldAttributes(CallbackInfo info) {
		Player self = (Player) (Object) this;
		AttributeInstance knockbackResist = self.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
		AttributeInstance armorAdd = self.getAttribute(Attributes.ARMOR);

		if (self.getMainHandItem().is(ModItems.COBALT_SHIELD) || self.getOffhandItem().is(ModItems.COBALT_SHIELD)) {
			TrinketTerrariaItem.addModifier(knockbackResist, TerraMine.KNOCKBACK_RESISTANCE_COBALT_MODIFIER);
			TrinketTerrariaItem.addModifier(armorAdd, TerraMine.ARMOR_ADD_ONE);
		} else {
			TrinketTerrariaItem.removeModifier(knockbackResist, TerraMine.KNOCKBACK_RESISTANCE_COBALT_MODIFIER);
			TrinketTerrariaItem.removeModifier(armorAdd, TerraMine.ARMOR_ADD_ONE);
		}
		if (self.getMainHandItem().is(ModItems.OBSIDIAN_SHIELD) || self.getOffhandItem().is(ModItems.OBSIDIAN_SHIELD)) {
			TrinketTerrariaItem.addModifier(knockbackResist, TerraMine.KNOCKBACK_RESISTANCE_OBSIDIAN_MODIFIER);
			TrinketTerrariaItem.addModifier(armorAdd, TerraMine.ARMOR_ADD_TWO);
		} else {
			TrinketTerrariaItem.removeModifier(knockbackResist, TerraMine.KNOCKBACK_RESISTANCE_OBSIDIAN_MODIFIER);
			TrinketTerrariaItem.removeModifier(armorAdd, TerraMine.ARMOR_ADD_TWO);
		}
	}
}
