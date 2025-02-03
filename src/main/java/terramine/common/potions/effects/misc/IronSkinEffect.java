package terramine.common.potions.effects.misc;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.jetbrains.annotations.NotNull;
import terramine.TerraMine;
import terramine.common.item.accessories.AccessoryTerrariaItem;
import terramine.common.potions.effects.TerrariaEffect;

public class IronSkinEffect extends TerrariaEffect {

    public static final AttributeModifier IRONSKIN_ARMOR = new AttributeModifier(TerraMine.id("ironskin_armor"), 8, AttributeModifier.Operation.ADD_VALUE);

    private LivingEntity livingEntity;

    public IronSkinEffect(MobEffectCategory type, int color, boolean isInstant) {
        super(type, color, isInstant);
    }

    @Override
    protected boolean canApplyEffect(int remainingTicks, int level) {
        return remainingTicks % Math.max(5, 30/(level+1)) == 0;
    }

    @Override
    public void removeAttributeModifiers(AttributeMap attributeMap) {
        if (livingEntity.getAttributes().hasAttribute(Attributes.ARMOR)) {
            AccessoryTerrariaItem.removeModifier(livingEntity.getAttribute(Attributes.ARMOR), IRONSKIN_ARMOR);
        }
        super.removeAttributeModifiers(attributeMap);
    }

    @Override
    public boolean applyEffectTick(ServerLevel serverLevel, @NotNull LivingEntity livingEntity, int level) {
        if (livingEntity.getAttributes().hasAttribute(Attributes.ARMOR)) {
            AccessoryTerrariaItem.addModifier(livingEntity.getAttribute(Attributes.ARMOR), IRONSKIN_ARMOR);
        }
        this.livingEntity = livingEntity;
        return true;
    }
}
