package terramine.common.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import terramine.common.init.ModFeatures;

import java.util.Optional;

public class TerrariaJigsawStructure extends Structure {
    public static final Codec<TerrariaJigsawStructure> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            TerrariaJigsawStructure.settingsCodec(instance),
            StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(structure -> structure.startPool),
            StructureTemplatePool.CODEC.fieldOf("start_room_pool").forGetter(structure -> structure.startRoomPool),
            ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name").forGetter(structure -> structure.startJigsawName),
            Codec.intRange(0, 30).fieldOf("size").forGetter(structure -> structure.maxDepth),
            HeightProvider.CODEC.fieldOf("start_height").forGetter(structure -> structure.startHeight),
            Codec.BOOL.fieldOf("use_bounding_box_hack").orElse(false).forGetter(structure -> structure.useExpansionHack),
            Heightmap.Types.CODEC.optionalFieldOf("project_start_to_heightmap").forGetter(structure -> structure.projectStartToHeightmap),
            Codec.intRange(1, 128).fieldOf("max_distance_from_center").forGetter(structure -> structure.maxDistanceFromCenter)
    ).apply(instance, TerrariaJigsawStructure::new));

    private final Holder<StructureTemplatePool> startPool;
    private final Holder<StructureTemplatePool> startRoomPool;
    private final Optional<ResourceLocation> startJigsawName;
    private final int maxDepth;
    private final HeightProvider startHeight;
    private final boolean useExpansionHack;
    private final Optional<Heightmap.Types> projectStartToHeightmap;
    private final int maxDistanceFromCenter;

    public TerrariaJigsawStructure(StructureSettings structureSettings, Holder<StructureTemplatePool> holder, Holder<StructureTemplatePool> holder2, Optional<ResourceLocation> optional, int i, HeightProvider heightProvider, boolean bl, Optional<Heightmap.Types> optional2, int j) {
        super(structureSettings);
        this.startPool = holder;
        this.startRoomPool = holder2;
        this.startJigsawName = optional;
        this.maxDepth = i;
        this.startHeight = heightProvider;
        this.useExpansionHack = bl;
        this.projectStartToHeightmap = optional2;
        this.maxDistanceFromCenter = j;
    }

    public Optional<Structure.GenerationStub> findGenerationPoint(GenerationContext generationContext) {
        ChunkPos chunkPos = generationContext.chunkPos();
        int i = this.startHeight.sample(generationContext.random(), new WorldGenerationContext(generationContext.chunkGenerator(), generationContext.heightAccessor()));
        BlockPos blockPos = new BlockPos(chunkPos.getMinBlockX(), i, chunkPos.getMinBlockZ());
        Pools.forceBootstrap();
        return DungeonGenerator.generate(generationContext, blockPos, maxDepth, startPool, startRoomPool);
        //return JigsawPlacement.addPieces(generationContext, this.startPool, this.startJigsawName, this.maxDepth, blockPos, this.useExpansionHack, this.projectStartToHeightmap, this.maxDistanceFromCenter);
    }

    public StructureType<?> type() {
        return ModFeatures.TERRARIA_JIGSAW_STRUCTURE;
    }
}
