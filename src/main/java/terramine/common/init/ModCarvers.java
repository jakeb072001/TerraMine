package terramine.common.init;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.TrapezoidFloat;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.*;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import terramine.common.world.CorruptionPitCarver;
import terramine.common.world.CorruptionPitCarverConfigured;

public class ModCarvers {
    public static final Holder<ConfiguredWorldCarver<CorruptionPitCarverConfigured>> CORRUPTION_PIT;
    public static final WorldCarver<CorruptionPitCarverConfigured> CORRUPTION_PIT_CARVER;

    private static <WC extends CarverConfiguration> Holder<ConfiguredWorldCarver<WC>> register(String string, ConfiguredWorldCarver<WC> configuredWorldCarver) {
        return BuiltinRegistries.registerExact(BuiltinRegistries.CONFIGURED_CARVER, string, configuredWorldCarver);
    }

    private static <C extends CarverConfiguration, F extends WorldCarver<C>> F register(String string, F worldCarver) {
        return Registry.register(Registry.CARVER, string, worldCarver);
    }

    static {
        CORRUPTION_PIT_CARVER = register("corruption_pit", new CorruptionPitCarver(CorruptionPitCarverConfigured.CODEC));
        CORRUPTION_PIT = register("corruption_pit", CORRUPTION_PIT_CARVER.configured(new CorruptionPitCarverConfigured(0.01F, UniformHeight.of(VerticalAnchor.absolute(10), VerticalAnchor.absolute(80)), ConstantFloat.of(3.0F), VerticalAnchor.aboveBottom(8), CarverDebugSettings.of(false, Blocks.CRIMSON_BUTTON.defaultBlockState()), UniformFloat.of(-0.125F, 0.125F), new CorruptionPitCarverConfigured.PitShapeConfiguration(UniformFloat.of(0.75F, 1.0F), TrapezoidFloat.of(0.0F, 6.0F, 2.0F), 3, UniformFloat.of(0.75F, 1.0F), 1.0F, 0.0F))));
    }
}
