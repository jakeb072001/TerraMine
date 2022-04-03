package terracraft.client.integrations;

import terracraft.common.init.Items;
import terracraft.common.item.ArtifactItem;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import me.shedaniel.rei.plugin.common.displays.DefaultInformationDisplay;
import net.minecraft.core.Registry;
import net.minecraft.world.item.ItemStack;

public class REIPlugin implements REIClientPlugin {

	@Override
	public void registerDisplays(DisplayRegistry recipeHelper) {
		Registry.ITEM.stream()
				.filter(item -> item instanceof ArtifactItem)
				.filter(item -> item != Items.NOVELTY_DRINKING_HAT)
				.map(item -> {
					DefaultInformationDisplay display = DefaultInformationDisplay.createFromEntry(EntryStack.of(VanillaEntryTypes.ITEM, new ItemStack(item)), item.getDescription());
					display.line(((ArtifactItem) item).getREITooltip());
					return display;
				}).forEach(recipeHelper::add);

		// Novelty Drinking Hat
		DefaultInformationDisplay display = DefaultInformationDisplay.createFromEntry(EntryStack.of(VanillaEntryTypes.ITEM, new ItemStack(Items.NOVELTY_DRINKING_HAT)), Items.NOVELTY_DRINKING_HAT.getDescription());
		display.line(((ArtifactItem) Items.PLASTIC_DRINKING_HAT).getREITooltip());
		recipeHelper.add(display);
	}
}
