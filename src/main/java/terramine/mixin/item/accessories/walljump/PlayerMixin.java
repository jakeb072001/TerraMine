package terramine.mixin.item.accessories.walljump;

import com.mojang.authlib.GameProfile;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terramine.common.init.ModComponents;
import terramine.common.init.ModItems;
import terramine.common.init.ModSoundEvents;
import terramine.common.misc.AccessoriesHelper;
import terramine.common.network.ServerPacketHandler;
import terramine.common.network.types.FloatSoundNetworkType;
import terramine.common.network.types.IntBoolUUIDNetworkType;
import terramine.common.utility.InputHandler;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Mixin(LocalPlayer.class)
public abstract class PlayerMixin extends AbstractClientPlayer {

    @Unique
    public int ticksWallClinged;
    @Unique
    private int ticksKeyDown;
    @Unique
    private double clingX;
    @Unique
    private double clingZ;
    @Unique
    private double lastJumpY = Double.MAX_VALUE;
    @Unique
    private Set<Direction> walls = new HashSet<>();
    @Unique
    private Set<Direction> staleWalls = new HashSet<>();
    @Unique
    private final Minecraft mc = Minecraft.getInstance();

    public PlayerMixin(ClientLevel clientLevel, GameProfile gameProfile) {
        super(clientLevel, gameProfile);
    }


    @Inject(method = "tick", at = @At("TAIL"))
    private void wallJumpTickMovement(CallbackInfo ci) {
        if (mc.gameMode != null && !mc.gameMode.hasInfiniteItems()) {
            if (AccessoriesHelper.isEquipped(ModItems.SHOE_SPIKES, this) || AccessoriesHelper.isEquipped(ModItems.CLIMBING_CLAWS, this) || AccessoriesHelper.isEquipped(ModItems.TIGER_CLIMBING_GEAR, this)
                    || AccessoriesHelper.isEquipped(ModItems.MASTER_NINJA_GEAR, this)) {
                this.doWallJump();
            }
        }
    }

    @Unique
    private boolean canStayOnWall() {
        return (AccessoriesHelper.isEquipped(ModItems.SHOE_SPIKES, this) && AccessoriesHelper.isEquipped(ModItems.CLIMBING_CLAWS, this)) || AccessoriesHelper.isEquipped(ModItems.TIGER_CLIMBING_GEAR, this)
                || AccessoriesHelper.isEquipped(ModItems.MASTER_NINJA_GEAR, this);
    }


    @Unique
    private void doWallJump() {
        if(this.onGround() || this.isFallFlying() || !this.level().getFluidState(this.blockPosition()).isEmpty() || this.isImmobile()) {
            this.ticksWallClinged = 0;
            this.clingX = Double.NaN;
            this.clingZ = Double.NaN;
            this.lastJumpY = Double.MAX_VALUE;
            this.staleWalls.clear();

            ModComponents.MOVEMENT_ORDER.get(this).setWallJumped(false);
            ClientPlayNetworking.send(new IntBoolUUIDNetworkType(0, 0,false, UUID.randomUUID(), ServerPacketHandler.WALL_JUMP_PACKET_ID));

            return;
        }

        this.updateWalls();
        this.ticksKeyDown = InputHandler.isHoldingShift(this) ? this.ticksKeyDown + 1 : 0;
        boolean accessoryCheck = canStayOnWall();

        if(this.ticksWallClinged < 1) {
            if (!accessoryCheck) {
                if (this.ticksKeyDown > 0 && this.ticksKeyDown < 4 && !this.walls.isEmpty() && this.canWallCling()) {
                    this.ticksWallClinged = 1;
                    this.clingX = this.getX();
                    this.clingZ = this.getZ();

                    this.playHitSound(this.getWallPos());
                    this.spawnWallParticle(this.getWallPos());

                    ModComponents.MOVEMENT_ORDER.get(this).setWallJumped(true);
                    ClientPlayNetworking.send(new IntBoolUUIDNetworkType(0, 0, true, UUID.randomUUID(), ServerPacketHandler.WALL_JUMP_PACKET_ID));
                }
            } else if (this.ticksKeyDown > 0 && this.ticksKeyDown < 4 && !this.walls.isEmpty()) {
                this.ticksWallClinged = 1;
                this.clingX = this.getX();
                this.clingZ = this.getZ();

                this.playHitSound(this.getWallPos());
                this.spawnWallParticle(this.getWallPos());

                ModComponents.MOVEMENT_ORDER.get(this).setWallJumped(true);
                ClientPlayNetworking.send(new IntBoolUUIDNetworkType(0, 0, true, UUID.randomUUID(), ServerPacketHandler.WALL_JUMP_PACKET_ID));
            }

            return;
        }

        if(!InputHandler.isHoldingShift(this) || this.onGround() || !this.level().getFluidState(this.blockPosition()).isEmpty() || this.walls.isEmpty()) {
            this.ticksWallClinged = 0;

            if((this.getSpeed() != 0) && !this.onGround() && !this.walls.isEmpty()) {
                this.fallDistance = 0.0F;
                this.wallJump(0.55F);
                this.staleWalls = new HashSet<>(this.walls);
            }

            ModComponents.MOVEMENT_ORDER.get(this).setWallJumped(false);
            ClientPlayNetworking.send(new IntBoolUUIDNetworkType(0, 0,false, UUID.randomUUID(), ServerPacketHandler.WALL_JUMP_PACKET_ID));

            return;
        }

        this.setPos(this.clingX, this.getY(), this.clingZ);
        double motionY = this.getDeltaMovement().y();
        if (!accessoryCheck) {
            if (motionY > 0.0) {
                motionY = 0.0;
            } else if (motionY < -0.6) {
                motionY = motionY + 0.2;
                this.spawnWallParticle(this.getWallPos());
            } else if (this.ticksWallClinged++ > 15) {
                motionY = -0.1;
                this.spawnWallParticle(this.getWallPos());
            } else {
                motionY = 0.0;
            }
        } else if (InputHandler.isHoldingBackwards(this)) {
            if (motionY > 0.0) {
                motionY = 0.0;
            } else if (motionY < -0.6) {
                motionY = motionY + 0.2;
                this.spawnWallParticle(this.getWallPos());
            } else {
                motionY = -0.2;
            }
        } else {
            if (motionY > 0.0) {
                motionY = 0.0;
            } else if (motionY < -0.6) {
                motionY = motionY + 0.2;
                this.spawnWallParticle(this.getWallPos());
            } else {
                motionY = 0.0;
            }
        }

        if(this.fallDistance > 2) {
            this.fallDistance = 0;

            ClientPlayNetworking.send(new FloatSoundNetworkType((float) (motionY * motionY * 8), 0, ModSoundEvents.FART, ServerPacketHandler.FALL_DISTANCE_PACKET_ID));
        }

        this.setDeltaMovement(0.0, motionY, 0.0);
    }


    @Unique
    private boolean canWallCling() {
        if(this.onClimbable() || this.getForward().y() > 0.1)
            return false;
        if(!this.level().noCollision(this.getBoundingBox().inflate(0, -0.8, 0)))
            return false;
        if(this.getY() < this.lastJumpY - 1)
            return true;

        return !this.staleWalls.containsAll(this.walls);
    }


    @Unique
    private void updateWalls() {
        AABB box = new AABB(
                this.getX() - 0.001,
                this.getY(),
                this.getZ() - 0.001,
                this.getX() + 0.001,
                this.getY() + this.getEyePosition().y(),
                this.getZ() + 0.001
        );

        double dist = (this.getBbWidth() / 2) + (this.ticksWallClinged > 0 ? 0.1 : 0.06);

        AABB[] axes = {
                box.inflate(0, 0, dist),
                box.inflate(-dist, 0, 0),
                box.inflate(0, 0, -dist),
                box.inflate(dist, 0, 0)
        };

        int i = 0;
        Direction direction;
        this.walls = new HashSet<>();

        for (AABB axis : axes) {
            direction = Direction.fromYRot(i++);
            if(!this.level().noCollision(axis)) {
                this.walls.add(direction);
                this.horizontalCollision = false;
            }
        }
    }


    @Unique
    private Direction getClingDirection() {

        return this.walls.isEmpty() ? Direction.UP : this.walls.iterator().next();
    }


    @Unique
    private BlockPos getWallPos() {

        BlockPos clingPos = this.blockPosition().relative(this.getClingDirection());
        return this.level().getBlockState(clingPos).isSolid() ? clingPos : clingPos.relative(Direction.UP);
    }


    @Unique
    private void wallJump(float up) {
        float strafe = Math.signum(this.getSpeed()) * up * up;
        float forward = Math.signum(this.getSpeed()) * up * up;

        float f = (float) (1.0F / Math.sqrt(strafe * strafe + up * up + forward * forward));
        strafe = strafe * f;
        forward = forward * f;

        float f1 = (float) (Math.sin(this.getYHeadRot() * 0.017453292F) * 0.01F);
        float f2 = (float) (Math.cos(this.getYHeadRot() * 0.017453292F) * 0.01F);

        int jumpBoostLevel = 0;
        MobEffectInstance jumpBoostEffect = this.getEffect(MobEffects.JUMP);
        if(jumpBoostEffect != null) jumpBoostLevel = jumpBoostEffect.getAmplifier() + 1;

        Vec3 motion = this.getDeltaMovement();
        this.setDeltaMovement(
                motion.x() + (strafe * f2 - forward * f1),
                up + (jumpBoostLevel * 0.125),
                motion.z() + (forward * f2 + strafe * f1)
        );

        this.lastJumpY = this.getY();
        this.playBreakSound(this.getWallPos());
    }


    @Unique
    private void playHitSound(BlockPos blockPos) {
        BlockState blockState = this.level().getBlockState(blockPos);
        SoundType soundType = blockState.getBlock().defaultBlockState().getSoundType();
        this.playSound(soundType.getHitSound(), soundType.getVolume() * 0.25F, soundType.getPitch());
    }

    @Unique
    private void playBreakSound(BlockPos blockPos) {
        BlockState blockState = this.level().getBlockState(blockPos);
        SoundType soundType = blockState.getBlock().defaultBlockState().getSoundType();
        this.playSound(soundType.getFallSound(), soundType.getVolume() * 0.5F, soundType.getPitch());
    }

    @Unique
    private void spawnWallParticle(BlockPos blockPos) {
        BlockState blockState = this.level().getBlockState(blockPos);
        if(blockState.getRenderShape() != RenderShape.INVISIBLE) {
            Vec3 pos = this.position();
            Vec3i motion = this.getClingDirection().getUnitVec3i();
            this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, blockState), pos.x(), pos.y(), pos.z(), motion.getX() * -1.0D, -1.0D, motion.getZ() * -1.0D);
        }
    }
}
