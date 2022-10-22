package terramine.common.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CarverDebugSettings;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;

public class CorruptionPitCarverConfigured extends CarverConfiguration {
    public static final Codec<CorruptionPitCarverConfigured> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(CarverConfiguration.CODEC.forGetter((corruptionPitCarverConfigured) -> {
            return corruptionPitCarverConfigured;
        }), FloatProvider.CODEC.fieldOf("vertical_rotation").forGetter((corruptionPitCarverConfigured) -> {
            return corruptionPitCarverConfigured.verticalRotation;
        }), PitShapeConfiguration.CODEC.fieldOf("shape").forGetter((corruptionPitCarverConfigured) -> {
            return corruptionPitCarverConfigured.shape;
        })).apply(instance, CorruptionPitCarverConfigured::new);
    });
    public final FloatProvider verticalRotation;
    public final PitShapeConfiguration shape;

    public CorruptionPitCarverConfigured(float f, HeightProvider heightProvider, FloatProvider floatProvider, VerticalAnchor verticalAnchor, CarverDebugSettings carverDebugSettings, FloatProvider floatProvider2, PitShapeConfiguration corruptionPitCarverConfigured) {
        super(f, heightProvider, floatProvider, verticalAnchor, carverDebugSettings);
        this.verticalRotation = floatProvider2;
        this.shape = corruptionPitCarverConfigured;
    }

    public CorruptionPitCarverConfigured(CarverConfiguration carverConfiguration, FloatProvider floatProvider, PitShapeConfiguration pitShapeConfiguration) {
        this(carverConfiguration.probability, carverConfiguration.y, carverConfiguration.yScale, carverConfiguration.lavaLevel, carverConfiguration.debugSettings, floatProvider, pitShapeConfiguration);
    }

    public static class PitShapeConfiguration {
        public static final Codec<PitShapeConfiguration> CODEC = RecordCodecBuilder.create((instance) -> {
            return instance.group(FloatProvider.CODEC.fieldOf("distance_factor").forGetter((pitShapeConfiguration) -> {
                return pitShapeConfiguration.distanceFactor;
            }), FloatProvider.CODEC.fieldOf("thickness").forGetter((pitShapeConfiguration) -> {
                return pitShapeConfiguration.thickness;
            }), ExtraCodecs.NON_NEGATIVE_INT.fieldOf("width_smoothness").forGetter((pitShapeConfiguration) -> {
                return pitShapeConfiguration.widthSmoothness;
            }), FloatProvider.CODEC.fieldOf("horizontal_radius_factor").forGetter((pitShapeConfiguration) -> {
                return pitShapeConfiguration.horizontalRadiusFactor;
            }), Codec.FLOAT.fieldOf("vertical_radius_default_factor").forGetter((pitShapeConfiguration) -> {
                return pitShapeConfiguration.verticalRadiusDefaultFactor;
            }), Codec.FLOAT.fieldOf("vertical_radius_center_factor").forGetter((pitShapeConfiguration) -> {
                return pitShapeConfiguration.verticalRadiusCenterFactor;
            })).apply(instance, PitShapeConfiguration::new);
        });
        public final FloatProvider distanceFactor;
        public final FloatProvider thickness;
        public final int widthSmoothness;
        public final FloatProvider horizontalRadiusFactor;
        public final float verticalRadiusDefaultFactor;
        public final float verticalRadiusCenterFactor;

        public PitShapeConfiguration(FloatProvider floatProvider, FloatProvider floatProvider2, int i, FloatProvider floatProvider3, float f, float g) {
            this.widthSmoothness = i;
            this.horizontalRadiusFactor = floatProvider3;
            this.verticalRadiusDefaultFactor = f;
            this.verticalRadiusCenterFactor = g;
            this.distanceFactor = floatProvider;
            this.thickness = floatProvider2;
        }
    }
}
