package terramine.common.utility.damagesources;

import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DamageSourceItem extends DamageSource {
    @Nullable
    private final Entity owner;
    @Nullable
    private final Item itemUsed;

    public DamageSourceItem(Holder<DamageType> holder, Entity entity, @Nullable Entity entity2, @Nullable Item item) {
        super(holder, entity, entity2);
        this.owner = entity2;
        this.itemUsed = item;
    }

    public Component getLocalizedDeathMessage(@NotNull LivingEntity livingEntity) {
        Component component = this.owner == null ? this.getDirectEntity().getDisplayName() : this.owner.getDisplayName();
        String string = "death.attack." + this.getMsgId();
        String string2 = string + ".item";
        if (itemUsed != null) {
            return Component.translatable(string2, livingEntity.getDisplayName(), component, itemUsed.getDefaultInstance().getHoverName());
        }
        return Component.translatable(string, livingEntity.getDisplayName(), component);
    }
}
