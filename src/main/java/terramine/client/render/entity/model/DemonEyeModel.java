package terramine.client.render.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import terramine.common.entity.DemonEyeEntity;

public class DemonEyeModel extends EntityModel<DemonEyeEntity> {

    protected final ModelPart eye;
    protected final ModelPart veins;

    public DemonEyeModel(ModelPart part) {
        eye = part.getChild("eye");
        veins = part.getChild("veins");
    }

    @Override
    public void setupAnim(DemonEyeEntity eye, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void prepareMobModel(DemonEyeEntity eye, float limbSwing, float limbSwingAmount, float partialTicks) {
    }

    @Override
    public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        eye.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        veins.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public static LayerDefinition createLayer() {
        MeshDefinition mesh = new MeshDefinition();

        mesh.getRoot().addOrReplaceChild(
                "eye",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-2.0F, -4.0F, 0.0F, 4.0F, 4.0F, 4.0F)
                        .texOffs(9, 9)
                        .addBox(-1.5F, -3.5F, -0.25F, 3.0F, 3.0F, 3.0F),
                PartPose.offset(0.0F, 24.0F, 0.0F)
        );
        mesh.getRoot().addOrReplaceChild(
                "veins",
                CubeListBuilder.create()
                        .texOffs(8, 11)
                        .addBox(1.0F, -4.0F, 4.0F, 0.0F, 4.0F, 4.0F)
                        .texOffs(0, 8)
                        .addBox(-1.0F, -4.0F, 4.0F, 0.0F, 4.0F, 4.0F)
                        .texOffs(8, 0)
                        .addBox(-2.0F, -3.0F, 4.0F, 4.0F, 0.0F, 4.0F)
                        .texOffs(0, 8)
                        .addBox(-2.0F, -1.0F, 4.0F, 4.0F, 0.0F, 4.0F),
                PartPose.offset(0.0F, 24.0F, 0.0F)
        );

        return LayerDefinition.create(mesh, 32, 32);
    }
}