package terracraft.common.world.biome;

import terracraft.Artifacts;
import terracraft.common.init.Biomes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.ParameterUtils;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.function.Consumer;

public class BiomeAdder extends Region {

    public BiomeAdder(ResourceLocation name, RegionType type, int weight) {
        super(name, type, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper)
    {
        //no two biomes can have the exact same parameter values otherwise only one of them will be added (the one that is higher up). Have at least a small difference on one of the parameters to avoid issues.
        if (Artifacts.CONFIG.worldgen.corruptionEnabled) {
            this.addBiome(mapper, ParameterUtils.Temperature.WARM.parameter(), ParameterUtils.Humidity.NEUTRAL.parameter(), ParameterUtils.Continentalness.MUSHROOM_FIELDS.parameter(), ParameterUtils.Erosion.EROSION_2.parameter(), ParameterUtils.Weirdness.FULL_RANGE.parameter(), Climate.Parameter.point(0.0F), 0.0F, Biomes.CORRUPTION);
            this.addBiome(mapper, Climate.Parameter.span(0.55F, 1.0F), Climate.Parameter.span(-0.35F, -0.1F), ParameterUtils.Continentalness.MUSHROOM_FIELDS.parameter(), Climate.Parameter.span(-0.78F, -0.375F), Climate.Parameter.span(-1.0F, 1.0F), Climate.Parameter.point(0.0F), 0.0F, Biomes.CORRUPTION_DESERT);
        }
    }
}
