package terramine.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import terramine.common.init.ModComponents;

// todo: level is null so hardmode explosion resist doesn't work correctly
public class HotFloorBlock extends Block {
    private Level level;
    private final boolean hardmodeExplosionResist;

    public HotFloorBlock(Properties properties) {
        super(properties);
        this.hardmodeExplosionResist = true;
    }

    public HotFloorBlock(Properties properties, boolean hardmodeExplosionResist) {
        super(properties);
        this.hardmodeExplosionResist = hardmodeExplosionResist;
    }

    @Override
    public void stepOn(@NotNull Level level, @NotNull BlockPos blockPos, @NotNull BlockState blockState, Entity entity) {
        if (!entity.isSteppingCarefully() && entity instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity)entity)) {
            entity.hurt(level.damageSources().hotFloor(), 1.0F);
        }

        super.stepOn(level, blockPos, blockState, entity);
    }

    @Override
    public void tick(@NotNull BlockState blockState, @NotNull ServerLevel serverLevel, @NotNull BlockPos blockPos, @NotNull RandomSource random) {
        this.level = serverLevel;
    }

    @Override
    public float getExplosionResistance() {
        if (hardmodeExplosionResist) {
            if (level != null && ModComponents.HARDMODE.get(level.getLevelData()).get()) {
                return this.explosionResistance;
            }
            return 1200f;
        }
        return this.explosionResistance;
    }
}
