package terramine.common.potions.effects.misc;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.jetbrains.annotations.NotNull;
import terramine.common.item.accessories.AccessoryTerrariaItem;
import terramine.common.potions.effects.TerrariaEffect;

import java.util.UUID;

public class IronSkinEffect extends TerrariaEffect {

    public static final AttributeModifier IRONSKIN_ARMOR = new AttributeModifier(UUID.fromString("3419d896-3f29-4bdc-9837-e8244712b17d"),
            "ironskin_armor", 8, AttributeModifier.Operation.ADDITION);

    public IronSkinEffect(MobEffectCategory type, int color, boolean isInstant) {
        super(type, color, isInstant);
    }

    @Override
    protected boolean canApplyEffect(int remainingTicks, int level) {
        return remainingTicks % Math.max(5, 30/(level+1)) == 0;
    }

    @Override
    public void removeAttributeModifiers(LivingEntity livingEntity, @NotNull AttributeMap attributeMap, int i) {
        if (livingEntity.getAttributes().hasAttribute(Attributes.ARMOR)) {
            AccessoryTerrariaItem.removeModifier(livingEntity.getAttribute(Attributes.ARMOR), IRONSKIN_ARMOR);
        }
        super.removeAttributeModifiers(livingEntity, attributeMap, i);
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity livingEntity, int level) {
        if (livingEntity.getAttributes().hasAttribute(Attributes.ARMOR)) {
            AccessoryTerrariaItem.addModifier(livingEntity.getAttribute(Attributes.ARMOR), IRONSKIN_ARMOR);
        }
    }
}
