package terramine.client.render.entity.model.throwables;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import org.jetbrains.annotations.NotNull;
import terramine.common.entity.throwables.GrenadeEntity;

public class GrenadeModel extends HierarchicalModel<GrenadeEntity> {
    protected final ModelPart grenade;

    public GrenadeModel(ModelPart root) {
        this.grenade = root.getChild("grenade");
    }

    public static LayerDefinition createLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        partDefinition.addOrReplaceChild("grenade", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 9).mirror().addBox(-1.0F, -4.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(8, 11).mirror().addBox(-1.5F, -3.5F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 2.0F, 0.0F));

        return LayerDefinition.create(meshDefinition, 16, 16);
    }

    @Override
    public void setupAnim(@NotNull GrenadeEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float zRot, float xRot) {
        grenade.xRot = xRot;
        grenade.zRot = zRot;
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        grenade.render(poseStack, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return this.grenade;
    }
}
