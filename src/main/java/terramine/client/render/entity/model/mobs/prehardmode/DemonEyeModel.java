package terramine.client.render.entity.model.mobs.prehardmode;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.RenderType;
import org.jetbrains.annotations.NotNull;
import terramine.common.entity.mobs.prehardmode.DemonEyeEntity;

public class DemonEyeModel<T extends DemonEyeEntity> extends HierarchicalModel<T> {

    protected final ModelPart root;
    protected final ModelPart eye;
    protected final ModelPart veins;


    public DemonEyeModel(ModelPart part) {
        super(RenderType::entityCutout);
        root = part;
        eye = part.getChild("eye");
        veins = part.getChild("veins");
    }

    @Override
    public void setupAnim(@NotNull T eye, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        // todo: animate veins
    }

    @Override
    public void renderToBuffer(PoseStack matrixStack, @NotNull VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        matrixStack.pushPose();
        matrixStack.scale(1.5f, 1.5f,1.5f);
        matrixStack.translate(0f, 1.5f - 1.5 * 1.375f, -0.1f); // 1.5f - 1.5 * scale, normally but eye seems to be slightly off center in model so account for that here
        eye.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        veins.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        matrixStack.popPose();
    }

    @Override
    public ModelPart root() {
        return this.root;
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
