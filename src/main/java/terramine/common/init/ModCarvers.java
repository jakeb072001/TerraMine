package terramine.common.init;

import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.TrapezoidFloat;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.*;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import terramine.common.world.CorruptionPitCarver;
import terramine.common.world.CorruptionPitCarverConfigured;

import static terramine.TerraMine.id;

public class ModCarvers {
    public static final ResourceKey<ConfiguredWorldCarver<?>> CORRUPTION_PIT = register("corruption_pit");
    public static final WorldCarver<CorruptionPitCarverConfigured> CORRUPTION_PIT_CARVER;

    private static ResourceKey<ConfiguredWorldCarver<?>> register(String string) {
        return ResourceKey.create(Registries.CONFIGURED_CARVER, id(string));
    }

    private static <C extends CarverConfiguration, F extends WorldCarver<C>> F register(String string, F worldCarver) {
        return Registry.register(BuiltInRegistries.CARVER, id(string), worldCarver);
    }

    // todo: fix corruption pit registration, using minecraft canyon temporarily to avoid issues with datagen
    public static void bootstrap(BootstapContext<ConfiguredWorldCarver<?>> context) {
        context.register(CORRUPTION_PIT, WorldCarver.CANYON.configured(new CanyonCarverConfiguration(0.01F, UniformHeight.of(VerticalAnchor.absolute(10), VerticalAnchor.absolute(80)), ConstantFloat.of(3.0F), VerticalAnchor.aboveBottom(8), CarverDebugSettings.of(false, Blocks.CRIMSON_BUTTON.defaultBlockState()), HolderSet.direct(), UniformFloat.of(-0.125F, 0.125F), new CanyonCarverConfiguration.CanyonShapeConfiguration(UniformFloat.of(0.75F, 1.0F), TrapezoidFloat.of(0.0F, 6.0F, 2.0F), 3, UniformFloat.of(0.75F, 1.0F), 1.0F, 0.0F))));
        //context.register(CORRUPTION_PIT, CORRUPTION_PIT_CARVER.configured(new CorruptionPitCarverConfigured(0.01F, UniformHeight.of(VerticalAnchor.absolute(10), VerticalAnchor.absolute(80)), ConstantFloat.of(3.0F), VerticalAnchor.aboveBottom(8), CarverDebugSettings.of(false, Blocks.CRIMSON_BUTTON.defaultBlockState()), HolderSet.direct(), UniformFloat.of(-0.125F, 0.125F), new CorruptionPitCarverConfigured.PitShapeConfiguration(UniformFloat.of(0.75F, 1.0F), TrapezoidFloat.of(0.0F, 6.0F, 2.0F), 3, UniformFloat.of(0.75F, 1.0F), 1.0F, 0.0F))));
    }

    static {
        CORRUPTION_PIT_CARVER = register("corruption_pit", new CorruptionPitCarver(CorruptionPitCarverConfigured.CODEC));
    }
}
