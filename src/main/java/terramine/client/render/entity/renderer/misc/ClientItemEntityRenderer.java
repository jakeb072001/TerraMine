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
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import terramine.common.misc.ClientItemEntity;

import static net.minecraft.client.renderer.blockentity.BeaconRenderer.renderPart;

@Environment(EnvType.CLIENT)
public class ClientItemEntityRenderer extends EntityRenderer<ClientItemEntity> {
    private final ItemRenderer itemRenderer;
    private final RandomSource random = RandomSource.create();

    public ClientItemEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
        this.shadowRadius = 0;
        this.shadowStrength = 0.75F;
    }

    public void render(@NotNull ClientItemEntity itemEntity, float f, float g, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int i) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player.getUUID().equals(itemEntity.getClientPlayer())) {
            poseStack.pushPose();
            ItemStack itemStack = itemEntity.getItem();
            this.shadowRadius = 0.15F;
            int j = itemStack.isEmpty() ? 187 : Item.getId(itemStack.getItem()) + itemStack.getDamageValue();
            this.random.setSeed(j);
            BakedModel bakedModel = this.itemRenderer.getModel(itemStack, itemEntity.level(), null, itemEntity.getId());
            boolean bl = bakedModel.isGui3d();
            float l = Mth.sin(((float) itemEntity.getAge() + g) / 10.0F + itemEntity.bobOffs) * 0.1F + 0.1F;
            float m = bakedModel.getTransforms().getTransform(ItemDisplayContext.GROUND).scale.y();
            poseStack.translate(0.0F, l + 0.25F * m, 0.0F);
            float n = itemEntity.getSpin(g);
            poseStack.mulPose(Axis.YP.rotation(n));
            float o = bakedModel.getTransforms().ground.scale.x();
            float p = bakedModel.getTransforms().ground.scale.y();
            float q = bakedModel.getTransforms().ground.scale.z();
            float s;
            float t;

            poseStack.pushPose();
            s = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
            t = (this.random.nextFloat() * 2.0F - 1.0F) * 0.15F * 0.5F;
            poseStack.translate(s, t, 0.0F);

            this.itemRenderer.render(itemStack, ItemDisplayContext.GROUND, false, poseStack, multiBufferSource, i, OverlayTexture.NO_OVERLAY, bakedModel);
            poseStack.popPose();
            if (!bl) {
                poseStack.translate(0.0F * o, 0.0F * p, 0.09375F * q);
            }

            poseStack.popPose();
            super.render(itemEntity, f, g, poseStack, multiBufferSource, i);
            renderBeacon(itemEntity, f, itemEntity.level().getGameTime(), poseStack, multiBufferSource);
        } else {
            this.shadowRadius = 0;
        }
    }

    // Code from LootBeams, may make look neater later
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
        renderPart(stack, buffer.getBuffer(RenderType.lightning()), R, G, B, beamAlpha, 0, (int) beamHeight, 0.0F, beamRadius, beamRadius, 0.0F, -beamRadius, 0.0F, 0.0F, -beamRadius, 0f,1f, 0f, 1f);
        stack.popPose();

        //Render glow around main beam
        stack.pushPose();
        stack.translate(0, yOffset, 0);
        stack.translate(0, 1, 0);
        stack.mulPose(Axis.XP.rotationDegrees(180));
        stack.mulPose(Axis.XP.rotationDegrees(-180));
        renderPart(stack, buffer.getBuffer(RenderType.lightning()), R, G, B, beamAlpha * 0.4f, 0, (int) beamHeight, -glowRadius, -glowRadius, glowRadius, -glowRadius, -beamRadius, glowRadius, glowRadius, glowRadius, 0f,1f, 0f, 1f);
        stack.popPose();

        stack.popPose();
        RenderSystem.disableDepthTest();
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull ClientItemEntity itemEntity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
