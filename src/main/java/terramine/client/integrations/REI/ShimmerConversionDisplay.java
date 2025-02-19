package terramine.client.integrations.REI;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.plugin.common.BuiltinPlugin;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ShimmerConversionDisplay implements Display {
    public static final DisplaySerializer<ShimmerConversionDisplay> SERIALIZER;
    private final EntryIngredient input;
    private final EntryIngredient output;
    private final Component name = Component.translatable("rei.shimmer_conversion_display.title");
    private final boolean isTwoWay;

    public ShimmerConversionDisplay(EntryIngredient input, EntryIngredient output, boolean isTwoWay) {
        this.input = input;
        this.output = output;
        this.isTwoWay = isTwoWay;
    }

    public ShimmerConversionDisplay(EntryStack<?> input, EntryStack<?> output, boolean isTwoWay) {
        this.input = EntryIngredient.of(input);
        this.output = EntryIngredient.of(output);
        this.isTwoWay = isTwoWay;
    }

    public EntryIngredient getInput() {
        return this.input;
    }

    public EntryIngredient getOutput() {
        return this.output;
    }

    public boolean getIfTwoWay() {
        return this.isTwoWay;
    }

    public Component getName() {
        return this.name;
    }

    public List<EntryIngredient> getInputEntries() {
        return Collections.singletonList(this.input);
    }

    public List<EntryIngredient> getOutputEntries() {
        return Collections.singletonList(this.output);
    }

    public CategoryIdentifier<?> getCategoryIdentifier() {
        return REIPlugin.SHIMMER_CONVERSION_DISPLAY;
    }

    public Optional<ResourceLocation> getDisplayLocation() {
        return Optional.empty();
    }

    public DisplaySerializer<? extends Display> getSerializer() {
        return SERIALIZER;
    }

    static {
        SERIALIZER = DisplaySerializer.of(RecordCodecBuilder.mapCodec((instance) ->
                instance.group(EntryIngredient.codec().fieldOf("input").forGetter(ShimmerConversionDisplay::getInput), EntryIngredient.codec().fieldOf("output").forGetter(ShimmerConversionDisplay::getOutput), ComponentSerialization.CODEC.fieldOf("name").forGetter(ShimmerConversionDisplay::getName), Codec.BOOL.fieldOf("isTwoWay").forGetter(ShimmerConversionDisplay::getIfTwoWay)).apply(instance, (input, output, name, isTwoWay) ->
                        (new ShimmerConversionDisplay(input, output, isTwoWay)))), StreamCodec.composite(EntryIngredient.streamCodec(), ShimmerConversionDisplay::getInput, EntryIngredient.streamCodec(), ShimmerConversionDisplay::getOutput, ComponentSerialization.STREAM_CODEC, ShimmerConversionDisplay::getName, ByteBufCodecs.BOOL, ShimmerConversionDisplay::getIfTwoWay, (input, output, name, isTwoWay) ->
                (new ShimmerConversionDisplay(input, output, isTwoWay))));
    }
}
