package terramine.common.item.equipment.swords;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import terramine.extensions.ItemExtensions;

public class CustomSoundSwordItem extends SwordItem implements ItemExtensions {
    SoundEvent swingSound;

    public CustomSoundSwordItem(Tier tier, int i, float f, SoundEvent sound, Properties properties) {
        super(tier, i, f, properties);
        swingSound = sound;
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        float pitch = (float) (entity.getRandom().nextFloat() * (1.15 - 0.85) + 0.85);
        entity.level().playSound(null, entity.blockPosition(), swingSound, SoundSource.PLAYERS, 0.5f, pitch);
        return false;
    }

    @Override
    public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {
        return false;
    }
}
