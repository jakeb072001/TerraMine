package terramine.client.render.color;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.ARGB;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import terramine.common.item.dye.BasicDye;

public record TerrariaDye(int defaultColor) implements ItemTintSource {
    public static final MapCodec<TerrariaDye> MAP_CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(ExtraCodecs.RGB_COLOR_CODEC.fieldOf("default").forGetter(TerrariaDye::defaultColor)).apply(instance, TerrariaDye::new));

    public TerrariaDye() {
        this(-13083194);
    }

    public TerrariaDye(int defaultColor) {
        this.defaultColor = defaultColor;
    }

    public int calculate(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity) {
        if (itemStack.getItem() instanceof BasicDye basicDye) {
            return ARGB.opaque(basicDye.getColourInt());
        }

        return ARGB.opaque(this.defaultColor);
    }

    public @NotNull MapCodec<TerrariaDye> type() {
        return MAP_CODEC;
    }

    public int defaultColor() {
        return this.defaultColor;
    }
}
