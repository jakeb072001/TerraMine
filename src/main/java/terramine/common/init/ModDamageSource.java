package terramine.common.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import terramine.common.utility.damagesources.DamageSourceItem;

import java.util.HashMap;
import java.util.Map;

import static terramine.TerraMine.id;

public class ModDamageSource {
    private static final Map<ResourceKey<DamageType>, DamageSource> damageSourceCache = new HashMap<>();
    public static final ResourceKey<DamageType> MAGIC_PROJECTILE_TYPE = ResourceKey.create(Registries.DAMAGE_TYPE, id("indirect_magic_projectile"));
    public static final ResourceKey<DamageType> LASER_PROJECTILE_TYPE = ResourceKey.create(Registries.DAMAGE_TYPE, id("indirect_laser_projectile"));
    public static final ResourceKey<DamageType> FALLING_STAR = ResourceKey.create(Registries.DAMAGE_TYPE, id("falling_star"));
    public static final ResourceKey<DamageType> METEORITE = ResourceKey.create(Registries.DAMAGE_TYPE, id("meteorite"));

    public static DamageSource indirectMagicProjectile(@NotNull Entity entity, @Nullable Entity entity2, Item item) {
        return (new DamageSourceItem(getSource(entity.damageSources(), MAGIC_PROJECTILE_TYPE).typeHolder(), entity, entity2, item));
    }

    public static DamageSource indirectMagicProjectile(@NotNull Entity entity, Item item) {
        return indirectMagicProjectile(entity, null, item);
    }

    public static DamageSource indirectLaserProjectile(@NotNull Entity entity, @Nullable Entity entity2, Item item) {
        return (new DamageSourceItem(getSource(entity.damageSources(), LASER_PROJECTILE_TYPE).typeHolder(), entity, entity2, item));
    }

    public static DamageSource indirectLaserProjectile(@NotNull Entity entity, Item item) {
        return indirectLaserProjectile(entity, null, item);
    }

    public static DamageSource getSource(DamageSources damageSources, ResourceKey<DamageType> damageType) {
        return damageSourceCache.computeIfAbsent(damageType, damageSources::source);
    }
}