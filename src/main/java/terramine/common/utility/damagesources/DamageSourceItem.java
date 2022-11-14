package terramine.common.utility.damagesources;

import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DamageSourceItem extends EntityDamageSource {
    @Nullable
    private final Entity owner;
    @Nullable
    private final Item itemUsed;

    public DamageSourceItem(String string, Entity entity, @Nullable Entity entity2, @Nullable Item item) {
        super(string, entity);
        this.owner = entity2;
        this.itemUsed = item;
    }

    public DamageSourceItem(String string, Entity entity, @Nullable Item item) {
        super(string, entity);
        this.owner = null;
        this.itemUsed = item;
    }

    @Nullable
    public Entity getDirectEntity() {
        return this.entity;
    }

    @Nullable
    public Entity getEntity() {
        if (this.owner == null) {
            return this.entity;
        }
        return this.owner;
    }

    public Component getLocalizedDeathMessage(@NotNull LivingEntity livingEntity) {
        Component component = this.owner == null ? this.entity.getDisplayName() : this.owner.getDisplayName();
        String string = "death.attack." + this.msgId;
        String string2 = string + ".item";
        if (itemUsed != null) {
            return Component.translatable(string2, livingEntity.getDisplayName(), component, itemUsed.getDefaultInstance().getHoverName());
        }
        return Component.translatable(string, livingEntity.getDisplayName(), component);
    }
}
