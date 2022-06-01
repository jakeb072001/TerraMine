package terramine.client.render.trinket.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

import java.util.function.Function;

public class HeadModel extends HumanoidModel<LivingEntity> {

    public HeadModel(ModelPart part, Function<ResourceLocation, RenderType> renderType) {
        super(part, renderType);
    }

    public HeadModel(ModelPart part) {
        this(part, RenderType::entityCutoutNoCull);
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of(head);
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of();
    }

    public static MeshDefinition createEmptyHat(CubeListBuilder head) {
        MeshDefinition mesh = createMesh(CubeDeformation.NONE, 0);

        mesh.getRoot().addOrReplaceChild(
                "head",
                head,
                PartPose.ZERO
        );

        return mesh;
    }

    public static MeshDefinition createHat(CubeListBuilder head) {
        CubeDeformation deformation = new CubeDeformation(0.5F);

        head.texOffs(0, 0);
        head.addBox(-4, -8, -4, 8, 8, 8, deformation);

        return createEmptyHat(head);
    }

    public static MeshDefinition createDiagonalHat(CubeListBuilder head, CubeListBuilder diagonalParts, String partName) {
        MeshDefinition mesh = createHat(head);

        mesh.getRoot().getChild("head").addOrReplaceChild(
                partName,
                diagonalParts,
                PartPose.rotation(45 * (float) Math.PI / 180, 0, 0)
        );

        return mesh;
    }

    public static MeshDefinition createDivingHelmet() {
        CubeListBuilder head = CubeListBuilder.create();
        CubeListBuilder tube = CubeListBuilder.create();

        // mouth thingy
        head.texOffs(32, 0);
        head.addBox(-2, -1.5F, -6, 8, 2, 2);

        // tube
        tube.texOffs(0, 16);
        tube.addBox(4.01F, -5, -3, 2, 2, 12);

        return createDiagonalHat(head, tube, "tube");
    }

    public static MeshDefinition createSuperstitiousHat() {
        CubeListBuilder head = CubeListBuilder.create();

        head.texOffs(0, 0);
        head.addBox(-4, -16, -4, 8, 8, 8);
        head.texOffs(0, 16);
        head.addBox(-5, -9, -5, 10, 1, 10);

        return createEmptyHat(head);
    }

    public static MeshDefinition createVillagerHat() {
        CubeListBuilder head = CubeListBuilder.create();

        head.texOffs(0, 16);
        head.addBox(-8, -5.125F, -8, 16, 0, 16);

        return createHat(head);
    }

    public static MeshDefinition createWhoopeeCushion() {
        CubeListBuilder head = CubeListBuilder.create();

        // cushion
        head.texOffs(0, 0);
        head.addBox(-3, -10, -3, 6, 2, 6);

        // flap
        head.texOffs(0, 8);
        head.addBox(-2, -9.25F, 3, 4, 0, 4);

        return createEmptyHat(head);
    }
}
