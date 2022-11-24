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

    protected final ModelPart meteorite;

    public MeteoriteModel(ModelPart part) {
        meteorite = part.getChild("meteorite");
    }

    @Override
    public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float xRot, float yRot) {
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack matrixStack, @NotNull VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        meteorite.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public static LayerDefinition createLayer() {
        MeshDefinition partDefinition = new MeshDefinition();

        partDefinition.getRoot().addOrReplaceChild(
                "meteorite",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-20.0F, -40.0F, -20.0F, 40.0F, 40.0F, 40.0F),
                PartPose.offset(0.0F, -10F, 0.0F)
        );

        return LayerDefinition.create(partDefinition, 256, 256);
    }
}
