package terramine.mixin.world;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.OreVeinifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terramine.common.components.OreComponent;
import terramine.common.init.ModBlocks;
import terramine.common.init.ModComponents;

@Mixin(OreVeinifier.VeinType.class)
public class VeinTypeMixin {

    @Mutable
    @Final @Shadow
    BlockState ore;

    @Mutable
    @Final @Shadow
    BlockState rawOreBlock;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void modifyVeinType(String string, int i, BlockState blockState, BlockState blockState2, BlockState blockState3, int j, int k, CallbackInfo ci) {
        if (OreComponent.levelData != null) {
            if (blockState.is(Blocks.COPPER_ORE) && !ModComponents.ORE_TYPES.get(OreComponent.getLevelData()).getIfCopper()) {
                this.ore = ModBlocks.TIN_ORE.BLOCK.defaultBlockState();
                this.rawOreBlock = ModBlocks.RAW_TIN_BLOCK.BLOCK.defaultBlockState();
            } else if (blockState.is(Blocks.IRON_ORE) && !ModComponents.ORE_TYPES.get(OreComponent.getLevelData()).getIfIron()) {
                this.ore = ModBlocks.DEEPSLATE_LEAD_ORE.BLOCK.defaultBlockState();
                this.rawOreBlock = ModBlocks.RAW_LEAD_BLOCK.BLOCK.defaultBlockState();
            }
        }
    }
}
