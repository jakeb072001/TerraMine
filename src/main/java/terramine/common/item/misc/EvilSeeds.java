package terramine.common.item.misc;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import terramine.common.init.ModBlocks;

public class EvilSeeds extends Item {
    private final Block evilGrass;

    public EvilSeeds(Properties properties, Block evilGrass) {
        super(properties);
        this.evilGrass = evilGrass;
    }

    public InteractionResult useOn(UseOnContext useOnContext) {
        Level level = useOnContext.getLevel();
        BlockPos blockPos = useOnContext.getClickedPos();
        if (level.getBlockState(blockPos).getBlock() == Blocks.DIRT) {
            Player player = useOnContext.getPlayer();
            level.playSound(player, blockPos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (!level.isClientSide) {
                level.setBlockAndUpdate(blockPos, evilGrass.defaultBlockState());
            }

            useOnContext.getItemInHand().shrink(1);

            return InteractionResult.sidedSuccess(level.isClientSide);
        } else {
            return InteractionResult.PASS;
        }
    }
}
