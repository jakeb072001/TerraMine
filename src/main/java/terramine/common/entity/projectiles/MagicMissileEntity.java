package terramine.common.entity.projectiles;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import terramine.common.init.ModParticles;
import terramine.common.utility.MagicMissileHelper;

public class MagicMissileEntity extends MagicMissileHelper {
    private final RandomSource rand = RandomSource.create();

    public MagicMissileEntity(EntityType<? extends MagicMissileHelper> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void createParticles() {
        float random = (rand.nextFloat() - 0.5F) * 0.1F;
        this.level.addParticle(ParticleTypes.ENCHANTED_HIT, position().x(), position().y(), position().z(), random, -0.2D, random);
        this.level.addParticle(ModParticles.BLUE_POOF, position().x(), position().y(), position().z(), random, -0.2D, random);
    }
}
