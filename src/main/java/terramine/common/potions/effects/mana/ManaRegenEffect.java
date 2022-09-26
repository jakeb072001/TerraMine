package terramine.common.potions.effects.mana;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import terramine.common.init.ModComponents;
import terramine.common.potions.effects.TerrariaEffect;

public class ManaRegenEffect extends TerrariaEffect {

    public ManaRegenEffect(MobEffectCategory type, int color, boolean isInstant) {
        super(type, color, isInstant);
    }

    @Override
    protected boolean canApplyEffect(int remainingTicks, int level) {
        return remainingTicks % Math.max(5, 30/(level+1)) == 0;
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity livingEntity, int level) {
        if (livingEntity instanceof Player player) {
            ModComponents.MANA_HANDLER.get(player).setManaBonus(25, true);
        }
    }
}
