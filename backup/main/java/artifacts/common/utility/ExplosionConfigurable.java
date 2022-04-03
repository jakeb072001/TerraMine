package terracraft.common.utility;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;
import net.minecraft.world.level.EntityBasedExplosionDamageCalculator;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ExplosionConfigurable extends Explosion {

    private static final ExplosionDamageCalculator EXPLOSION_DAMAGE_CALCULATOR = new ExplosionDamageCalculator();
    private static final int MAX_DROPS_PER_COMBINED_STACK = 16;
    private final boolean fire;
    private final Explosion.BlockInteraction blockInteraction;
    private final Random random;
    private final Level level;
    private final double x;
    private final double y;
    private final double z;
    @Nullable
    private final Entity source;
    private final float radius;
    private final DamageSource damageSource;
    private final ExplosionDamageCalculator damageCalculator;
    private final List<BlockPos> toBlow;
    private final Map<Player, Vec3> hitPlayers;

    public ExplosionConfigurable(Level level, @Nullable Entity entity, @Nullable DamageSource damageSource, @Nullable ExplosionDamageCalculator explosionDamageCalculator, double d, double e, double f, float g, float divDamage, boolean bl, Explosion.BlockInteraction blockInteraction) {
        super(level, entity, damageSource, explosionDamageCalculator, d, e, f, g, bl, blockInteraction);
        this.random = new Random();
        this.toBlow = Lists.newArrayList();
        this.hitPlayers = Maps.newHashMap();
        this.level = level;
        this.source = entity;
        this.radius = g;
        this.x = d;
        this.y = e;
        this.z = f;
        this.fire = bl;
        this.blockInteraction = blockInteraction;
        this.damageSource = damageSource == null ? DamageSource.explosion(this) : damageSource;
        this.damageCalculator = explosionDamageCalculator == null ? this.makeDamageCalculator(entity) : explosionDamageCalculator;
        explode(divDamage);
    }

    private ExplosionDamageCalculator makeDamageCalculator(@Nullable Entity entity) {
        return (ExplosionDamageCalculator)(entity == null ? EXPLOSION_DAMAGE_CALCULATOR : new EntityBasedExplosionDamageCalculator(entity));
    }

    public void explode(float divDamage) {
        this.level.gameEvent(this.source, GameEvent.EXPLODE, new BlockPos(this.x, this.y, this.z));
        Set<BlockPos> set = Sets.newHashSet();

        int k;
        int l;
        for(int j = 0; j < 16; ++j) {
            for(k = 0; k < 16; ++k) {
                for(l = 0; l < 16; ++l) {
                    if (j == 0 || j == 15 || k == 0 || k == 15 || l == 0 || l == 15) {
                        double d = (double)((float)j / 15.0F * 2.0F - 1.0F);
                        double e = (double)((float)k / 15.0F * 2.0F - 1.0F);
                        double f = (double)((float)l / 15.0F * 2.0F - 1.0F);
                        double g = Math.sqrt(d * d + e * e + f * f);
                        d /= g;
                        e /= g;
                        f /= g;
                        float h = this.radius * (0.7F + this.level.random.nextFloat() * 0.6F);
                        double m = this.x;
                        double n = this.y;
                        double o = this.z;

                        for(float var21 = 0.3F; h > 0.0F; h -= 0.22500001F) {
                            BlockPos blockPos = new BlockPos(m, n, o);
                            BlockState blockState = this.level.getBlockState(blockPos);
                            FluidState fluidState = this.level.getFluidState(blockPos);
                            if (!this.level.isInWorldBounds(blockPos)) {
                                break;
                            }

                            Optional<Float> optional = this.damageCalculator.getBlockExplosionResistance(this, this.level, blockPos, blockState, fluidState);
                            if (optional.isPresent()) {
                                h -= ((Float)optional.get() + 0.3F) * 0.3F;
                            }

                            if (h > 0.0F && this.damageCalculator.shouldBlockExplode(this, this.level, blockPos, blockState, h)) {
                                set.add(blockPos);
                            }

                            m += d * 0.30000001192092896D;
                            n += e * 0.30000001192092896D;
                            o += f * 0.30000001192092896D;
                        }
                    }
                }
            }
        }

        this.toBlow.addAll(set);
        float q = this.radius * 2.0F;
        k = Mth.floor(this.x - (double)q - 1.0D);
        l = Mth.floor(this.x + (double)q + 1.0D);
        int t = Mth.floor(this.y - (double)q - 1.0D);
        int u = Mth.floor(this.y + (double)q + 1.0D);
        int v = Mth.floor(this.z - (double)q - 1.0D);
        int w = Mth.floor(this.z + (double)q + 1.0D);
        List<Entity> list = this.level.getEntities(this.source, new AABB((double)k, (double)t, (double)v, (double)l, (double)u, (double)w));
        Vec3 vec3 = new Vec3(this.x, this.y, this.z);

        for(int x = 0; x < list.size(); ++x) {
            Entity entity = (Entity)list.get(x);
            if (!entity.ignoreExplosion()) {
                double y = Math.sqrt(entity.distanceToSqr(vec3)) / (double)q;
                if (y <= 1.0D) {
                    double z = entity.getX() - this.x;
                    double aa = (entity instanceof PrimedTnt ? entity.getY() : entity.getEyeY()) - this.y;
                    double ab = entity.getZ() - this.z;
                    double ac = Math.sqrt(z * z + aa * aa + ab * ab);
                    if (ac != 0.0D) {
                        z /= ac;
                        aa /= ac;
                        ab /= ac;
                        double ad = (double)getSeenPercent(vec3, entity);
                        double ae = (1.0D - y) * ad;
                        entity.hurt(this.getDamageSource(), ((float)((int)((ae * ae + ae) / 2.0D * 7.0D * (double)q + 1.0D))) / divDamage);
                        double af = ae;
                        if (entity instanceof LivingEntity) {
                            af = ProtectionEnchantment.getExplosionKnockbackAfterDampener((LivingEntity)entity, ae);
                        }

                        entity.setDeltaMovement(entity.getDeltaMovement().add(z * af, aa * af, ab * af));
                        if (entity instanceof Player) {
                            Player player = (Player)entity;
                            if (!player.isSpectator() && (!player.isCreative() || !player.getAbilities().flying)) {
                                this.hitPlayers.put(player, new Vec3(z * ae, aa * ae, ab * ae));
                            }
                        }
                    }
                }
            }
        }
    }
}
