package terracraft.common.world.biome;

import terracraft.common.init.Biomes;
import terracraft.common.init.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class BiomeSurfaceRules extends SurfaceRules {
    private static final RuleSource DIRT = makeStateRule(net.minecraft.world.level.block.Blocks.DIRT); // maybe make corrupt dirt, not sure... could just be slightly tinted darker
    private static final RuleSource CORRUPTED_STONE = makeStateRule(ModBlocks.CORRUPTED_STONE);
    private static final RuleSource CORRUPTED_DEEPSLATE = makeStateRule(ModBlocks.CORRUPTED_DEEPSLATE);
    private static final RuleSource CORRUPTED_SANDSTONE = makeStateRule(ModBlocks.CORRUPTED_SANDSTONE);
    private static final RuleSource CORRUPTED_GRASS = makeStateRule(ModBlocks.CORRUPTED_GRASS);
    private static final RuleSource CORRUPTED_SAND = makeStateRule(ModBlocks.CORRUPTED_SAND);

    public static RuleSource makeRules()
    {
        ConditionSource isAtOrAboveWaterLevel = waterBlockCheck(-1, 0);
        ConditionSource isOceanFloorWaterLevel = waterBlockCheck(-6, 0);
        RuleSource grassSurface = sequence(ifTrue(ON_FLOOR, sequence(ifTrue(isAtOrAboveWaterLevel, CORRUPTED_GRASS))), sequence(ifTrue(UNDER_FLOOR, sequence(ifTrue(isOceanFloorWaterLevel, DIRT), CORRUPTED_SAND)), CORRUPTED_STONE));
        RuleSource sandSurface = sequence(ifTrue(ON_FLOOR, sequence(ifTrue(isAtOrAboveWaterLevel, CORRUPTED_SAND))), sequence(ifTrue(UNDER_FLOOR, CORRUPTED_SAND), CORRUPTED_SANDSTONE));

        return sequence(
                ifTrue(isBiome(Biomes.CORRUPTION), grassSurface),
                ifTrue(isBiome(Biomes.CORRUPTION_DESERT), sandSurface)
        );
    }

    private static RuleSource makeStateRule(Block block)
    {
        return state(block.defaultBlockState());
    }
}
