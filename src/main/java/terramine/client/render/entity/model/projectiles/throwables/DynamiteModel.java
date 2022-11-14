package terramine.client.render.entity.model.projectiles.throwables;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import org.jetbrains.annotations.NotNull;
import terramine.common.entity.throwables.DynamiteEntity;

public class DynamiteModel extends HierarchicalModel<DynamiteEntity> {
    protected final ModelPart dynamite;

    public DynamiteModel(ModelPart root) {
        this.dynamite = root.getChild("dynamite");
    }

    public static LayerDefinition createLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();


        partDefinition.addOrReplaceChild("dynamite", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -4.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(8, 2).addBox(-0.5F, -6.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(6, 0).addBox(0.5F, -7.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2F, 0.0F));

        return LayerDefinition.create(meshDefinition, 16, 16);
    }

    @Override
    public void setupAnim(@NotNull DynamiteEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float zRot, float xRot) {
        dynamite.xRot = xRot;
        dynamite.zRot = zRot;
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        dynamite.render(poseStack, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return this.dynamite;
    }
}
