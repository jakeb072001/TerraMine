package terramine.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RedstoneTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import terramine.common.utility.CorruptionHelper;

public class CorruptedRedstoneOreBlock extends CorruptionHelper {
    public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;

    public CorruptedRedstoneOreBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(LIT, false));
    }

    @Override
    public void attack(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, @NotNull Player player) {
        interact(blockState, level, blockPos);
        super.attack(blockState, level, blockPos, player);
    }

    @Override
    public void stepOn(@NotNull Level level, @NotNull BlockPos blockPos, @NotNull BlockState blockState, @NotNull Entity entity) {
        interact(blockState, level, blockPos);
        super.stepOn(level, blockPos, blockState, entity);
    }

    @Override
    public InteractionResult use(@NotNull BlockState blockState, Level level, @NotNull BlockPos blockPos, @NotNull Player player, @NotNull InteractionHand interactionHand, @NotNull BlockHitResult blockHitResult) {
        if (level.isClientSide) {
            spawnParticles(level, blockPos);
        } else {
            interact(blockState, level, blockPos);
        }
        ItemStack itemStack = player.getItemInHand(interactionHand);
        if (itemStack.getItem() instanceof BlockItem && new BlockPlaceContext(player, interactionHand, itemStack, blockHitResult).canPlace()) {
            return InteractionResult.PASS;
        }
        return InteractionResult.SUCCESS;
    }

    private static void interact(BlockState blockState, Level level, BlockPos blockPos) {
        spawnParticles(level, blockPos);
        if (!blockState.getValue(LIT)) {
            level.setBlock(blockPos, blockState.setValue(LIT, true), 3);
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState blockState) {
        return blockState.getValue(LIT);
    }

    @Override
    public void randomTick(@NotNull BlockState blockState, @NotNull ServerLevel serverLevel, @NotNull BlockPos blockPos, @NotNull RandomSource random) {
        if (blockState.getValue(LIT)) {
            serverLevel.setBlock(blockPos, blockState.setValue(LIT, false), 3);
        }
    }

    @Override
    public void spawnAfterBreak(@NotNull BlockState blockState, @NotNull ServerLevel serverLevel, @NotNull BlockPos blockPos, @NotNull ItemStack itemStack, boolean bl) {
        super.spawnAfterBreak(blockState, serverLevel, blockPos, itemStack, bl);
        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, itemStack) == 0) {
            int i = 1 + serverLevel.random.nextInt(5);
            this.popExperience(serverLevel, blockPos, i);
        }
    }

    @Override
    public void animateTick(BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, @NotNull RandomSource random) {
        if (blockState.getValue(LIT)) {
            spawnParticles(level, blockPos);
        }
    }

    private static void spawnParticles(Level level, BlockPos blockPos) {
        RandomSource random = level.random;
        for (Direction direction : Direction.values()) {
            BlockPos blockPos2 = blockPos.relative(direction);
            if (level.getBlockState(blockPos2).isSolidRender(level, blockPos2)) continue;
            Direction.Axis axis = direction.getAxis();
            double e = axis == Direction.Axis.X ? 0.5 + 0.5625 * (double)direction.getStepX() : (double)random.nextFloat();
            double f = axis == Direction.Axis.Y ? 0.5 + 0.5625 * (double)direction.getStepY() : (double)random.nextFloat();
            double g = axis == Direction.Axis.Z ? 0.5 + 0.5625 * (double)direction.getStepZ() : (double)random.nextFloat();
            level.addParticle(DustParticleOptions.REDSTONE, (double)blockPos.getX() + e, (double)blockPos.getY() + f, (double)blockPos.getZ() + g, 0.0, 0.0, 0.0);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT);
        builder.add(SNOWY);
    }
}
