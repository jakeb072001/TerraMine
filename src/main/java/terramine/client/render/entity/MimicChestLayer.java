package terramine.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import terramine.TerraMine;
import terramine.client.render.entity.model.MimicChestLayerModel;
import terramine.client.render.entity.model.MimicModel;
import terramine.common.entity.MimicEntity;
import terramine.common.init.ModModelLayers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class MimicChestLayer extends RenderLayer<MimicEntity, MimicModel> {

    public static final ResourceLocation CHEST_ATLAS = new ResourceLocation("textures/atlas/chest.png");
    private final MimicChestLayerModel chestModel;
    public final Material christmasChestMaterial;
    public final Material vanillaChestMaterial;
    public final List<Material> chestMaterials;

    public MimicChestLayer(RenderLayerParent<MimicEntity, MimicModel> entityRenderer, EntityModelSet modelSet) {
        super(entityRenderer);

        chestModel = new MimicChestLayerModel(modelSet.bakeLayer(ModModelLayers.MIMIC_OVERLAY));
        chestMaterials = new ArrayList<>();
        christmasChestMaterial = Sheets.CHEST_XMAS_LOCATION;
        vanillaChestMaterial = Sheets.CHEST_LOCATION;

        chestMaterials.add(vanillaChestMaterial);
        chestMaterials.add(christmasChestMaterial);
        chestMaterials.add(new Material(CHEST_ATLAS, TerraMine.id("block/chests/gold/gold_chest")));
        chestMaterials.add(new Material(CHEST_ATLAS, TerraMine.id("block/chests/frozen/frozen_chest")));
        chestMaterials.add(new Material(CHEST_ATLAS, TerraMine.id("block/chests/shadow/shadow_chest")));
    }

    @Override
    public void render(@NotNull PoseStack matrixStack, @NotNull MultiBufferSource buffer, int packedLight, MimicEntity mimic, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!mimic.isInvisible()) {
            matrixStack.pushPose();

            matrixStack.mulPose(Vector3f.XP.rotationDegrees(180));
            matrixStack.translate(-0.5, -1.5, -0.5);

            getParentModel().copyPropertiesTo(chestModel);
            chestModel.prepareMobModel(mimic, limbSwing, limbSwingAmount, partialTicks);
            chestModel.setupAnim(mimic, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            VertexConsumer builder = getChestMaterial(mimic).buffer(buffer, RenderType::entityCutout);
            chestModel.renderToBuffer(matrixStack, builder, packedLight, LivingEntityRenderer.getOverlayCoords(mimic, 0), 1, 1, 1, 1);

            matrixStack.popPose();
        }
    }

    private Material getChestMaterial(MimicEntity mimic) {
        if (chestMaterials.size() == 1) {
            return chestMaterials.get(0);
        }
        return chestMaterials.get(mimic.getMimicType());
    }
}
