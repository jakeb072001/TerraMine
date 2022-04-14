package terracraft.client.integrations;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import me.shedaniel.rei.plugin.common.displays.DefaultInformationDisplay;
import net.minecraft.core.Registry;
import net.minecraft.world.item.ItemStack;
import terracraft.common.init.ModItems;
import terracraft.common.item.TerrariaItem;

public class REIPlugin implements REIClientPlugin {

	@Override
	public void registerDisplays(DisplayRegistry recipeHelper) {
		Registry.ITEM.stream()
				.filter(item -> item instanceof TerrariaItem)
				.map(item -> {
					DefaultInformationDisplay display = DefaultInformationDisplay.createFromEntry(EntryStack.of(VanillaEntryTypes.ITEM, new ItemStack(item)), item.getDescription());
					display.line(((TerrariaItem) item).getREITooltip());
					return display;
				}).forEach(recipeHelper::add);
	}
}
