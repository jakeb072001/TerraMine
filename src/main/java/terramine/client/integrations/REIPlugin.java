package terramine.client.integrations;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import me.shedaniel.rei.plugin.common.displays.DefaultInformationDisplay;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.ItemStack;
import terramine.common.item.TerrariaItem;
import terramine.common.item.armor.TerrariaArmor;
import terramine.common.item.armor.vanity.VanityArmor;

public class REIPlugin implements REIClientPlugin {

	@Override
	public void registerDisplays(DisplayRegistry recipeHelper) {
		Registry.ITEM.stream()
				.filter(item -> item instanceof TerrariaItem)
				.map(item -> {
					DefaultInformationDisplay display = DefaultInformationDisplay.createFromEntry(EntryStack.of(VanillaEntryTypes.ITEM, new ItemStack(item)), item.getDescription());
					for (String string : ((TerrariaItem) item).getREITooltip()) {
						display.line(new TextComponent(string));
					}
					return display;
				}).forEach(recipeHelper::add);

		Registry.ITEM.stream()
				.filter(item -> item instanceof TerrariaArmor && !(item instanceof VanityArmor))
				.map(item -> {
					DefaultInformationDisplay display = DefaultInformationDisplay.createFromEntry(EntryStack.of(VanillaEntryTypes.ITEM, new ItemStack(item)), item.getDescription());
					for (String string : ((TerrariaArmor) item).getREITooltip()) {
						display.line(new TextComponent(string));
					}
					if (((TerrariaArmor) item).getREISetBonusTooltip() != null) {
						for (String string : ((TerrariaArmor) item).getREISetBonusTooltip()) {
							display.line(new TextComponent(string));
						}
					}
					return display;
				}).forEach(recipeHelper::add);
	}
}
