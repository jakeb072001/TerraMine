package terramine.common.utility;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;
import net.minecraft.world.level.EntityBasedExplosionDamageCalculator;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import terramine.common.init.ModBlocks;
import terramine.common.init.ModDamageSource;
import terramine.common.init.ModTags;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

// todo: meteorite explosion (and maybe others) causes de-sync issue, blocks appear to be missing but if the player walks into an area of explosion they will get caught on blocks
public class ExplosionConfigurable extends Explosion {

    private static final ExplosionDamageCalculator EXPLOSION_DAMAGE_CALCULATOR = new ExplosionDamageCalculator();
    private final boolean fire, isMeteorite;
    private final Explosion.BlockInteraction blockInteraction;
    private final RandomSource random;
    private final Level level;
    private final double x, y, z;
    private final Entity source;
    private final float radius;
    private final DamageSource damageSource;
    private final ExplosionDamageCalculator damageCalculator;
    private final ObjectArrayList<Pair<ItemStack, BlockPos>> toBlow;
    private final Map<Player, Vec3> hitPlayers;

    public ExplosionConfigurable(Level level, Entity entity, boolean isMeteorite) {
        this(level, entity, ModDamageSource.getSource(level.damageSources(), ModDamageSource.METEORITE), null, entity.getX(), entity.getY(), entity.getZ(), 10, 1000, true, isMeteorite, BlockInteraction.DESTROY);
    }

    public ExplosionConfigurable(Level level, @Nullable Entity entity, double x, double y, double z, float radius, float damage, Explosion.BlockInteraction blockInteraction) {
        this(level, entity, null, null, x, y, z, radius, damage, false, false, blockInteraction);
    }

    public ExplosionConfigurable(Level level, @Nullable Entity entity, @Nullable DamageSource damageSource, double x, double y, double z, float radius, float damage, Explosion.BlockInteraction blockInteraction) {
        this(level, entity, damageSource, null, x, y, z, radius, damage, false, false, blockInteraction);
    }

    public ExplosionConfigurable(Level level, @Nullable Entity entity, @Nullable DamageSource damageSource, @Nullable ExplosionDamageCalculator explosionDamageCalculator, double x, double y, double z, float radius, float damage, boolean fire, boolean isMeteorite, Explosion.BlockInteraction blockInteraction) {
        super(level, entity, damageSource, explosionDamageCalculator, x, y, z, radius, fire, blockInteraction);
        this.random = RandomSource.create();
        this.toBlow = new ObjectArrayList<>();
        this.hitPlayers = Maps.newHashMap();
        this.level = level;
        this.source = entity;
        this.radius = radius;
        this.x = x;
        this.y = y;
        this.z = z;
        this.fire = fire;
        this.isMeteorite = isMeteorite;
        this.blockInteraction = blockInteraction;
        this.damageSource = damageSource == null ? level.damageSources().explosion(this) : damageSource;
        this.damageCalculator = explosionDamageCalculator == null ? this.makeDamageCalculator(entity) : explosionDamageCalculator;
        explode(damage);
        finalizeExplosion(false);
    }

    private ExplosionDamageCalculator makeDamageCalculator(@Nullable Entity entity) {
        return entity == null ? EXPLOSION_DAMAGE_CALCULATOR : new EntityBasedExplosionDamageCalculator(entity);
    }

    public void explode(float damage) {
        this.level.gameEvent(this.source, GameEvent.EXPLODE, new BlockPos((int) this.x, (int) this.y, (int) this.z));
        Set<Pair<ItemStack, BlockPos>> set = Sets.newHashSet();

        int k;
        int l;
        for(int j = 0; j < 16; ++j) {
            for(k = 0; k < 16; ++k) {
                for(l = 0; l < 16; ++l) {
                    if (j == 0 || j == 15 || k == 0 || k == 15 || l == 0 || l == 15) {
                        double d = (float)j / 15.0F * 2.0F - 1.0F;
                        double e = (float)k / 15.0F * 2.0F - 1.0F;
                        double f = (float)l / 15.0F * 2.0F - 1.0F;
                        double g = Math.sqrt(d * d + e * e + f * f);
                        d /= g;
                        e /= g;
                        f /= g;
                        float h = this.radius * (0.7F + random.nextFloat() * 0.6F);
                        double m = this.x;
                        double n = this.y;
                        double o = this.z;

                        for(; h > 0.0F; h -= 0.22500001F) {
                            BlockPos blockPos = new BlockPos((int) m, (int) n, (int) o);
                            BlockState blockState = this.level.getBlockState(blockPos);
                            FluidState fluidState = this.level.getFluidState(blockPos);
                            if (!this.level.isInWorldBounds(blockPos)) {
                                break;
                            }

                            Optional<Float> optional = this.damageCalculator.getBlockExplosionResistance(this, this.level, blockPos, blockState, fluidState);
                            if (optional.isPresent() && !(isMeteorite && !fluidState.isEmpty())) {
                                h -= (optional.get() + 0.3F) * 0.3F;
                            }

                            if (h > 0.0F && this.damageCalculator.shouldBlockExplode(this, this.level, blockPos, blockState, h) && fluidState.isEmpty()) {
                                set.add(Pair.of(null, blockPos));
                            }

                            m += d * 0.30000001192092896D;
                            n += e * 0.30000001192092896D;
                            o += f * 0.30000001192092896D;
                        }
                    }
                }
            }
        }

        toBlow.addAll(set);
        float q = this.radius * 2.0F;
        k = Mth.floor(this.x - (double)q - 1.0D);
        l = Mth.floor(this.x + (double)q + 1.0D);
        int t = Mth.floor(this.y - (double)q - 1.0D);
        int u = Mth.floor(this.y + (double)q + 1.0D);
        int v = Mth.floor(this.z - (double)q - 1.0D);
        int w = Mth.floor(this.z + (double)q + 1.0D);
        List<Entity> list = this.level.getEntities(this.source, new AABB(k, t, v, l, u, w));
        Vec3 vec3 = new Vec3(this.x, this.y, this.z);

        for (Entity entity : list) {
            if (!entity.ignoreExplosion()) {
                double y = Math.sqrt(entity.distanceToSqr(vec3)) / (double) q;
                if (y <= 1.0D) {
                    double z = entity.getX() - this.x;
                    double aa = (entity instanceof PrimedTnt ? entity.getY() : entity.getEyeY()) - this.y;
                    double ab = entity.getZ() - this.z;
                    double ac = Math.sqrt(z * z + aa * aa + ab * ab);
                    if (ac != 0.0D) {
                        z /= ac;
                        aa /= ac;
                        ab /= ac;
                        double ad = getSeenPercent(vec3, entity);
                        double ae = (1.0D - y) * ad;
                        entity.hurt(this.damageSource, ((float) ((int) ((ae * ae + ae) / 2.0D * 7.0D * (double) q + 1.0D))) * damage);
                        double af = ae;
                        if (entity instanceof LivingEntity) {
                            af = ProtectionEnchantment.getExplosionKnockbackAfterDampener((LivingEntity) entity, ae);
                        }

                        entity.setDeltaMovement(entity.getDeltaMovement().add(z * af, aa * af, ab * af));
                        if (entity instanceof Player player) {
                            if (!player.isSpectator() && (!player.isCreative() || !player.getAbilities().flying)) {
                                this.hitPlayers.put(player, new Vec3(z * ae, aa * ae, ab * ae));
                            }
                        }
                    }
                }
            }
        }
    }

    public void finalizeExplosion(boolean bl) {
        boolean bl2 = this.blockInteraction != BlockInteraction.KEEP;
        if (this.level.isClientSide) {
            this.level.playLocalSound(this.x, this.y, this.z, SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 4.0f, (1.0f + (random.nextFloat() - random.nextFloat()) * 0.2f) * 0.7f, false);
        }
        if (bl) {
            if (this.radius < 2.0f || !bl2) {
                this.level.addParticle(ParticleTypes.EXPLOSION, this.x, this.y, this.z, 1.0, 0.0, 0.0);
            } else {
                this.level.addParticle(ParticleTypes.EXPLOSION_EMITTER, this.x, this.y, this.z, 1.0, 0.0, 0.0);
            }
        }
        if (bl2) {
            ObjectArrayList<Pair<ItemStack, BlockPos>> objectArrayList = new ObjectArrayList<>();
            boolean bl3 = getIndirectSourceEntity() instanceof Player;
            Util.shuffle(toBlow, random);
            ObjectListIterator<Pair<ItemStack, BlockPos>> var5 = toBlow.iterator();

            while(var5.hasNext()) {
                BlockPos blockPos = var5.next().getSecond();
                BlockState blockState = level.getBlockState(blockPos);
                Block block = blockState.getBlock();
                if (!blockState.isAir()) {
                    BlockPos blockPos2 = blockPos.immutable();
                    this.level.getProfiler().push("explosion_blocks");
                    if (block.dropFromExplosion(this)) {
                        Level var11 = this.level;
                        if (var11 instanceof ServerLevel) {
                            ServerLevel serverLevel = (ServerLevel)var11;
                            BlockEntity blockEntity = blockState.hasBlockEntity() ? this.level.getBlockEntity(blockPos) : null;
                            LootParams.Builder builder = (new LootParams.Builder(serverLevel)).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(blockPos)).withParameter(LootContextParams.TOOL, ItemStack.EMPTY).withOptionalParameter(LootContextParams.BLOCK_ENTITY, blockEntity).withOptionalParameter(LootContextParams.THIS_ENTITY, this.source);
                            if (this.blockInteraction == Explosion.BlockInteraction.DESTROY_WITH_DECAY) {
                                builder.withParameter(LootContextParams.EXPLOSION_RADIUS, this.radius);
                            }

                            blockState.spawnAfterBreak(serverLevel, blockPos, ItemStack.EMPTY, bl3);
                            blockState.getDrops(builder).forEach((itemStack) -> {
                                addBlockDrops(objectArrayList, itemStack, blockPos2);
                            });
                        }
                    }

                    if (isMeteorite) {
                        BlockPos replaceBlock = blockPos.below(random.nextInt(2) + 3).offset(random.nextInt(3) - 1, random.nextInt(3) - 1, random.nextInt(3) - 1);
                        if (blockState.is(ModTags.METEORITE_REPLACE_BLOCKS)) {
                            this.level.setBlock(replaceBlock, ModBlocks.METEORITE_ORE.defaultBlockState(), 3);
                        }
                    }
                    this.level.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 3);
                    block.wasExploded(level, blockPos, this);
                    this.level.getProfiler().pop();
                }
            }

            var5 = objectArrayList.iterator();

            while(var5.hasNext()) {
                Pair<ItemStack, BlockPos> pair = var5.next();
                Block.popResource(level, pair.getSecond(), pair.getFirst());
            }
        }
        if (this.fire) {
            for (Pair<ItemStack, BlockPos> itemStackBlockPosPair : toBlow) {
                BlockPos blockPos3 = itemStackBlockPosPair.getSecond();
                if (this.random.nextInt(3) == 0 && this.level.getBlockState(blockPos3).isAir() && this.level.getFluidState(blockPos3).isEmpty() && this.level.getBlockState(blockPos3.below()).isSolidRender(this.level, blockPos3.below())) {
                    this.level.setBlockAndUpdate(blockPos3, BaseFireBlock.getState(this.level, blockPos3));
                }
            }
        }
    }

    private static void addBlockDrops(ObjectArrayList<Pair<ItemStack, BlockPos>> objectArrayList, ItemStack itemStack, BlockPos blockPos) {
        int i = objectArrayList.size();
        for (int j = 0; j < i; ++j) {
            Pair<ItemStack, BlockPos> pair = objectArrayList.get(j);
            ItemStack itemStack2 = pair.getFirst();
            if (!ItemEntity.areMergable(itemStack2, itemStack)) continue;
            ItemStack itemStack3 = ItemEntity.merge(itemStack2, itemStack, 16);
            objectArrayList.set(j, Pair.of(itemStack3, pair.getSecond()));
            if (!itemStack.isEmpty()) continue;
            return;
        }
        objectArrayList.add(Pair.of(itemStack, blockPos));
    }
}
