package terramine.client.render.entity.renderer.misc;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ItemClusterRenderState;
import net.minecraft.client.renderer.entity.state.ItemEntityRenderState;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.ItemEntity;
import org.jetbrains.annotations.NotNull;
import terramine.common.entity.misc.ClientItemEntity;

import static net.minecraft.client.renderer.blockentity.BeaconRenderer.renderPart;

@Environment(EnvType.CLIENT)
public class ClientItemEntityRenderer extends EntityRenderer<ClientItemEntity, ItemEntityRenderState> {
    public static final ResourceLocation BEAM_LOCATION = ResourceLocation.withDefaultNamespace("textures/entity/beacon_beam.png");
    private final ItemModelResolver itemModelResolver;
    private final RandomSource random = RandomSource.create();
    private ClientItemEntity itemEntity;

    public ClientItemEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemModelResolver = context.getItemModelResolver();
        this.shadowRadius = 0;
        this.shadowStrength = 0.75F;
    }

    @Override
    public @NotNull ItemEntityRenderState createRenderState() {
        return new ItemEntityRenderState();
    }

    @Override
    public void extractRenderState(ClientItemEntity itemEntity, ItemEntityRenderState itemEntityRenderState, float f) {
        super.extractRenderState(itemEntity, itemEntityRenderState, f);
        itemEntityRenderState.ageInTicks = (float)itemEntity.getAge() + f;
        itemEntityRenderState.bobOffset = itemEntity.bobOffs;
        this.itemEntity = itemEntity;
        itemEntityRenderState.extractItemGroupRenderState(itemEntity, itemEntity.getItem(), this.itemModelResolver);
    }

    @Override
    public void render(@NotNull ItemEntityRenderState itemEntityRenderState, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int i) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player.getUUID().equals(itemEntity.getClientPlayer())) {
            if (!itemEntityRenderState.item.isEmpty()) {
                poseStack.pushPose();
                float g = Mth.sin(itemEntityRenderState.ageInTicks / 10.0F + itemEntityRenderState.bobOffset) * 0.1F + 0.1F;
                float h = itemEntityRenderState.item.transform().scale.y();
                poseStack.translate(0.0F, g + 0.25F * h, 0.0F);
                float j = ItemEntity.getSpin(itemEntityRenderState.ageInTicks, itemEntityRenderState.bobOffset);
                poseStack.mulPose(Axis.YP.rotation(j));
                renderMultipleFromCount(poseStack, multiBufferSource, i, itemEntityRenderState, this.random);
                poseStack.popPose();
                super.render(itemEntityRenderState, poseStack, multiBufferSource, i);
                renderBeacon(itemEntity, itemEntityRenderState.ageInTicks, itemEntity.level().getGameTime(), poseStack, multiBufferSource);
            }
        } else {
            this.shadowRadius = 0;
        }
    }

    // Code from LootBeams, may make look neater later
    // todo: maybe not working after update, needs testing
    // todo: make beam fade in and out nicely, minor thing to do later
    private void renderBeacon(ClientItemEntity item, float pticks, long worldtime, PoseStack stack, MultiBufferSource buffer) {
        RenderSystem.enableDepthTest();
        float beamAlpha = 0.75f;
        //Fade out when close
        double distance = Minecraft.getInstance().player.distanceToSqr(item);
        double fadeDistance = 1000 * 4;
        if (distance < 3) {
            beamAlpha *= Math.max(0, distance - 4);
        } else if (distance > fadeDistance * 0.75f) {
            float fade = (float) (distance - fadeDistance * 0.75f);
            beamAlpha *= Math.max(0, 1 - fade);
        }
        // Don't render beam if its too transparent
        if (beamAlpha <= 0.01f) {
            return;
        }

        float beamRadius = 0.05f;
        float glowRadius = beamRadius + (beamRadius * 0.2f);
        float beamHeight = 100f;
        float yOffset = -0.3f;

        float R = (float) (Math.sin(worldtime * 0.1f) * 0.5 + 0.5);
        float G = (float) (Math.sin(worldtime * 0.1f + 2 * Math.PI / 3) * 0.5 + 0.5);
        float B = (float) (Math.sin(worldtime * 0.1f + 4 * Math.PI / 3) * 0.5 + 0.5);

        stack.pushPose();

        //Render main beam
        stack.pushPose();
        float rotation = (float) Math.floorMod(worldtime, 40L) + pticks;
        stack.mulPose(Axis.YP.rotationDegrees(rotation * 2.25F - 45.0F));
        stack.translate(0, yOffset, 0);
        stack.translate(0, 1, 0);
        stack.mulPose(Axis.XP.rotationDegrees(180));
        stack.mulPose(Axis.XP.rotationDegrees(-180));
        renderPart(stack, buffer.getBuffer(RenderType.beaconBeam(BEAM_LOCATION, false)).setColor(R, G, B, beamAlpha), 0, 0, (int) beamHeight, 0.0F, beamRadius, beamRadius, 0.0F, -beamRadius, 0.0F, 0.0F, -beamRadius, 0f,1f, 0f, 1f);
        stack.popPose();

        //Render glow around main beam
        stack.pushPose();
        stack.translate(0, yOffset, 0);
        stack.translate(0, 1, 0);
        stack.mulPose(Axis.XP.rotationDegrees(180));
        stack.mulPose(Axis.XP.rotationDegrees(-180));
        renderPart(stack, buffer.getBuffer(RenderType.beaconBeam(BEAM_LOCATION, false)).setColor(R, G, B, beamAlpha * 0.4f), 0, 0, (int) beamHeight, -glowRadius, -glowRadius, glowRadius, -glowRadius, -beamRadius, glowRadius, glowRadius, glowRadius, 0f,1f, 0f, 1f);
        stack.popPose();

        stack.popPose();
        RenderSystem.disableDepthTest();
    }

    public static void renderMultipleFromCount(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, ItemClusterRenderState itemClusterRenderState, RandomSource randomSource) {
        randomSource.setSeed(itemClusterRenderState.seed);
        int j = itemClusterRenderState.count;
        ItemStackRenderState itemStackRenderState = itemClusterRenderState.item;
        boolean bl = itemStackRenderState.isGui3d();
        float f = itemStackRenderState.transform().scale.x();
        float g = itemStackRenderState.transform().scale.y();
        float h = itemStackRenderState.transform().scale.z();
        float l;
        float m;
        if (!bl) {
            float k = -0.0F * (float)(j - 1) * 0.5F * f;
            l = -0.0F * (float)(j - 1) * 0.5F * g;
            m = -0.09375F * (float)(j - 1) * 0.5F * h;
            poseStack.translate(k, l, m);
        }

        for(int n = 0; n < j; ++n) {
            poseStack.pushPose();
            if (n > 0) {
                if (bl) {
                    l = (randomSource.nextFloat() * 2.0F - 1.0F) * 0.15F;
                    m = (randomSource.nextFloat() * 2.0F - 1.0F) * 0.15F;
                    float o = (randomSource.nextFloat() * 2.0F - 1.0F) * 0.15F;
                    poseStack.translate(l, m, o);
                } else {
                    l = (randomSource.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
                    m = (randomSource.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
                    poseStack.translate(l, m, 0.0F);
                }
            }

            itemStackRenderState.render(poseStack, multiBufferSource, i, OverlayTexture.NO_OVERLAY);
            poseStack.popPose();
            if (!bl) {
                poseStack.translate(0.0F * f, 0.0F * g, 0.09375F * h);
            }
        }
    }
}
