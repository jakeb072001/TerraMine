package terramine.common.item.equipment;

import com.google.common.collect.BiMap;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import terramine.common.init.ModTags;
import terramine.extensions.ItemExtensions;

import java.util.Optional;

public class TerrariaShaxeItem extends DiggerItem implements ItemExtensions {
    private final boolean fireAspect;

    public TerrariaShaxeItem(Tier tier, float f, float g, Item.Properties properties) {
        super(f, g, tier, ModTags.MINEABLE_WITH_SHAXE, properties);
        fireAspect = false;
    }

    public TerrariaShaxeItem(Tier tier, float f, float g, boolean fireAspect, Item.Properties properties) {
        super(f, g, tier, ModTags.MINEABLE_WITH_SHAXE, properties);
        this.fireAspect = fireAspect;
    }

    public InteractionResult useOn(UseOnContext useOnContext) {
        Level level = useOnContext.getLevel();
        BlockPos blockPos = useOnContext.getClickedPos();
        BlockState blockState = level.getBlockState(blockPos);
        Player player = useOnContext.getPlayer();

        // Shovel
        BlockState blockState2 = ShovelItem.FLATTENABLES.get(blockState.getBlock());
        BlockState blockState3 = null;
        if (blockState2 != null && level.getBlockState(blockPos.above()).isAir()) {
            level.playSound(player, blockPos, SoundEvents.SHOVEL_FLATTEN, SoundSource.BLOCKS, 1.0F, 1.0F);
            blockState3 = blockState2;
        } else if (blockState.getBlock() instanceof CampfireBlock && blockState.getValue(CampfireBlock.LIT)) {
            if (!level.isClientSide()) {
                level.levelEvent(null, 1009, blockPos, 0);
            }

            CampfireBlock.dowse(useOnContext.getPlayer(), level, blockPos, blockState);
            blockState3 = blockState.setValue(CampfireBlock.LIT, false);
        }

        if (blockState3 != null) {
            if (!level.isClientSide) {
                level.setBlock(blockPos, blockState3, 11);
                if (player != null) {
                    useOnContext.getItemInHand().hurtAndBreak(1, player, (playerx) -> {
                        playerx.broadcastBreakEvent(useOnContext.getHand());
                    });
                }
            }

            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        // Axe
        Optional<BlockState> optional = this.getStripped(blockState);
        Optional<BlockState> optional2 = WeatheringCopper.getPrevious(blockState);
        Optional<BlockState> optional3 = Optional.ofNullable((Block)((BiMap<?, ?>) HoneycombItem.WAX_OFF_BY_BLOCK.get()).get(blockState.getBlock())).map((block) -> block.withPropertiesOf(blockState));
        ItemStack itemStack = useOnContext.getItemInHand();
        Optional<BlockState> optional4 = Optional.empty();
        if (optional.isPresent()) {
            level.playSound(player, blockPos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
            optional4 = optional;
        } else if (optional2.isPresent()) {
            level.playSound(player, blockPos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.levelEvent(player, 3005, blockPos, 0);
            optional4 = optional2;
        } else if (optional3.isPresent()) {
            level.playSound(player, blockPos, SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.levelEvent(player, 3004, blockPos, 0);
            optional4 = optional3;
        }

        if (optional4.isPresent()) {
            if (player instanceof ServerPlayer) {
                CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer)player, blockPos, itemStack);
            }

            level.setBlock(blockPos, optional4.get(), 11);
            if (player != null) {
                itemStack.hurtAndBreak(1, player, (playerx) -> {
                    playerx.broadcastBreakEvent(useOnContext.getHand());
                });
            }

            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            return InteractionResult.PASS;
        }
    }

    private Optional<BlockState> getStripped(BlockState blockState) {
        return Optional.ofNullable(AxeItem.STRIPPABLES.get(blockState.getBlock())).map((block) -> block.defaultBlockState().setValue(RotatedPillarBlock.AXIS, blockState.getValue(RotatedPillarBlock.AXIS)));
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack itemStack, @NotNull LivingEntity livingEntity, @NotNull LivingEntity livingEntity2) {
        if (fireAspect) {
            if (livingEntity.getRandom().nextFloat() <= 0.20) {
                livingEntity.setSecondsOnFire(6);
            }
        }
        return super.hurtEnemy(itemStack, livingEntity, livingEntity2);
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        return false;
    }

    @Override
    public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {
        return true;
    }
}
