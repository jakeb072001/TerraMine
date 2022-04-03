package terracraft.mixin.item.cobaltshield;

import terracraft.Artifacts;
import terracraft.common.init.Items;
import terracraft.common.item.curio.TrinketArtifactItem;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin {
	
	@Inject(method = "tick", at = @At("TAIL"))
	private void shieldAttributes(CallbackInfo info) {
		Player self = (Player) (Object) this;
		AttributeInstance knockbackResist = self.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
		AttributeInstance armorAdd = self.getAttribute(Attributes.ARMOR);

		if (self.getMainHandItem().is(Items.COBALT_SHIELD) || self.getOffhandItem().is(Items.COBALT_SHIELD)) {
			TrinketArtifactItem.addModifier(knockbackResist, Artifacts.KNOCKBACK_RESISTANCE_COBALT_MODIFIER);
			TrinketArtifactItem.addModifier(armorAdd, Artifacts.ARMOR_ADD_ONE);
		} else {
			TrinketArtifactItem.removeModifier(knockbackResist, Artifacts.KNOCKBACK_RESISTANCE_COBALT_MODIFIER);
			TrinketArtifactItem.removeModifier(armorAdd, Artifacts.ARMOR_ADD_ONE);
		}
		if (self.getMainHandItem().is(Items.OBSIDIAN_SHIELD) || self.getOffhandItem().is(Items.OBSIDIAN_SHIELD)) {
			TrinketArtifactItem.addModifier(knockbackResist, Artifacts.KNOCKBACK_RESISTANCE_OBSIDIAN_MODIFIER);
			TrinketArtifactItem.addModifier(armorAdd, Artifacts.ARMOR_ADD_TWO);
		} else {
			TrinketArtifactItem.removeModifier(knockbackResist, Artifacts.KNOCKBACK_RESISTANCE_OBSIDIAN_MODIFIER);
			TrinketArtifactItem.removeModifier(armorAdd, Artifacts.ARMOR_ADD_TWO);
		}
	}
}
