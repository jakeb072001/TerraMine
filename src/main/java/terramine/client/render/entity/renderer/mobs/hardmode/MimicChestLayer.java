package terramine.client.render.entity.renderer.mobs.hardmode;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import terramine.TerraMine;
import terramine.client.render.entity.model.mobs.hardmode.MimicChestLayerModel;
import terramine.client.render.entity.model.mobs.hardmode.MimicModel;
import terramine.client.render.entity.states.TerrariaLivingEntityRenderState;
import terramine.common.entity.mobs.hardmode.MimicEntity;
import terramine.common.init.ModModelLayers;

import java.util.ArrayList;
import java.util.List;

public class MimicChestLayer extends RenderLayer<TerrariaLivingEntityRenderState, MimicModel> {

    public static final ResourceLocation CHEST_ATLAS = ResourceLocation.withDefaultNamespace("textures/atlas/chest.png");
    private final MimicChestLayerModel chestModel;
    public final Material christmasChestMaterial;
    public final Material vanillaChestMaterial;
    public final List<Material> chestMaterials;

    public MimicChestLayer(RenderLayerParent<TerrariaLivingEntityRenderState, MimicModel> entityRenderer, EntityModelSet modelSet) {
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
    public void render(@NotNull PoseStack matrixStack, @NotNull MultiBufferSource buffer, int packedLight, TerrariaLivingEntityRenderState mimic, float partialTicks, float ageInTicks) {
        if (!mimic.entity.isInvisible()) {
            matrixStack.pushPose();

            matrixStack.mulPose(Axis.XP.rotationDegrees(180));
            matrixStack.translate(-0.5, -1.5, -0.5);

            for (int i = 0; i < getParentModel().allParts().size(); i++) {
                chestModel.allParts().get(i).copyFrom(getParentModel().allParts().get(i));
            }
            chestModel.prepareMobModel(mimic, partialTicks);
            chestModel.setupAnim(mimic);
            VertexConsumer builder = getChestMaterial((MimicEntity) mimic.entity).buffer(buffer, RenderType::entityCutout);
            chestModel.renderToBuffer(matrixStack, builder, packedLight, LivingEntityRenderer.getOverlayCoords(mimic, 0));

            matrixStack.popPose();
        }
    }

    private Material getChestMaterial(MimicEntity mimic) {
        if (chestMaterials.size() == 1) {
            return chestMaterials.getFirst();
        }
        return chestMaterials.get(mimic.getMimicType());
    }
}
