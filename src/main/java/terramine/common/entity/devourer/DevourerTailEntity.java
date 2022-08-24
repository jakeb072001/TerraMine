package terramine.common.entity.devourer;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class DevourerTailEntity extends DevourerBodyEntity {
    public DevourerTailEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createMobAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 25)
                .add(Attributes.ARMOR, 10)
                .add(Attributes.FOLLOW_RANGE, 24)
                .add(Attributes.MOVEMENT_SPEED, 1)
                .add(Attributes.ATTACK_DAMAGE, 1);
    }
}
