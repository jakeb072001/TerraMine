package terracraft.common.world;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.CarvingMask;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.carver.WorldCarver;

import java.util.Random;
import java.util.function.Function;

public class CorruptionPitCarver extends WorldCarver<CorruptionPitCarverConfigured> {
    public CorruptionPitCarver(Codec<CorruptionPitCarverConfigured> codec) {
        super(codec);
    }

    public boolean isStartChunk(CorruptionPitCarverConfigured corruptionPitCarverConfiguration, Random random) {
        return random.nextFloat() <= corruptionPitCarverConfiguration.probability;
    }

    public boolean carve(CarvingContext carvingContext, CorruptionPitCarverConfigured corruptionPitCarverConfiguration, ChunkAccess chunkAccess, Function<BlockPos, Holder<Biome>> function, Random random, Aquifer aquifer, ChunkPos chunkPos, CarvingMask carvingMask) {
        int i = (this.getRange() * 2 - 1) * 16;
        double d = chunkPos.getBlockX(random.nextInt(16));
        int j = corruptionPitCarverConfiguration.y.sample(random, carvingContext);
        double e = chunkPos.getBlockZ(random.nextInt(16));
        float f = random.nextFloat() * 6.2831855F;
        float g = corruptionPitCarverConfiguration.verticalRotation.sample(random);
        double h = corruptionPitCarverConfiguration.yScale.sample(random);
        float k = corruptionPitCarverConfiguration.shape.thickness.sample(random);
        int l = (int)((float)i * corruptionPitCarverConfiguration.shape.distanceFactor.sample(random));
        this.doCarve(carvingContext, corruptionPitCarverConfiguration, chunkAccess, function, random.nextLong(), aquifer, d, (double)j, e, k, f, g, 0, l, h, carvingMask);
        return true;
    }

    private void doCarve(CarvingContext carvingContext, CorruptionPitCarverConfigured corruptionPitCarverConfiguration, ChunkAccess chunkAccess, Function<BlockPos, Holder<Biome>> function, long l, Aquifer aquifer, double d, double e, double f, float g, float h, float i, int j, int k, double m, CarvingMask carvingMask) {
        Random random = new Random(l);
        float[] fs = this.initWidthFactors(carvingContext, corruptionPitCarverConfiguration, random);
        float n = 0.0F;
        float o = 0.0F;

        for(int p = j; p < k; ++p) {
            double q = 1.5 + (double)(Mth.sin((float)p * 3.1415927F / (float)k) * g);
            double r = q * m;
            q *= corruptionPitCarverConfiguration.shape.horizontalRadiusFactor.sample(random);
            r = this.updateVerticalRadius(corruptionPitCarverConfiguration, random, r, (float)k, (float)p);
            float s = Mth.cos(i);
            float t = Mth.sin(i);
            d += Mth.cos(h) * s;
            e += t;
            f += Mth.sin(h) * s;
            i *= 0.7F;
            i += o * 0.05F;
            h += n * 0.05F;
            o *= 0.8F;
            n *= 0.5F;
            o += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
            n += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;
            if (random.nextInt(4) != 0) {
                if (!canReach(chunkAccess.getPos(), d, f, p, k, g)) {
                    return;
                }

                this.carveEllipsoid(carvingContext, corruptionPitCarverConfiguration, chunkAccess, function, aquifer, d, e, f, q, r, carvingMask, (carvingContextx, dx, ex, fx, ix) -> {
                    return this.shouldSkip(carvingContextx, fs, dx, ex, fx, ix);
                });
            }
        }

    }

    private float[] initWidthFactors(CarvingContext carvingContext, CorruptionPitCarverConfigured corruptionPitCarverConfiguration, Random random) {
        int i = carvingContext.getGenDepth();
        float[] fs = new float[i];
        float f = 1.0F;

        for(int j = 0; j < i; ++j) {
            if (j == 0 || random.nextInt(corruptionPitCarverConfiguration.shape.widthSmoothness) == 0) {
                f = 1.0F + random.nextFloat() * random.nextFloat();
            }

            fs[j] = f * f;
        }

        return fs;
    }

    private double updateVerticalRadius(CorruptionPitCarverConfigured corruptionPitCarverConfiguration, Random random, double d, float f, float g) {
        float h = 1.0F - Mth.abs(0.5F - g / f) * 2.0F;
        float i = corruptionPitCarverConfiguration.shape.verticalRadiusDefaultFactor + corruptionPitCarverConfiguration.shape.verticalRadiusCenterFactor * h;
        return (double)i * d * (double)Mth.randomBetween(random, 0.75F, 1.0F);
    }

    private boolean shouldSkip(CarvingContext carvingContext, float[] fs, double d, double e, double f, int i) {
        int j = i - carvingContext.getMinGenY();
        return (d * d + f * f) * (double)fs[j - 1] + e * e / 6.0 >= 1.0;
    }
}
