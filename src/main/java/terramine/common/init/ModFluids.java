package terramine.common.init;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import terramine.TerraMine;
import terramine.common.block.fluids.ShimmerFluid;

public class ModFluids {
    public static final FlowingFluid STILL_SHIMMER = register("shimmer", new ShimmerFluid.Source());
    public static final FlowingFluid FLOWING_SHIMMER = register("flowing_shimmer", new ShimmerFluid.Flowing());

    private static <T extends Fluid> T register(String string, T fluid) {
        return Registry.register(BuiltInRegistries.FLUID, TerraMine.id(string), fluid);
    }
}
