package terramine.common.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import terramine.TerraMine;

import java.util.Collections;
import java.util.List;

public abstract class TerrariaItemConfigurable extends Item {

	public TerrariaItemConfigurable(Properties properties) {
		super(properties);
	}

	public TerrariaItemConfigurable() {
		this(new Properties());
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void appendHoverText(ItemStack stack, Level world, List<Component> tooltip, TooltipFlag flags) {
		if (TerraMine.CONFIG.general.showTooltips) {
			appendTooltipDescription(tooltip, this.getDescriptionId() + ".tooltip");
		}
	}

	public Component getREITooltip() {
		return new TextComponent(Language.getInstance().getOrDefault(this.getDescriptionId() + ".tooltip").replace("\n", " ").replace("%%", "%"));
	}

	protected void appendTooltipDescription(List<Component> tooltip, String translKey) {
		String[] lines = String.format(Language.getInstance().getOrDefault(translKey), getTooltipDescriptionArguments().toArray()).split("\n");

		for (String line : lines) {
			tooltip.add(new TextComponent(line).withStyle(ChatFormatting.GRAY));
		}
	}

	protected List<String> getTooltipDescriptionArguments() {
		return Collections.emptyList();
	}
}
