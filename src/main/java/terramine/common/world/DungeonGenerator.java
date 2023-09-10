package terramine.common.world;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.JigsawBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.pools.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.lang3.mutable.MutableObject;
import terramine.TerraMine;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class DungeonGenerator {

    public static Optional<Structure.GenerationStub> generate(Structure.GenerationContext inContext, BlockPos pos, int size, Holder<StructureTemplatePool> startJigsawName, Holder<StructureTemplatePool> startRoomPool) {
        if (size <= 0 || TerraMine.CONFIG.worldgen.structures.disableDungeon)
            return Optional.empty();

        RegistryAccess registryManager = inContext.registryAccess();
        Registry<StructureTemplatePool> registry = registryManager.registryOrThrow(Registries.TEMPLATE_POOL);
        StructureTemplatePool structurePool = startJigsawName.value();
        StructureTemplatePool startingRoomPool = startRoomPool.value();

        WorldgenRandom chunkRandom = new WorldgenRandom(inContext.random());
        chunkRandom.setLargeFeatureSeed(inContext.seed(), inContext.chunkPos().x, inContext.chunkPos().z);

        StructurePoolElement startingElement = structurePool.getRandomTemplate(chunkRandom);
        StructurePoolElement startingRoomElement = startingRoomPool.getRandomTemplate(chunkRandom);
        if (startingElement == EmptyPoolElement.INSTANCE || startingRoomElement == EmptyPoolElement.INSTANCE)
            return Optional.empty();

        ChunkGenerator chunkGenerator = inContext.chunkGenerator();
        StructureTemplateManager structureManager = inContext.structureTemplateManager();
        LevelHeightAccessor heightLimitView = inContext.heightAccessor();
        Predicate<Holder<Biome>> biomePredicate = inContext.validBiome();

        Rotation blockRotation = Rotation.getRandom(chunkRandom);
        PoolElementStructurePiece poolStructurePiece = new PoolElementStructurePiece(structureManager, startingElement, pos, startingElement.getGroundLevelDelta(), blockRotation, startingElement.getBoundingBox(structureManager, pos, blockRotation));
        PoolElementStructurePiece poolRoomStructurePiece = new PoolElementStructurePiece(structureManager, startingRoomElement, pos, startingRoomElement.getGroundLevelDelta(), blockRotation, startingRoomElement.getBoundingBox(structureManager, pos, blockRotation));
        BoundingBox pieceBoundingBox = poolStructurePiece.getBoundingBox();
        BoundingBox pieceBoundingBoxRoom = poolRoomStructurePiece.getBoundingBox();

        int centerX = (pieceBoundingBox.maxX() + pieceBoundingBox.minX()) / 2;
        int centerZ = (pieceBoundingBox.maxZ() + pieceBoundingBox.minZ()) / 2;
        int y = pos.getY() + chunkGenerator.getBaseHeight(centerX, centerZ, Heightmap.Types.WORLD_SURFACE_WG, heightLimitView, inContext.randomState());
        int centerXRoom = (pieceBoundingBoxRoom.maxX() + pieceBoundingBoxRoom.minX()) / 2;
        int centerZRoom = (pieceBoundingBoxRoom.maxZ() + pieceBoundingBoxRoom.minZ()) / 2;
        int yRoom = y - 86;

        if (!biomePredicate.test(chunkGenerator.getBiomeSource().getNoiseBiome(QuartPos.fromBlock(centerX), QuartPos.fromBlock(y), QuartPos.fromBlock(centerZ), inContext.randomState().sampler())))
            return Optional.empty();

        int yOffset = pieceBoundingBox.minY() + poolStructurePiece.getGroundLevelDelta();
        poolStructurePiece.move(0, y - yOffset, 0);

        switch (poolStructurePiece.getRotation()) {
            case CLOCKWISE_90 -> poolRoomStructurePiece.move(-14, yRoom, 0);
            case CLOCKWISE_180 -> poolRoomStructurePiece.move(0, yRoom, -14);
            case COUNTERCLOCKWISE_90 -> poolRoomStructurePiece.move(14, yRoom, 0);
            default -> poolRoomStructurePiece.move(0, yRoom, 14);
        }

        return Optional.of(new Structure.GenerationStub(new BlockPos(centerX, y, centerZ), (collector) -> {
            ArrayList<PoolElementStructurePiece> list = Lists.newArrayList(poolStructurePiece);

            AABB box = new AABB(centerX - 80, y - 80, centerZ - 80, centerX + 80 + 1, y + 80 + 1, centerZ + 80 + 1);
            DungeonStructurePoolGenerator structurePoolGenerator = new DungeonStructurePoolGenerator(registry, size, chunkGenerator, structureManager, list, chunkRandom);
            structurePoolGenerator.structurePieces.addLast(new DungeonShapedPoolStructurePiece(poolStructurePiece, new MutableObject<>(Shapes.join(Shapes.create(box), Shapes.create(AABB.of(pieceBoundingBox)), BooleanOp.ONLY_FIRST)), 0, null));

            // Go through all structure pieces in the project.
            while (!structurePoolGenerator.structurePieces.isEmpty()) {
                DungeonShapedPoolStructurePiece shapedPoolStructurePiece = structurePoolGenerator.structurePieces.removeFirst();
                structurePoolGenerator.generatePiece(shapedPoolStructurePiece.piece, shapedPoolStructurePiece.pieceShape, shapedPoolStructurePiece.currentSize, shapedPoolStructurePiece.sourceBlockPos, heightLimitView, inContext.randomState());
            }

            // todo: remove this once figured out how to connect to last stair jigsaw
            AABB boxRoom = new AABB(centerXRoom - 80, yRoom - 80, centerZRoom - 80, centerXRoom + 80 + 1, yRoom + 80 + 1, centerZRoom + 80 + 1);
            list.add(poolRoomStructurePiece);
            DungeonStructurePoolGenerator structurePoolGeneratorRoom = new DungeonStructurePoolGenerator(registry, size, chunkGenerator, structureManager, list, chunkRandom);
            structurePoolGeneratorRoom.structurePieces.addLast(new DungeonShapedPoolStructurePiece(poolRoomStructurePiece, new MutableObject<>(Shapes.join(Shapes.create(boxRoom), Shapes.create(AABB.of(pieceBoundingBoxRoom)), BooleanOp.ONLY_FIRST)), 0, null));

            // Go through all structure pieces in the project.
            while (!structurePoolGeneratorRoom.structurePieces.isEmpty()) {
                DungeonShapedPoolStructurePiece shapedPoolStructurePiece = structurePoolGeneratorRoom.structurePieces.removeFirst();
                structurePoolGeneratorRoom.generatePiece(shapedPoolStructurePiece.piece, shapedPoolStructurePiece.pieceShape, shapedPoolStructurePiece.currentSize, shapedPoolStructurePiece.sourceBlockPos, heightLimitView, inContext.randomState());
            }

            list.forEach(collector::addPiece);
        }));
    }


    static final class DungeonStructurePoolGenerator {
        final Registry<StructureTemplatePool> registry;
        final int maxSize;
        final ChunkGenerator chunkGenerator;
        final StructureTemplateManager structureManager;
        final List<? super PoolElementStructurePiece> children;
        final WorldgenRandom random;
        final Deque<DungeonShapedPoolStructurePiece> structurePieces = Queues.newArrayDeque();

        DungeonStructurePoolGenerator(Registry<StructureTemplatePool> registry, int maxSize, ChunkGenerator chunkGenerator, StructureTemplateManager structureManager, List<? super PoolElementStructurePiece> children, WorldgenRandom random) {
            this.registry = registry;
            this.maxSize = maxSize;
            this.chunkGenerator = chunkGenerator;
            this.structureManager = structureManager;
            this.children = children;
            this.random = random;
        }

        void generatePiece(PoolElementStructurePiece piece, MutableObject<VoxelShape> pieceShape, int currentSize, BlockPos sourceStructureBlockPos, LevelHeightAccessor world, RandomState noiseConfig) {
            StructurePoolElement structurePoolElement = piece.getElement();
            BlockPos sourcePos = piece.getPosition();
            Rotation sourceRotation = piece.getRotation();
            MutableObject<VoxelShape> mutableObject = new MutableObject<>();
            BoundingBox sourceBoundingBox = piece.getBoundingBox();
            int boundsMinY = sourceBoundingBox.minY();

            BlockPos sourceBlock = sourcePos.offset(sourceStructureBlockPos == null ? BlockPos.ZERO : sourceStructureBlockPos);

            // For every structure block in the piece.
            for (StructureTemplate.StructureBlockInfo structureBlock : structurePoolElement.getShuffledJigsawBlocks(this.structureManager, sourcePos, sourceRotation, this.random)) {
                if(sourceBlock.equals(structureBlock.pos()))
                    continue;

                MutableObject<VoxelShape> structureShape;
                Direction structureBlockFaceDirection = JigsawBlock.getFrontFacing(structureBlock.state());
                BlockPos structureBlockPosition = structureBlock.pos();
                BlockPos structureBlockAimPosition = structureBlockPosition.relative(structureBlockFaceDirection);

                // Get pool that structure block is targeting.
                ResourceLocation structureBlockTargetPoolId = new ResourceLocation(structureBlock.nbt().getString("pool"));
                Optional<StructureTemplatePool> targetPool = this.registry.getOptional(structureBlockTargetPoolId);
                //if (targetPool.isEmpty() || targetPool.get().size() == 0 && !Objects.equals(structureBlockTargetPoolId, StructurePools.EMPTY.getValue())) {
                if (targetPool.isEmpty() || targetPool.get().size() == 0) {
                    //TerraMine.LOGGER.warn("Empty or non-existent pool: {}", structureBlockTargetPoolId);
                    continue;
                }

                //boolean ignoredPool = terrainCheckIgnoredPools.contains(structureBlockTargetPoolId);

                // Get end cap pool for target pool.
                StructureTemplatePool terminatorPool = targetPool.get().getFallback().value();
                //Optional<StructureTemplatePool> terminatorPool = this.registry.getOptional(terminatorPoolId);
                //if (terminatorPool.isEmpty() || terminatorPool.get().size() == 0 && !Objects.equals(terminatorPoolId, StructurePools.EMPTY.getValue())) {
                if (terminatorPool.size() == 0) {
                    //TerraMine.LOGGER.warn("Empty or non-existent fallback pool: {}", terminatorPoolId);
                    continue;
                }

                // Check if target position is inside current piece's bounding box.
                boolean containsPosition = sourceBoundingBox.isInside(structureBlockAimPosition);
                if (containsPosition) {
                    structureShape = mutableObject;
                    if (mutableObject.getValue() == null) {
                        mutableObject.setValue(Shapes.create(AABB.of(sourceBoundingBox)));
                    }
                } else {
                    structureShape = pieceShape;
                }

                // Get spawnable elements
                ArrayList<StructurePoolElement> possibleElementsToSpawn = Lists.newArrayList();
                if (currentSize < this.maxSize) {
                    possibleElementsToSpawn.addAll(targetPool.get().getShuffledTemplates(this.random)); // Add in pool elements if we haven't reached max size.
                }
                possibleElementsToSpawn.addAll(terminatorPool.getShuffledTemplates(this.random)); // Add in terminator elements.

                for (StructurePoolElement iteratedStructureElement : possibleElementsToSpawn) {
                    if (iteratedStructureElement == EmptyPoolElement.INSTANCE)
                        break;

                    boolean placed = tryPlacePiece(piece, currentSize, world, noiseConfig, boundsMinY, structureBlock, structureShape, structureBlockFaceDirection, structureBlockPosition, structureBlockAimPosition, iteratedStructureElement);
                    if(placed)
                        break;
                }
            }
        }

        // Returns true if we could place piece.
        boolean tryPlacePiece(PoolElementStructurePiece piece, int currentSize, LevelHeightAccessor world, RandomState noiseConfig, int boundsMinY, StructureTemplate.StructureBlockInfo structureBlock, MutableObject<VoxelShape> structureShape, Direction structureBlockFaceDirection, BlockPos structureBlockPosition, BlockPos structureBlockAimPosition, StructurePoolElement element) {
            int j = structureBlockPosition.getY() - boundsMinY;
            int t = boundsMinY + j;
            int pieceGroundLevelDelta = piece.getGroundLevelDelta();

            for (Rotation randomizedRotation : Rotation.getShuffled(this.random)) {
                // Get all structure blocks in structure.
                List<StructureTemplate.StructureBlockInfo> structureBlocksInStructure = element.getShuffledJigsawBlocks(this.structureManager, BlockPos.ZERO, randomizedRotation, this.random);

                // Loop through all blocks in piece we are trying to place.
                for (StructureTemplate.StructureBlockInfo structureBlockInfo : structureBlocksInStructure) {
                    // If the attachment ID doesn't match then skip this one.
                    if (!JigsawBlock.canAttach(structureBlock, structureBlockInfo))
                        continue;

                    BlockPos structureBlockPos = structureBlockInfo.pos();
                    BlockPos structureBlockAimDelta = structureBlockAimPosition.subtract(structureBlockPos);
                    BoundingBox iteratedStructureBoundingBox = element.getBoundingBox(this.structureManager, structureBlockAimDelta, randomizedRotation);

                    int structureBlockY = structureBlockPos.getY();
                    int o = j - structureBlockY + JigsawBlock.getFrontFacing(structureBlock.state()).getStepY();
                    int adjustedMinY = boundsMinY + o;
                    int pieceYOffset = adjustedMinY - iteratedStructureBoundingBox.minY();
                    BoundingBox offsetBoundingBox = iteratedStructureBoundingBox.move(0, pieceYOffset, 0);

                    // If bounding boxes overlap at all; skip.
                    if (Shapes.joinIsNotEmpty(structureShape.getValue(), Shapes.create(AABB.of(offsetBoundingBox).deflate(0.25)), BooleanOp.ONLY_SECOND))
                        continue;

                    StructureTemplatePool.Projection iteratedProjection = element.getProjection();
                    BlockPos offsetBlockPos = structureBlockAimDelta.offset(0, pieceYOffset, 0);

                    // All checks have passed,
                    structureShape.setValue(Shapes.join(structureShape.getValue(), Shapes.create(AABB.of(offsetBoundingBox)), BooleanOp.ONLY_FIRST));

                    int s = pieceGroundLevelDelta - o;
                    PoolElementStructurePiece poolStructurePiece = new PoolElementStructurePiece(this.structureManager, element, offsetBlockPos, s, randomizedRotation, offsetBoundingBox);

                    piece.addJunction(new JigsawJunction(structureBlockAimPosition.getX(), t - j + pieceGroundLevelDelta, structureBlockAimPosition.getZ(), o, iteratedProjection));
                    poolStructurePiece.addJunction(new JigsawJunction(structureBlockPosition.getX(), t - structureBlockY + s, structureBlockPosition.getZ(), -o, StructureTemplatePool.Projection.RIGID));
                    this.children.add(poolStructurePiece);

                    if (currentSize + 1 <= this.maxSize) // Whilst this is not the end.
                        this.structurePieces.addLast(new DungeonShapedPoolStructurePiece(poolStructurePiece, structureShape, currentSize + 1, structureBlockPos));

                    return true;
                }
            }

            return false;
        }
    }

    record DungeonShapedPoolStructurePiece(PoolElementStructurePiece piece, MutableObject<VoxelShape> pieceShape, int currentSize, BlockPos sourceBlockPos) {}
}