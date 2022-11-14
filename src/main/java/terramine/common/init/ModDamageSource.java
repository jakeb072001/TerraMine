package terramine.common.init;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import terramine.common.utility.damagesources.DamageSourceItem;

public class ModDamageSource extends DamageSource {
    public static final DamageSource FALLING_STAR = (new ModDamageSource("falling_star"));

    public static DamageSource indirectMagicProjectile(@NotNull Entity entity, @Nullable Entity entity2, Item item) {
        return (new DamageSourceItem("indirect_magic_projectile", entity, entity2, item));
    }

    public static DamageSource indirectMagicProjectile(@NotNull Entity entity, Item item) {
        return indirectMagicProjectile(entity, null, item);
    }

    protected ModDamageSource(String string) {
        super(string);
    }
}
