package terramine.client.render.entity.model.misc;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class MeteoriteModel<T extends Entity> extends EntityModel<T> {

    protected final ModelPart star;

    public MeteoriteModel(ModelPart part) {
        star = part.getChild("star");
    }

    @Override
    public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float xRot, float yRot) {
        star.zRot = 135f;
        //star.xRot = xRot;
        star.yRot = yRot;
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack matrixStack, @NotNull VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        star.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public static LayerDefinition createLayer() {
        MeshDefinition mesh = new MeshDefinition();

        mesh.getRoot().addOrReplaceChild(
                "star",
                CubeListBuilder.create()
                .texOffs(8, 12)
                .addBox(-3.0F, 3.0F, 0.0F, 2.0F, 1.0F, 1.0F)
                .texOffs(0, 0)
                .addBox(-3.0F, -4.0F, 0.0F, 5.0F, 5.0F, 1.0F)
                .texOffs(11, 15)
                .addBox(-1.0F, -7.0F, 0.0F, 1.0F, 3.0F, 1.0F)
                .texOffs(4, 16)
                .addBox(-2.0F, -6.0F, 0.0F, 1.0F, 2.0F, 1.0F)
                .texOffs(0, 16)
                .addBox(0.0F, -6.0F, 0.0F, 1.0F, 2.0F, 1.0F)
                .texOffs(17, 11)
                .addBox(-4.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F)
                .texOffs(0, 9)
                .addBox(-5.0F, 1.0F, 0.0F, 4.0F, 2.0F, 1.0F)
                .texOffs(12, 4)
                .addBox(-6.0F, -3.0F, 0.0F, 3.0F, 1.0F, 1.0F)
                .texOffs(17, 8)
                .addBox(2.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F)
                .texOffs(0, 6)
                .addBox(0.0F, 1.0F, 0.0F, 4.0F, 2.0F, 1.0F)
                .texOffs(12, 2)
                .addBox(2.0F, -3.0F, 0.0F, 3.0F, 1.0F, 1.0F)
                .texOffs(12, 0)
                .addBox(2.0F, -2.0F, 0.0F, 3.0F, 1.0F, 1.0F)
                .texOffs(0, 12)
                .addBox(-6.0F, -2.0F, 0.0F, 3.0F, 1.0F, 1.0F)
                .texOffs(6, 14)
                .addBox(-5.0F, -1.0F, 0.0F, 2.0F, 1.0F, 1.0F)
                .texOffs(0, 14)
                .addBox(2.0F, -1.0F, 0.0F, 2.0F, 1.0F, 1.0F)
                .texOffs(10, 9)
                .addBox(-6.0F, 3.0F, 0.0F, 3.0F, 2.0F, 1.0F)
                .texOffs(10, 6)
                .addBox(2.0F, 3.0F, 0.0F, 3.0F, 2.0F, 1.0F)
                .texOffs(15, 15)
                .addBox(-1.0F, 1.0F, 0.0F, 1.0F, 2.0F, 1.0F)
                .texOffs(13, 13)
                .addBox(0.0F, 3.0F, 0.0F, 2.0F, 1.0F, 1.0F),
                PartPose.offset(0.0F, 19.0F, 0.0F)
        );

        return LayerDefinition.create(mesh, 32, 32);
    }
}
