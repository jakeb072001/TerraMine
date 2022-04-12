package terracraft.common.world;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.feature.JigsawFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;

public class FloatingIslandFeature extends JigsawFeature {
    private static final int FLOATING_ISLAND_SPAWN_HEIGHT = 200;

    public FloatingIslandFeature(Codec<JigsawConfiguration> codec) {
        super(codec, FLOATING_ISLAND_SPAWN_HEIGHT, false, false, context -> true);
    }
}
