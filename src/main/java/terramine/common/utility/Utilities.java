package terramine.common.utility;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.*;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import terramine.common.item.dye.BasicDye;
import terramine.common.network.ServerPacketHandler;
import terramine.common.network.types.ItemNetworkType;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.function.Predicate;

public class Utilities { // todo: need to fix bug with magic missile where the projectile will jitter back and forth instead of just staying at its position
    public static BlockHitResult rayTraceBlocks(Entity entity, double length, boolean checkLiquids)
    {
        if (checkLiquids) {
            return entity.level().clip(new ClipContext(new Vec3(entity.position().x(), entity.position().y() + (double) entity.getEyeHeight(), entity.position().z()),
                    entity.getLookAngle().scale(length).add(entity.position().x(), entity.position().y() + (double) entity.getEyeHeight(), entity.position().z()), ClipContext.Block.COLLIDER, ClipContext.Fluid.ANY, entity));
        } else {
            return entity.level().clip(new ClipContext(new Vec3(entity.position().x(), entity.position().y() + (double) entity.getEyeHeight(), entity.position().z()),
                    entity.getLookAngle().scale(length).add(entity.position().x(), entity.position().y() + (double) entity.getEyeHeight(), entity.position().z()), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity));
        }
    }

    public static EntityHitResult rayTraceEntity(Entity entity, Entity self, double length)
    {
        Vec3 vec3 = new Vec3(entity.position().x(), entity.position().y() + (double) entity.getEyeHeight(), entity.position().z());
        Vec3 vec33 = entity.getLookAngle().scale(length).add(entity.position().x(), entity.position().y() + (double) entity.getEyeHeight(), entity.position().z());
        Predicate<Entity> predicate = entity1 -> !(entity1 instanceof Player || entity1 == self || entity1 instanceof ItemEntity || entity1 instanceof ExperienceOrb);
        return ProjectileUtil.getEntityHitResult(entity.level(), entity, vec3, vec33, (new AABB(vec3, vec33)).inflate(1.0D), predicate);
    }


    // todo: make work better, seems to not work well right now
    /**
     * Rotates the given entity towards its motion vector at the given speed.
     */
    public static void rotateTowardsMotion(Entity entity, float speed)
    {
        float horizontalMotion = (float) Math.sqrt(entity.getDeltaMovement().x() * entity.getDeltaMovement().x() + entity.getDeltaMovement().z() * entity.getDeltaMovement().z());
        entity.setXRot((float) (Math.atan2(entity.getDeltaMovement().x(), entity.getDeltaMovement().z()) * (180D / Math.PI)));
        for (entity.setYRot((float) (Math.atan2(entity.getDeltaMovement().y(), horizontalMotion) * (180D / Math.PI))); entity.getYRot() - entity.yRotO < -180.0F; entity.yRotO -= 360.0F) {
            while (entity.getYRot() - entity.yRotO >= 180F) entity.yRotO += 360F;
            while (entity.getXRot() - entity.xRotO < -180F) entity.xRotO -= 360F;
            while (entity.getXRot() - entity.xRotO >= 180F) entity.xRotO += 360F;
            entity.setYRot(entity.yRotO + (entity.getYRot() - entity.yRotO) * speed);
            entity.setXRot(entity.xRotO + (entity.getXRot() - entity.xRotO) * speed);
        }
    }

    /**
     * Returns a random unit vector inside a sphere cap defined by a given axis vector and angle.
     * @see <a href="https://math.stackexchange.com/questions/56784/generate-a-random-direction-within-a-cone">source</a>
     */
    public static Vec3 sampleSphereCap(Vec3 coneAxis, float angle)
    {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        // Choose an axis
        Vec3 axis;
        if(Math.abs(coneAxis.x) < Math.abs(coneAxis.y))
        {
            if(Math.abs(coneAxis.x) < Math.abs(coneAxis.z)) axis = new Vec3(1D, 0D, 0D);
            else axis = new Vec3(0D, 0D, 1D);
        }
        else
        {
            if(Math.abs(coneAxis.y) < Math.abs(coneAxis.z)) axis = new Vec3(0D, 1D, 0D);
            else axis = new Vec3(0D, 0D, 1D);
        }

        // Construct two mutually orthogonal vectors, both of which are orthogonal to the cone's axis vector
        Vec3 u = coneAxis.cross(axis);
        Vec3 v = coneAxis.cross(u);

        // Uniformly  sample an angle of rotation around the cone's axis vector
        float phi = random.nextFloat() * 2F * (float) Math.PI;

        // Uniformly sample an angle of rotation around one of the orthogonal vectors
        float theta = (float) Math.acos(random.nextDouble(Math.cos(angle), 1D));

        // Construct a unit vector uniformly distributed on the spherical cap
        return  coneAxis.scale(Math.cos(theta)).add(u.scale(Math.cos(phi) * Math.sin(theta))).add(v.scale(Math.sin(phi) * Math.sin(theta)));
    }

    // from Fancy Dyes, I could have easily got this code from elsewhere, but I am using their code to help with my dye system, so I'll give credit where I can
    // todo: probably don't need anymore
    public static Vector3f colorFromInt(int color) {
        float r = (float) (color >> 16 & 0xFF) / 255.0f;
        float g = (float) (color >> 8 & 0xFF) / 255.0f;
        float b = (float) (color & 0xFF) / 255.0f;
        return new Vector3f(r, g, b);
    }

    public static int intFromColor(BasicDye basicDye) {
        int m = basicDye.getColourInt();
        int i = ARGB.red(m);
        int j = ARGB.green(m);
        int k = ARGB.blue(m);
        return ARGB.color(i, j, k);
    }

    public static int getDyeColour(ItemStack itemStack) {
        if (itemStack.getItem() instanceof BasicDye dyeItem) {
            return intFromColor(dyeItem);
        }

        return -1;
    }

    private static int swingTimer = 0;

    @Environment(EnvType.CLIENT)
    public static void autoSwing() {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player != null && InputHandler.isHoldingAttack(player)) {
            if (player.getAttackStrengthScale(0) >= 1) {
                if (mc.hitResult != null && mc.hitResult.getType() == HitResult.Type.ENTITY && !(mc.hitResult instanceof BlockHitResult)) {
                    Entity entity = ((EntityHitResult) mc.hitResult).getEntity();
                    if (entity.isAlive() && entity.isAttackable()) {
                        swingTimer++;
                        if (swingTimer >= 2 && mc.gameMode != null) {
                            mc.gameMode.attack(player, entity);
                            player.swing(InteractionHand.MAIN_HAND);
                            swingTimer = 0;
                        }
                    }
                }
            }
        }
    }

    private static boolean upPressed, downPressed, leftPressed, rightPressed;
    private static boolean upKeyUnpressed, downKeyUnpressed, leftKeyUnpressed, rightKeyUnpressed;
    private static int dashTimer;

    // todo: make work better, a little bit jank right now (doesn't work sometimes after double pressing direction, doesn't move player very far while on ground)
    public static void playerDash(Player player, Item item) {
        if (dashTimer++ >= 6) {
            if (upPressed) {
                upPressed = false;
                upKeyUnpressed = false;
                dashTimer = 0;
            }
            if (downPressed) {
                downPressed = false;
                downKeyUnpressed = false;
                dashTimer = 0;
            }
            if (leftPressed) {
                leftPressed = false;
                leftKeyUnpressed = false;
                dashTimer = 0;
            }
            if (rightPressed) {
                rightPressed = false;
                rightKeyUnpressed = false;
                dashTimer = 0;
            }
        }
        //up
        if (InputHandler.isHoldingForwards(player)) {
            if (InputHandler.isHoldingForwards(player) && upPressed && upKeyUnpressed && !player.getCooldowns().isOnCooldown(item.getDefaultInstance()) && !player.isInWaterOrBubble()) {
                sendDash(item);
                player.moveRelative(1, new Vec3(0, 0, 10));
                upPressed = false;
                upKeyUnpressed = false;
            } else {
                upPressed = true;
            }
        } else if (upPressed) {
            upKeyUnpressed = true;
        }
        //down
        if (InputHandler.isHoldingBackwards(player)) {
            if (InputHandler.isHoldingBackwards(player) && downPressed && downKeyUnpressed && !player.getCooldowns().isOnCooldown(item.getDefaultInstance()) && !player.isInWaterOrBubble()) {
                sendDash(item);
                player.moveRelative(1, new Vec3(0, 0, -10));
                downPressed = false;
                downKeyUnpressed = false;
            } else {
                downPressed = true;
            }
        } else if (downPressed) {
            downKeyUnpressed = true;
        }
        //left
        if (InputHandler.isHoldingLeft(player)) {
            if (InputHandler.isHoldingLeft(player) && leftPressed && leftKeyUnpressed && !player.getCooldowns().isOnCooldown(item.getDefaultInstance()) && !player.isInWaterOrBubble()) {
                sendDash(item);
                player.moveRelative(1, new Vec3(10, 0, 0));
                leftPressed = false;
                leftKeyUnpressed = false;
            } else {
                leftPressed = true;
            }
        } else if (leftPressed) {
            leftKeyUnpressed = true;
        }
        //right
        if (InputHandler.isHoldingRight(player)) {
            if (InputHandler.isHoldingRight(player) && rightPressed && rightKeyUnpressed && !player.getCooldowns().isOnCooldown(item.getDefaultInstance()) && !player.isInWaterOrBubble()) {
                sendDash(item);
                player.moveRelative(1, new Vec3(-10, 0, 0));
                rightPressed = false;
                rightKeyUnpressed = false;
            } else {
                rightPressed = true;
            }
        } else if (rightPressed) {
            rightKeyUnpressed = true;
        }
    }

    @Environment(EnvType.CLIENT)
    private static void sendDash(Item item) {
        ClientPlayNetworking.send(new ItemNetworkType(item.getDefaultInstance(), 0, UUID.randomUUID(), ServerPacketHandler.DASH_PACKET_ID));
    }

    @Environment(EnvType.CLIENT)
    public static void alphaBlit(GuiGraphics guiGraphics, Function<ResourceLocation, RenderType> function, ResourceLocation resourceLocation, int i, int j, float f, float g, int k, int l, int m, int n, float v) {
        RenderType renderType = function.apply(resourceLocation);
        Matrix4f matrix4f = guiGraphics.pose.last().pose();
        VertexConsumer vertexConsumer = guiGraphics.bufferSource.getBuffer(renderType);

        float uMin = (f + 0.0F) / (float)m;
        float uMax = (f + (float)k) / (float)m;
        float vMin = (g + 0.0F) / (float)n;
        float vMax = (g + (float)l) / (float)n;

        vertexConsumer.addVertex(matrix4f, (float)i, (float)j, 0.0F).setUv(uMin, vMin).setColor(1, 1, 1, v);
        vertexConsumer.addVertex(matrix4f, (float)i, (float)(j + l), 0.0F).setUv(uMin, vMax).setColor(1, 1, 1, v);
        vertexConsumer.addVertex(matrix4f, (float)(i + k), (float)(j + l), 0.0F).setUv(uMax, vMax).setColor(1, 1, 1, v);
        vertexConsumer.addVertex(matrix4f, (float)(i + k), (float)j, 0.0F).setUv(uMax, vMin).setColor(1, 1, 1, v);
    }
}
