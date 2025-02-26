package terramine.common.utility;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.MatrixUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.model.ShieldModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.*;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ImposterProtoChunk;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.PalettedContainer;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.phys.*;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import terramine.TerraMine;
import terramine.common.network.ServerPacketHandler;
import terramine.common.network.types.IntBoolUUIDNetworkType;
import terramine.common.network.types.ItemNetworkType;
import terramine.datagen.ModBiomes;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.function.Predicate;

public class Utilities { // todo: need to fix bug with magic missile where the projectile will jitter back and forth instead of just staying at its position

    // ---------------------------- server code ----------------------------

    // Magic Missile aiming
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

    public static void cleanBiome(ServerLevel level, BlockPos blockPos) {
        ResourceKey<Biome> biome = level.getNoiseBiome(blockPos.getX(), blockPos.getY(), blockPos.getZ()).unwrapKey().orElseThrow();
        if (biome.equals(ModBiomes.CRIMSON) || biome.equals(ModBiomes.CORRUPTION)) {
            biome = Biomes.PLAINS;
        }
        if (biome.equals(ModBiomes.CRIMSON_DESERT) || biome.equals(ModBiomes.CORRUPTION_DESERT)) {
            biome = Biomes.DESERT;
        }

        setBiome(level, blockPos, biome);
    }

    // todo: doesn't update live visually, grass blocks for example will still be tinted as the old biome (sometimes it does update though, it's a little inconsistent, maybe blocks need update? or maybe updateChunkAfterBiomeChange packet isn't working right)
    // Change biomes
    // Copied from EvilCraft, may improve later if possible
    public static void setBiome(ServerLevel level, BlockPos posIn, ResourceKey<Biome> biome) {
        BiomeManager biomeManager = level.getBiomeManager();
        // Worldgen applies some funk "magnifier" position transformation to a "noise position",
        // which can change the pos into some other internal pos.
        // We copied the logic in BiomeManager#getBiome below:
        int i = posIn.getX() - 2;
        int j = posIn.getY() - 2;
        int k = posIn.getZ() - 2;
        int l = i >> 2;
        int i1 = j >> 2;
        int j1 = k >> 2;
        double d0 = (double)(i & 3) / 4.0D;
        double d1 = (double)(j & 3) / 4.0D;
        double d2 = (double)(k & 3) / 4.0D;
        int k1 = 0;
        double d3 = Double.POSITIVE_INFINITY;

        for(int l1 = 0; l1 < 8; ++l1) {
            boolean flag = (l1 & 4) == 0;
            boolean flag1 = (l1 & 2) == 0;
            boolean flag2 = (l1 & 1) == 0;
            int i2 = flag ? l : l + 1;
            int j2 = flag1 ? i1 : i1 + 1;
            int k2 = flag2 ? j1 : j1 + 1;
            double d4 = flag ? d0 : d0 - 1.0D;
            double d5 = flag1 ? d1 : d1 - 1.0D;
            double d6 = flag2 ? d2 : d2 - 1.0D;
            double d7 = BiomeManager.getFiddledDistance(biomeManager.biomeZoomSeed, i2, j2, k2, d4, d5, d6);
            if (d3 > d7) {
                k1 = l1;
                d3 = d7;
            }
        }

        int l2 = (k1 & 4) == 0 ? l : l + 1;
        int i3 = (k1 & 2) == 0 ? i1 : i1 + 1;
        int j3 = (k1 & 1) == 0 ? j1 : j1 + 1;

        // Update biome data in chunk
        ChunkAccess chunk = level.getChunk(QuartPos.toSection(l2), QuartPos.toSection(j3), ChunkStatus.BIOMES, false);
        if (chunk instanceof ImposterProtoChunk) {
            chunk = ((ImposterProtoChunk) chunk).getWrapped();
        }
        if(chunk != null) {
            // HACK
            // Due to some weird thing in MC, different instances of the same biome can exist.
            // This hack allows us to convert to the biome instance that is required for chunk serialization.
            // This avoids weird errors in the form of "Received invalid biome id: -1" (#818)
            Registry<Biome> biomeRegistry = level.registryAccess().lookupOrThrow(Registries.BIOME);
            Optional<Holder.Reference<Biome>> biomeHack = biomeRegistry.get(biome);
            if (biomeHack.isEmpty()) {
                return;
            }

            // Update biome in chunk
            // Based on ChunkAccess#getNoiseBiome
            int minBuildHeight = QuartPos.fromBlock(chunk.getMinY());
            int maxHeight = minBuildHeight + QuartPos.fromBlock(chunk.getHeight()) - 1;
            int dummyY = Mth.clamp(i3, minBuildHeight, maxHeight);
            int sectionIndex = chunk.getSectionIndex(QuartPos.toBlock(dummyY));
            ((PalettedContainer<Holder<Biome>>) chunk.sections[sectionIndex].getBiomes()).set(l2 & 3, dummyY & 3, j3 & 3, biomeHack.get());

            chunk.markUnsaved();
        } else {
            TerraMine.LOGGER.warn("Tried changing biome at non-existing chunk for position " + posIn);
        }
    }

    // Copied from EvilCraft
    public static void updateChunkAfterBiomeChange(Level level, ChunkPos chunkPos) {
        LevelChunk chunkSafe = level.getChunkSource().getChunk(chunkPos.x, chunkPos.z, false);
        if (chunkSafe == null) {
            TerraMine.LOGGER.warn("Chunk is null, failed to update chunk after biome change");
            return;
        }
        ((ServerChunkCache) level.getChunkSource()).chunkMap.getPlayers(chunkPos, false).forEach((player) -> {
            player.connection.send(new ClientboundLevelChunkWithLightPacket(chunkSafe, ((ServerChunkCache) level.getChunkSource()).chunkMap.getLightEngine(), null, null));
            //NetworkManager.sendToPlayer(player, new IntBoolUUIDNetworkType(chunkPos.x, chunkPos.z, false, UUID.randomUUID()).setCustomType(ServerPacketHandler.UPDATE_BIOME_PACKET_ID));
            ServerPlayNetworking.send(player, new IntBoolUUIDNetworkType(chunkPos.x, chunkPos.z, false, UUID.randomUUID(), ServerPacketHandler.UPDATE_BIOME_PACKET_ID));
        });
    }

    // ---------------------------- client code ----------------------------

    // Auto swing
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

    // Dash movement
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

    // GUI Alpha Blit
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

    // Custom item renderer w/ dye support
    @Environment(EnvType.CLIENT)
    public static void renderItemCustomDye(ItemStackRenderState itemStackRenderState, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j, int dyeColour) {
        for(int k = 0; k < itemStackRenderState.activeLayerCount; ++k) {
            poseStack.pushPose();
            itemStackRenderState.layers[k].transform().apply(itemStackRenderState.isLeftHand, poseStack);
            poseStack.translate(-0.5F, -0.5F, -0.5F);
            if (itemStackRenderState.layers[k].specialRenderer != null) {
                shieldSpecialRender(new ShieldModel(EntityModelSet.vanilla().bakeLayer(ModelLayers.SHIELD)), (DataComponentMap) itemStackRenderState.layers[k].argumentForSpecialRendering, itemStackRenderState.displayContext, poseStack, multiBufferSource, i, j, itemStackRenderState.layers[k].foilType != ItemStackRenderState.FoilType.NONE, dyeColour);
            } else if (itemStackRenderState.layers[k].model != null) {
                VertexConsumer vertexConsumer;
                if (itemStackRenderState.layers[k].foilType == ItemStackRenderState.FoilType.SPECIAL) {
                    PoseStack.Pose pose = poseStack.last().copy();
                    if (itemStackRenderState.displayContext == ItemDisplayContext.GUI) {
                        MatrixUtil.mulComponentWise(pose.pose(), 0.5F);
                    } else if (itemStackRenderState.displayContext.firstPerson()) {
                        MatrixUtil.mulComponentWise(pose.pose(), 0.75F);
                    }

                    vertexConsumer = ItemRenderer.getCompassFoilBuffer(multiBufferSource, itemStackRenderState.layers[k].renderType, pose);
                } else {
                    vertexConsumer = ItemRenderer.getFoilBuffer(multiBufferSource, itemStackRenderState.layers[k].renderType, true, itemStackRenderState.layers[k].foilType != ItemStackRenderState.FoilType.NONE);
                }

                RandomSource randomSource = RandomSource.create();
                Direction[] var9 = Direction.values();

                for (Direction direction : var9) {
                    randomSource.setSeed(42L);
                    renderQuadList(poseStack, vertexConsumer, itemStackRenderState.layers[k].model.getQuads(null, direction, randomSource), itemStackRenderState.layers[k].tintLayers, i, j, dyeColour);
                }

                randomSource.setSeed(42L);
                renderQuadList(poseStack, vertexConsumer, itemStackRenderState.layers[k].model.getQuads(null, null, randomSource), itemStackRenderState.layers[k].tintLayers, i, j, dyeColour);
            }

            poseStack.popPose();
        }
    }

    private static void renderQuadList(PoseStack poseStack, VertexConsumer vertexConsumer, List<BakedQuad> list, int[] is, int i, int j, int dyeColour) {
        PoseStack.Pose pose = poseStack.last();

        BakedQuad bakedQuad;
        float f;
        float g;
        float h;
        float l;
        for(Iterator<BakedQuad> var7 = list.iterator(); var7.hasNext(); vertexConsumer.putBulkData(pose, bakedQuad, g, h, l, f, i, j)) {
            bakedQuad = var7.next();
            if (bakedQuad.isTinted()) {
                int k = ItemRenderer.getLayerColorSafe(is, bakedQuad.getTintIndex());
                f = (float)ARGB.alpha(k) / 255.0F;
                g = (float)ARGB.red(dyeColour) / 255.0F;
                h = (float)ARGB.green(dyeColour) / 255.0F;
                l = (float)ARGB.blue(dyeColour) / 255.0F;
            } else {
                f = 1.0F;
                g = (float)ARGB.red(dyeColour) / 255.0F;
                h = (float)ARGB.green(dyeColour) / 255.0F;
                l = (float)ARGB.blue(dyeColour) / 255.0F;
            }
        }
    }

    public static void shieldSpecialRender(ShieldModel shieldModel, @Nullable DataComponentMap dataComponentMap, ItemDisplayContext itemDisplayContext, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j, boolean bl, int dyeColour) {
        BannerPatternLayers bannerPatternLayers = dataComponentMap != null ? dataComponentMap.getOrDefault(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY) : BannerPatternLayers.EMPTY;
        DyeColor dyeColor = dataComponentMap != null ? dataComponentMap.get(DataComponents.BASE_COLOR) : null;
        boolean bl2 = !bannerPatternLayers.layers().isEmpty() || dyeColor != null;
        poseStack.pushPose();
        poseStack.scale(1.0F, -1.0F, -1.0F);
        Material material = bl2 ? ModelBakery.SHIELD_BASE : ModelBakery.NO_PATTERN_SHIELD;
        VertexConsumer vertexConsumer = material.sprite().wrap(ItemRenderer.getFoilBuffer(multiBufferSource, shieldModel.renderType(material.atlasLocation()), itemDisplayContext == ItemDisplayContext.GUI, bl));
        shieldModel.handle().render(poseStack, vertexConsumer, i, j, dyeColour);
        if (bl2) {
            renderPatterns(poseStack, multiBufferSource, i, j, shieldModel.plate(), material, false, Objects.requireNonNullElse(dyeColor, DyeColor.WHITE), bannerPatternLayers, bl, false, dyeColour);
        } else {
            shieldModel.plate().render(poseStack, vertexConsumer, i, j, dyeColour);
        }

        poseStack.popPose();
    }

    // todo: dye banner pattern (need to multiply g with dyeColour, or add them, idk)
    public static void renderPatterns(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j, ModelPart modelPart, Material material, boolean bl, DyeColor dyeColor, BannerPatternLayers bannerPatternLayers, boolean bl2, boolean bl3, int customDyeColour) {
        modelPart.render(poseStack, material.buffer(multiBufferSource, RenderType::entitySolid, bl3, bl2), i, j, customDyeColour);
        int g = dyeColor.getTextureDiffuseColor();
        modelPart.render(poseStack, (bl ? Sheets.BANNER_BASE : Sheets.SHIELD_BASE).buffer(multiBufferSource, RenderType::entityNoOutline), i, j, g);

        for(int k = 0; k < 16 && k < bannerPatternLayers.layers().size(); ++k) {
            BannerPatternLayers.Layer layer = bannerPatternLayers.layers().get(k);
            Material material2 = bl ? Sheets.getBannerMaterial(layer.pattern()) : Sheets.getShieldMaterial(layer.pattern());
            g = layer.color().getTextureDiffuseColor();
            modelPart.render(poseStack, material2.buffer(multiBufferSource, RenderType::entityNoOutline), i, j, g);
        }
    }
}
