package terramine.client.integrations.REI;

import com.google.common.collect.Lists;
import me.shedaniel.math.Dimension;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import terramine.common.init.ModItems;

import java.util.List;

public class ShimmerConversionCategory<T extends ShimmerConversionDisplay> implements DisplayCategory<T> {
    @Override
    public CategoryIdentifier<T> getCategoryIdentifier() {
        return (CategoryIdentifier<T>) REIPlugin.SHIMMER_CONVERSION_DISPLAY;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("rei.shimmer_conversion_display.title");
    }

    @Override
    public Renderer getIcon() {
        return EntryStack.of(VanillaEntryTypes.ITEM, new ItemStack(ModItems.SHIMMER_BUCKET));
    }

    @Override
    public List<Widget> setupDisplay(T display, Rectangle bounds) {
        Point startPoint = new Point(bounds.getCenterX() - 41, bounds.getCenterY() - 13);
        List<Widget> widgets = Lists.newArrayList();

        widgets.add(Widgets.createRecipeBase(bounds));

        if (display.getIfTwoWay()) {
            widgets.add(new TwoWayArrowWidget(new Rectangle(new Point(startPoint.x + 27, startPoint.y + 4), new Dimension(24, 17))));

            widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x + 61, startPoint.y + 5)));
            widgets.add(Widgets.createSlot(new Point(startPoint.x + 61, startPoint.y + 5))
                    .entries(display.getOutputEntries().get(0))
                    .disableBackground()
                    .markOutput());

            widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x + 4, startPoint.y + 5)));
            widgets.add(Widgets.createSlot(new Point(startPoint.x + 4, startPoint.y + 5))
                    .entries(display.getInputEntries().get(0))
                    .disableBackground()
                    .markInput());
        } else {
            widgets.add(Widgets.createArrow(new Point(startPoint.x + 27, startPoint.y + 4)));

            widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x + 61, startPoint.y + 5)));
            widgets.add(Widgets.createSlot(new Point(startPoint.x + 61, startPoint.y + 5))
                    .entries(display.getOutputEntries().get(0))
                    .disableBackground()
                    .markOutput());

            widgets.add(Widgets.createSlot(new Point(startPoint.x + 4, startPoint.y + 5))
                    .entries(display.getInputEntries().get(0))
                    .markInput());
        }

        return widgets;
    }
}
