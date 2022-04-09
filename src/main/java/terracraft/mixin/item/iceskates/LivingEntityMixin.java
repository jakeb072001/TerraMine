package terracraft.mixin.item.iceskates;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import terracraft.common.init.ModBlocks;
import terracraft.common.init.ModItems;
import terracraft.common.trinkets.TrinketsHelper;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> type, Level world) {
        super(type, world);
    }

    @ModifyVariable(method = "travel", at = @At("STORE"), ordinal = 0)
    private float noIceSlip(float t, Vec3 vec3) {
        if (TrinketsHelper.isEquipped(ModItems.ICE_SKATES, (LivingEntity) (Object) this)) {
            BlockPos blockPos = this.getBlockPosBelowThatAffectsMyMovement();
            Block block = this.level.getBlockState(blockPos).getBlock();
            if (block.equals(Blocks.ICE) || block.equals(Blocks.BLUE_ICE) || block.equals(Blocks.FROSTED_ICE) || block.equals(Blocks.PACKED_ICE) ||
                    block.equals(ModBlocks.CORRUPTED_ICE) || block.equals(ModBlocks.CORRUPTED_BLUE_ICE) || block.equals(ModBlocks.CORRUPTED_PACKED_ICE) || block.equals(ModBlocks.FROZEN_CHEST)) {
                t = 0.6F;
            }
        }
        return t;
    }
}
