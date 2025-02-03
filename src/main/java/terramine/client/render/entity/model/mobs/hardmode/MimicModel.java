package terramine.client.render.entity.model.mobs.hardmode;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import terramine.client.render.entity.states.TerrariaLivingEntityRenderState;
import terramine.common.entity.mobs.hardmode.MimicEntity;

public class MimicModel extends EntityModel<TerrariaLivingEntityRenderState> {

    protected final ModelPart bottom;
    protected final ModelPart lid;

    public MimicModel(ModelPart part) {
        super(part);
        bottom = part.getChild("bottom");
        lid = part.getChild("lid");
    }

    @Override
    public void setupAnim(TerrariaLivingEntityRenderState mimic) {
        prepareMobModel(mimic, mimic.ageInTicks);
    }

    public void prepareMobModel(TerrariaLivingEntityRenderState renderState, float partialTicks) {
        setChestRotations((MimicEntity) renderState.entity, partialTicks, lid, bottom);
    }

    protected static void setChestRotations(MimicEntity mimic, float partialTicks, ModelPart lid, ModelPart bottom) {
        if (mimic.ticksInAir > 0) {
            lid.xRot = Math.max(-60, (mimic.ticksInAir - 1 + partialTicks) * -6) * 0.0174533F;
            bottom.xRot = Math.min(30, (mimic.ticksInAir - 1 + partialTicks) * 3) * 0.0174533F;
        } else {
            lid.xRot = 0;
            bottom.xRot = 0;
        }
    }

    public static LayerDefinition createLayer() {
        MeshDefinition mesh = new MeshDefinition();

        mesh.getRoot().addOrReplaceChild(
                "bottom",
                CubeListBuilder.create()
                        .texOffs(0, 15)
                        .addBox(-6, -4, -13, 12, 3, 12)
                        .texOffs(36, 15)
                        .addBox(-6, -1, -13, 12, 0, 12, new CubeDeformation(0.02F)),
                PartPose.offset(0, 15, 7)
        );
        mesh.getRoot().addOrReplaceChild(
                "lid",
                CubeListBuilder.create()
                        .texOffs(0, 0) // teeth
                        .addBox(-6, 0, -13, 12, 3, 12)
                        .texOffs(24, 0) // overlay
                        .addBox(-6, 0, -13, 12, 0, 12, new CubeDeformation(0.02F)),
                PartPose.offset(0, 15, 7)
        );

        return LayerDefinition.create(mesh, 64, 32);
    }
}
