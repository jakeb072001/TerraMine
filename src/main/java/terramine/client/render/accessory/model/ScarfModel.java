package terramine.client.render.accessory.model;

import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.util.function.Function;

public class ScarfModel extends HumanoidModel<HumanoidRenderState> {

    private final ModelPart cloak = body.getChild("cloak");

    public ScarfModel(ModelPart part, Function<ResourceLocation, RenderType> renderType) {
        super(part, renderType);
    }

    @Override
    public void setupAnim(HumanoidRenderState renderState) {
        double x = renderState.elytraRotX - renderState.x;
        double y = renderState.elytraRotY - renderState.y;
        double z = renderState.elytraRotZ - renderState.z;
        float f = renderState.bodyRot;
        double d3 = Mth.sin(f * ((float) Math.PI / 180));
        double d4 = -Mth.cos(f * ((float) Math.PI / 180));
        float f1 = (float) y * 10;
        f1 = Mth.clamp(f1, -6, 32);
        float f2 = (float) (x * d3 + z * d4) * 100;
        f2 = Mth.clamp(f2, 0, 150);
        if (f2 < 0) {
            f2 = 0;
        }

        AnimationUtils.bobModelPart(this.cloak, renderState.ageInTicks, -1.0F);

        cloak.xRot = body.xRot + (6 + f2 / 2 + f1) / 180 * (float) Math.PI;
    }

    public static MeshDefinition createScarf() {
        MeshDefinition mesh = createMesh(new CubeDeformation(0.5F), 0);
        PartDefinition partDefinition = mesh.getRoot();
        PartDefinition partDefinition2 = partDefinition.clearChild("head");
        partDefinition2.clearChild("hat");
        partDefinition.clearChild("left_arm");
        partDefinition.clearChild("right_arm");
        partDefinition.clearChild("left_leg");
        partDefinition.clearChild("right_leg");

        partDefinition.addOrReplaceChild(
                "body",
                CubeListBuilder.create()
                        .texOffs(0, 16)
                        .addBox(-6.01F, -2, -4, 12, 6, 8),
                PartPose.ZERO
        );

        partDefinition.getChild("body").addOrReplaceChild(
                "cloak",
                CubeListBuilder.create()
                        .texOffs(32, 0)
                        .addBox(-5, 0, 0, 5, 12, 2),
                PartPose.offset(0, 0, 1.99F)
        );

        return mesh;
    }
}
