package terramine.common.utility;

import dev.architectury.networking.NetworkManager;
import io.netty.buffer.Unpooled;
import net.minecraft.core.*;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.SpreadingSnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.*;
import net.minecraft.world.level.lighting.LayerLightEngine;
import org.jetbrains.annotations.NotNull;
import terramine.TerraMine;
import terramine.common.block.CorruptedSnowLayer;
import terramine.common.init.ModBiomes;
import terramine.common.init.ModBlocks;
import terramine.common.network.ServerPacketHandler;
import terramine.common.world.biome.CorruptionBiome;
import terramine.common.world.biome.CorruptionDesertBiome;
import terramine.common.world.biome.CrimsonBiome;
import terramine.common.world.biome.CrimsonDesertBiome;

import java.util.Objects;
import java.util.Random;

public class CorruptionHelper extends SpreadingSnowyDirtBlock  {
    protected CorruptionHelper(Properties properties) {
        super(properties);
    }

    public static boolean canNotBeGrass(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        BlockPos blockPos2 = blockPos.above();
        BlockState blockState2 = levelReader.getBlockState(blockPos2);
        if (blockState2.is(Blocks.SNOW) && blockState2.getValue(SnowLayerBlock.LAYERS) == 1) {
            return false;
        }
        if (blockState2.is(ModBlocks.CORRUPTED_SNOW_LAYER) && blockState2.getValue(CorruptedSnowLayer.LAYERS) == 1) {
            return false;
        }
        if (blockState2.getFluidState().getAmount() == 8) {
            return true;
        }
        int i = LayerLightEngine.getLightBlockInto(levelReader, blockState, blockPos, blockState2, blockPos2, Direction.UP, blockState2.getLightBlock(levelReader, blockPos2));
        return i >= levelReader.getMaxLightLevel();
    }

    private static boolean canNotPropagate(BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
        BlockPos blockPos2 = blockPos.above();
        return canNotBeGrass(blockState, levelReader, blockPos) || levelReader.getFluidState(blockPos2).is(FluidTags.WATER);
    }

    @Override
    public void randomTick(@NotNull BlockState blockState, @NotNull ServerLevel serverLevel, @NotNull BlockPos blockPos, @NotNull Random random) {
        if (!TerraMine.CONFIG.general.disableEvilSpread) { // allows user to disable spreading in configs
            BlockState grass = ModBlocks.CORRUPTED_GRASS.defaultBlockState();
            BlockState snow_layer = ModBlocks.CORRUPTED_SNOW_LAYER.defaultBlockState();

            for (int i = 0; i < 4; ++i) { // corrupted grass spread to grass and dirt
                if (random.nextInt(TerraMine.CONFIG.general.evilSpreadRarity + 1) == 1) {
                    BlockPos blockPos2 = blockPos.offset(random.nextInt(3) - 1, random.nextInt(3) - 1, random.nextInt(3) - 1);
                    if ((!serverLevel.getBlockState(blockPos2).is(Blocks.GRASS_BLOCK) && !serverLevel.getBlockState(blockPos2).is(Blocks.DIRT)) || canNotPropagate(grass, serverLevel, blockPos2)) continue;
                    serverLevel.setBlockAndUpdate(blockPos2, grass.setValue(SNOWY, (serverLevel.getBlockState(blockPos2.above()).is(Blocks.SNOW) || serverLevel.getBlockState(blockPos2.above()).is(ModBlocks.CORRUPTED_SNOW_LAYER))));
                    spreadBiome(serverLevel, blockPos2, false);
                }
            }
            for (int i = 0; i < 4; ++i) { // spread layered snow
                if (random.nextInt(TerraMine.CONFIG.general.evilSpreadRarity + 1) == 1) {
                    BlockPos blockPos2 = blockPos.offset(random.nextInt(3) - 1, random.nextInt(3) - 1, random.nextInt(3) - 1);
                    if (!serverLevel.getBlockState(blockPos2).is(Blocks.SNOW)) continue;
                    serverLevel.setBlockAndUpdate(blockPos2, snow_layer.setValue(CorruptedSnowLayer.LAYERS, serverLevel.getBlockState(blockPos2).getValue(SnowLayerBlock.LAYERS)));
                }
            }

            spreadBlock(ModBlocks.CORRUPTED_GRAVEL, Blocks.GRAVEL, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CORRUPTED_SAND, Blocks.SAND, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CORRUPTED_GLASS, Blocks.GLASS, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CORRUPTED_SANDSTONE, Blocks.SANDSTONE, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CORRUPTED_ANDESITE, Blocks.ANDESITE, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CORRUPTED_DIORITE, Blocks.DIORITE, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CORRUPTED_GRANITE, Blocks.GRANITE, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CORRUPTED_STONE, Blocks.STONE, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CORRUPTED_DEEPSLATE, Blocks.DEEPSLATE, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CORRUPTED_COBBLESTONE, Blocks.COBBLESTONE, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CORRUPTED_COBBLED_DEEPSLATE, Blocks.COBBLED_DEEPSLATE, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CORRUPTED_COAL_ORE, Blocks.COAL_ORE, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CORRUPTED_IRON_ORE, Blocks.IRON_ORE, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CORRUPTED_COPPER_ORE, Blocks.COPPER_ORE, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CORRUPTED_GOLD_ORE, Blocks.GOLD_ORE, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CORRUPTED_LAPIS_ORE, Blocks.LAPIS_ORE, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CORRUPTED_REDSTONE_ORE, Blocks.REDSTONE_ORE, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CORRUPTED_DIAMOND_ORE, Blocks.DIAMOND_ORE, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CORRUPTED_EMERALD_ORE, Blocks.EMERALD_ORE, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CORRUPTED_DEEPSLATE_COAL_ORE, Blocks.DEEPSLATE_COAL_ORE, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CORRUPTED_DEEPSLATE_IRON_ORE, Blocks.DEEPSLATE_IRON_ORE, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CORRUPTED_DEEPSLATE_COPPER_ORE, Blocks.DEEPSLATE_COPPER_ORE, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CORRUPTED_DEEPSLATE_GOLD_ORE, Blocks.DEEPSLATE_GOLD_ORE, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CORRUPTED_DEEPSLATE_LAPIS_ORE, Blocks.DEEPSLATE_LAPIS_ORE, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CORRUPTED_DEEPSLATE_REDSTONE_ORE, Blocks.DEEPSLATE_REDSTONE_ORE, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CORRUPTED_DEEPSLATE_DIAMOND_ORE, Blocks.DEEPSLATE_DIAMOND_ORE, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CORRUPTED_DEEPSLATE_EMERALD_ORE, Blocks.DEEPSLATE_EMERALD_ORE, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CORRUPTED_SNOW, Blocks.SNOW_BLOCK, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CORRUPTED_ICE, Blocks.ICE, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CORRUPTED_PACKED_ICE, Blocks.PACKED_ICE, serverLevel, blockPos, random);
            spreadBlock(ModBlocks.CORRUPTED_BLUE_ICE, Blocks.BLUE_ICE, serverLevel, blockPos, random);
        }
    }

    private void spreadBlock(Block toSpread, Block spreadTo, ServerLevel serverLevel, BlockPos blockPos, Random random) {
        for (int i = 0; i < 4; ++i) {
            if (random.nextInt(TerraMine.CONFIG.general.evilSpreadRarity + 1) == 1) {
                BlockState block = toSpread.defaultBlockState();
                BlockPos blockPos2 = blockPos.offset(random.nextInt(3) - 1, random.nextInt(3) - 1, random.nextInt(3) - 1);
                if (!serverLevel.getBlockState(blockPos2).is(spreadTo)) continue;
                serverLevel.setBlockAndUpdate(blockPos2, block.setValue(SNOWY, (serverLevel.getBlockState(blockPos2.above()).is(Blocks.SNOW) || serverLevel.getBlockState(blockPos2.above()).is(ModBlocks.CORRUPTED_SNOW_LAYER))));
                spreadBiome(serverLevel, blockPos2, false);
            }
        }
    }

    public static void spreadBiome(ServerLevel serverLevel, BlockPos blockPos, boolean isCrimson) {
        if (!serverLevel.getBiome(blockPos).is(ModBiomes.CORRUPTION) && !serverLevel.getBiome(blockPos).is(ModBiomes.CORRUPTION_DESERT)
                && !serverLevel.getBiome(blockPos).is(ModBiomes.CRIMSON) && !serverLevel.getBiome(blockPos).is(ModBiomes.CRIMSON_DESERT)) {
            if (!isCrimson) {
                if (serverLevel.getBiome(blockPos).is(Biomes.DESERT)) {
                    setBiome(serverLevel, blockPos, CorruptionDesertBiome.CORRUPTION_DESERT);
                } else {
                    setBiome(serverLevel, blockPos, CorruptionBiome.CORRUPTION);
                }
            } else {
                if (serverLevel.getBiome(blockPos).is(Biomes.DESERT)) {
                    setBiome(serverLevel, blockPos, CrimsonDesertBiome.CRIMSON_DESERT);
                } else {
                    setBiome(serverLevel, blockPos, CrimsonBiome.CRIMSON);
                }
            }
            updateChunkAfterBiomeChange(serverLevel, new ChunkPos(blockPos));
        }
    }

    // todo: almost works, getting unknown registry element error right now which stops chunk from saving but otherwise spreads perfectly
    // Copied from EvilCraft, may improve later if possible
    public static void setBiome(ServerLevel level, BlockPos posIn, Biome biome) {
        BiomeManager biomeManager = level.getBiomeManager();
        // Worldgen applies some funk "magnifier" position transformation to a "noise position",
        // which can change the pos into some other internal pos.
        // We copied the logic in BiomeManager#getBiome below:
        int i = posIn.getX() - 2;
        int j = posIn.getY() - 2;
        int k = posIn.getZ() - 2;
        int l = i >> 2;
        int i1 = j >> 2;
        int j1 = k >> 2;
        double d0 = (double)(i & 3) / 4.0D;
        double d1 = (double)(j & 3) / 4.0D;
        double d2 = (double)(k & 3) / 4.0D;
        int k1 = 0;
        double d3 = Double.POSITIVE_INFINITY;

        for(int l1 = 0; l1 < 8; ++l1) {
            boolean flag = (l1 & 4) == 0;
            boolean flag1 = (l1 & 2) == 0;
            boolean flag2 = (l1 & 1) == 0;
            int i2 = flag ? l : l + 1;
            int j2 = flag1 ? i1 : i1 + 1;
            int k2 = flag2 ? j1 : j1 + 1;
            double d4 = flag ? d0 : d0 - 1.0D;
            double d5 = flag1 ? d1 : d1 - 1.0D;
            double d6 = flag2 ? d2 : d2 - 1.0D;
            double d7 = BiomeManager.getFiddledDistance(biomeManager.biomeZoomSeed, i2, j2, k2, d4, d5, d6);
            if (d3 > d7) {
                k1 = l1;
                d3 = d7;
            }
        }

        int l2 = (k1 & 4) == 0 ? l : l + 1;
        int i3 = (k1 & 2) == 0 ? i1 : i1 + 1;
        int j3 = (k1 & 1) == 0 ? j1 : j1 + 1;

        // Update biome data in chunk
        ChunkAccess chunk = level.getChunk(QuartPos.toSection(l2), QuartPos.toSection(j3), ChunkStatus.BIOMES, false);
        if (chunk instanceof ImposterProtoChunk) {
            chunk = ((ImposterProtoChunk) chunk).getWrapped();
        }
        if(chunk != null) {
            // HACK
            // Due to some weird thing in MC, different instances of the same biome can exist.
            // This hack allows us to convert to the biome instance that is required for chunk serialization.
            // This avoids weird errors in the form of "Received invalid biome id: -1" (#818)
            Registry<Biome> biomeRegistry = level.registryAccess().registryOrThrow(Registry.BIOME_REGISTRY);
            Holder<Biome> biomeHack = biomeRegistry.getOrCreateHolder(ResourceKey.create(Registry.BIOME_REGISTRY, Objects.requireNonNull(BuiltinRegistries.BIOME.getKey(biome))));
            if (biomeHack == null)
                return;

            // Update biome in chunk
            // Based on ChunkAccess#getNoiseBiome
            int minBuildHeight = QuartPos.fromBlock(chunk.getMinBuildHeight());
            int maxHeight = minBuildHeight + QuartPos.fromBlock(chunk.getHeight()) - 1;
            int dummyY = Mth.clamp(i3, minBuildHeight, maxHeight);
            int sectionIndex = chunk.getSectionIndex(QuartPos.toBlock(dummyY));
            ((PalettedContainer<Holder<Biome>>) chunk.sections[sectionIndex].getBiomes()).set(l2 & 3, dummyY & 3, j3 & 3, biomeHack);

            chunk.setUnsaved(true);
        } else {
            TerraMine.LOGGER.warn("Tried changing biome at non-existing chunk for position " + posIn);
        }
    }

    // Copied from EvilCraft
    public static void updateChunkAfterBiomeChange(Level level, ChunkPos chunkPos) {
        LevelChunk chunkSafe = level.getChunkSource().getChunk(chunkPos.x, chunkPos.z, false);
        if (chunkSafe == null) {
            TerraMine.LOGGER.warn("Chunk is null, failed to update chunk after biome change");
            return;
        }
        ((ServerChunkCache) level.getChunkSource()).chunkMap.getPlayers(chunkPos, false).forEach((player) -> {
            player.connection.send(new ClientboundLevelChunkWithLightPacket(chunkSafe, ((ServerChunkCache) level.getChunkSource()).chunkMap.getLightEngine(), null, null, true));
            FriendlyByteBuf passedData = new FriendlyByteBuf(Unpooled.buffer());
            passedData.writeInt(chunkPos.x);
            passedData.writeInt(chunkPos.z);
            NetworkManager.sendToPlayer(player, ServerPacketHandler.UPDATE_BIOME_PACKET_ID, passedData);
        });
    }
}
