package terramine.client.render.entity.renderer.misc;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import terramine.common.misc.ClientItemEntity;

// todo: not correctly working, host of lan can see their bag but other player can not
@Environment(EnvType.CLIENT)
public class ClientItemEntityRenderer extends EntityRenderer<ItemEntity> {
    private final ItemRenderer itemRenderer;
    private final RandomSource random = RandomSource.create();

    public ClientItemEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
        this.shadowRadius = 0;
        this.shadowStrength = 0.75F;
    }

    public void render(@NotNull ItemEntity itemEntity, float f, float g, @NotNull PoseStack poseStack, @NotNull MultiBufferSource multiBufferSource, int i) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (itemEntity instanceof ClientItemEntity clientItemEntity && player != null && player.getUUID() == clientItemEntity.getClientPlayer()) {
            poseStack.pushPose();
            this.shadowRadius = 0.15F;
            ItemStack itemStack = itemEntity.getItem();
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
        }
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull ItemEntity itemEntity) {
        return TextureAtlas.LOCATION_BLOCKS;
    }
}
