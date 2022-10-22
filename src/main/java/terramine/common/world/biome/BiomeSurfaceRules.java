package terramine.common.world.biome;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import terramine.common.init.ModBiomes;
import terramine.common.init.ModBlocks;

public class BiomeSurfaceRules extends SurfaceRules {
    private static final RuleSource DIRT = makeStateRule(Blocks.DIRT);

    // Corruption
    private static final RuleSource CORRUPTED_STONE = makeStateRule(ModBlocks.CORRUPTED_STONE);
    private static final RuleSource CORRUPTED_DEEPSLATE = makeStateRule(ModBlocks.CORRUPTED_DEEPSLATE); // todo: get corruption surface rules to change all blocks up to and including some deepslate
    private static final RuleSource CORRUPTED_SANDSTONE = makeStateRule(ModBlocks.CORRUPTED_SANDSTONE);
    private static final RuleSource CORRUPTED_GRAVEL = makeStateRule(ModBlocks.CORRUPTED_GRAVEL);
    private static final RuleSource CORRUPTED_GRASS = makeStateRule(ModBlocks.CORRUPTED_GRASS);
    private static final RuleSource CORRUPTED_SAND = makeStateRule(ModBlocks.CORRUPTED_SAND);

    // Crimson
    private static final RuleSource CRIMSON_STONE = makeStateRule(ModBlocks.CRIMSON_STONE);
    private static final RuleSource CRIMSON_DEEPSLATE = makeStateRule(ModBlocks.CRIMSON_DEEPSLATE);
    private static final RuleSource CRIMSON_SANDSTONE = makeStateRule(ModBlocks.CRIMSON_SANDSTONE);
    private static final RuleSource CRIMSON_GRAVEL = makeStateRule(ModBlocks.CRIMSON_GRAVEL);
    private static final RuleSource CRIMSON_GRASS = makeStateRule(ModBlocks.CRIMSON_GRASS);
    private static final RuleSource CRIMSON_SAND = makeStateRule(ModBlocks.CRIMSON_SAND);

    public static RuleSource makeRules()
    {
        ConditionSource isAtOrAboveWaterLevel = waterBlockCheck(-1, 0);
        ConditionSource isOceanFloorWaterLevel = waterBlockCheck(-10, 0);
        RuleSource corruptGrassSurface = sequence(ifTrue(ON_FLOOR, sequence(ifTrue(isAtOrAboveWaterLevel, CORRUPTED_GRASS))), sequence(ifTrue(UNDER_FLOOR, sequence(ifTrue(isOceanFloorWaterLevel, DIRT), CORRUPTED_GRAVEL))), CORRUPTED_STONE);
        RuleSource corruptSandSurface = sequence(ifTrue(ON_FLOOR, sequence(ifTrue(isAtOrAboveWaterLevel, CORRUPTED_SAND))), sequence(ifTrue(UNDER_FLOOR, CORRUPTED_SAND)), CORRUPTED_SANDSTONE);
        RuleSource crimsonGrassSurface = sequence(ifTrue(ON_FLOOR, sequence(ifTrue(isAtOrAboveWaterLevel, CRIMSON_GRASS))), sequence(ifTrue(UNDER_FLOOR, sequence(ifTrue(isOceanFloorWaterLevel, DIRT), CRIMSON_GRAVEL))), CRIMSON_STONE);
        RuleSource crimsonSandSurface = sequence(ifTrue(ON_FLOOR, sequence(ifTrue(isAtOrAboveWaterLevel, CRIMSON_SAND))), sequence(ifTrue(UNDER_FLOOR, CRIMSON_SAND)), CRIMSON_SANDSTONE);

        return sequence(
                ifTrue(isBiome(ModBiomes.CORRUPTION), corruptGrassSurface),
                ifTrue(isBiome(ModBiomes.CORRUPTION_DESERT), corruptSandSurface),
                ifTrue(isBiome(ModBiomes.CRIMSON), crimsonGrassSurface),
                ifTrue(isBiome(ModBiomes.CRIMSON_DESERT), crimsonSandSurface)
        );
    }

    private static RuleSource makeStateRule(Block block)
    {
        return state(block.defaultBlockState());
    }
}
