package terramine.mixin.world;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.CraftingTableBlock;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terramine.TerraMine;
import terramine.common.entity.projectiles.FallingMeteoriteEntity;
import terramine.common.entity.projectiles.FallingStarEntity;
import terramine.common.init.ModEntities;

// todo: retry spawning a meteorite if it should have spawned but was just blocked
// todo: make only spawn after evil biome boss is defeated or at least one evil biome orb has been destroyed
// todo: make have a 50% chance to spawn on the night of defeating an evil biome boss
// https://terraria.fandom.com/wiki/Meteorite_(biome)#Conditions
@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin {
    @Shadow
    @Nullable
    public abstract ServerPlayer getRandomPlayer();
    @Unique
    private final RandomSource rand = RandomSource.create();
    @Unique
    private int ticks = 0;
    @Unique
    private int tickGoal = -1;
    @Unique
    private long lastTick = 0L;

    // might not be the best way of doing this, it shouldn't be too bad for performance though since it should rarely ever check if the meteorite can spawn
    @Inject(at = @At("HEAD"), method = "tickChunk")
    private void spawnMeteorite(LevelChunk levelChunk, int i, CallbackInfo ci) {
        Level level = levelChunk.getLevel();
        if (level.dimensionType().bedWorks()) {
            if (!TerraMine.CONFIG.general.disableFallingStars) {
                if (!level.isDay()) { // handles spawning stars randomly during the night, not the best way to do it most likely, but it works for now.
                    long wTime = level.getGameTime();
                    if (wTime % 20L == 0L && wTime != lastTick) {
                        lastTick = wTime;
                        if (this.tickGoal == -1) {
                            this.tickGoal = getNewTickGoal();
                        }
                        this.ticks++;
                        if (this.ticks >= this.tickGoal) {
                            if (rand.nextInt(100) <= TerraMine.CONFIG.general.fallingStarRarity) {
                                Player player = this.getRandomPlayer();
                                FallingStarEntity star = ModEntities.FALLING_STAR.create(level);
                                if (star != null && player != null && player.blockPosition().getY() >= 50) {
                                    star.setPos(player.blockPosition().getX() + rand.nextInt(12), player.blockPosition().getY() + 30, player.blockPosition().getZ() + rand.nextInt(12));
                                    level.addFreshEntity(star);
                                }
                            }

                            this.ticks = 0;
                            this.tickGoal = getNewTickGoal();
                        }
                    }
                }
            }

            if (level.getDayTime() == 18000L) {
                if (rand.nextInt(100) < TerraMine.CONFIG.general.meteoriteRarity && level.players().size() > 0 && !TerraMine.CONFIG.general.disableMeteorites) {
                    int x = level.random.nextInt(100 / 4);
                    int z = level.random.nextInt(100 / 4);
                    if (level.random.nextBoolean()) x = -x;
                    if (level.random.nextBoolean()) z = -z;
                    Player player = this.getRandomPlayer();
                    if (player != null) {
                        x = (int) (x + player.getX());
                        z = (int) (z + player.getZ());
                        FallingMeteoriteEntity meteorite = ModEntities.METEORITE.create(level);
                        if (meteorite != null) {
                            meteorite.setPos(x, 150, z);

                            boolean blocked = false;
                            for (int j = -100; j < 100; j++) {
                                for (int k = -100; k < 100; k++) {
                                    for (int l = 55; l < 150; l++) {
                                        BlockPos blockPos = new BlockPos(x + j, l, z + k);
                                        if (level.getBlockState(blockPos).getBlock() instanceof CraftingTableBlock || level.getBlockState(blockPos).getBlock() instanceof ChestBlock) {
                                            blocked = true;
                                            break;
                                        }
                                    }
                                    if (blocked) {
                                        break;
                                    }
                                }
                                if (blocked) {
                                    break;
                                }
                            }

                            if (!blocked) {
                                player.sendSystemMessage(Component.translatable("event." + TerraMine.MOD_ID + ".meteoriteSpawn"));
                                level.addFreshEntity(meteorite);
                            }
                        }
                    }
                }
            }
        }
    }

    private int getNewTickGoal() {
        return rand.nextInt(5) + 2;
    }
}
