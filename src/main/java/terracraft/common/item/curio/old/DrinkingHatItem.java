package terracraft.common.item.curio.old;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import terracraft.TerraCraft;
import terracraft.common.init.ModItems;
import terracraft.common.item.curio.TrinketTerrariaItem;

import java.util.List;

public class DrinkingHatItem extends TrinketTerrariaItem {

    @Override
	public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags) {
		if (TerraCraft.CONFIG.general.showTooltips) {
			if (this == ModItems.NOVELTY_DRINKING_HAT) {
				// Novelty drinking hat description is the same as plastic, but with an extra line appended
				appendTooltipDescription(tooltip, ModItems.PLASTIC_DRINKING_HAT.getDescriptionId() + ".tooltip");
			}
		}
		super.appendHoverText(stack, world, tooltip, flags);
	}

	@Override
	protected SoundInfo getEquipSoundInfo() {
		return new SoundInfo(SoundEvents.BOTTLE_FILL);
	}
}
