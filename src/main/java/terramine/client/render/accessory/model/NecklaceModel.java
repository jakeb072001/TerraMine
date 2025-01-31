package terramine.client.render.accessory.model;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;

public class NecklaceModel extends HumanoidModel<HumanoidRenderState> {

    public NecklaceModel(ModelPart part) {
        super(part, RenderType::entityCutoutNoCull);
    }

    public static MeshDefinition createNecklace(CubeListBuilder body) {
        MeshDefinition mesh = createMesh(CubeDeformation.NONE, 0);
        PartDefinition partDefinition = mesh.getRoot();
        PartDefinition partDefinition2 = partDefinition.clearChild("head");
        partDefinition2.clearChild("hat");
        partDefinition.clearChild("left_arm");
        partDefinition.clearChild("right_arm");
        partDefinition.clearChild("left_leg");
        partDefinition.clearChild("right_leg");

        partDefinition.addOrReplaceChild(
                "body",
                body.texOffs(0, 0)
                        .addBox(-(2 * 8) / 2F, -1 / 2F, -(2 * 4 + 1) / 2F, 2 * 8, 2 * 12 + 1, 2 * 4 + 1),
                PartPose.ZERO
        );

        return mesh;
    }

    public static MeshDefinition createCenteredNecklace(CubeListBuilder body) {
        MeshDefinition mesh = createMesh(CubeDeformation.NONE, 0);
        PartDefinition partDefinition = mesh.getRoot();
        PartDefinition partDefinition2 = partDefinition.clearChild("head");
        partDefinition2.clearChild("hat");
        partDefinition.clearChild("left_arm");
        partDefinition.clearChild("right_arm");
        partDefinition.clearChild("left_leg");
        partDefinition.clearChild("right_leg");

        partDefinition.addOrReplaceChild(
                "body",
                body.texOffs(0, 0)
                        .addBox(-(2 * 8 + 1) / 2F, -1 / 2F, -(2 * 4 + 1) / 2F, 2 * 8 + 1, 2 * 12 + 1, 2 * 4 + 1),
                PartPose.ZERO
        );

        return mesh;
    }

    public static MeshDefinition createCrossNecklace() {
        CubeListBuilder body = CubeListBuilder.create();

        // cross vertical
        body.texOffs(52, 0);
        body.addBox(-0.5F, 4.5F, -5, 1, 4, 1);

        // cross horizontal
        body.texOffs(56, 0);
        body.addBox(-1.5F, 5.5F, -5, 3, 1, 1);

        return createCenteredNecklace(body);
    }

    public static MeshDefinition createPanicNecklace() {
        CubeListBuilder body = CubeListBuilder.create();

        // gem top
        body.texOffs(52, 0);
        body.addBox(-2.5F, 5.5F, -5, 2, 2, 1);
        body.texOffs(58, 0);
        body.addBox(0.5F, 5.5F, -5, 2, 2, 1);

        // gem middle
        body.texOffs(52, 3);
        body.addBox(-1.5F, 6.5F, -5, 3, 2, 1);

        // gem bottom
        body.texOffs(60, 4);
        body.addBox(-0.5F, 8.5F, -5, 1, 1, 1);

        return createCenteredNecklace(body);
    }
}
