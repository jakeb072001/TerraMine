package terramine.client.render.entity.model.mobs.hardmode;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import terramine.client.render.entity.states.TerrariaLivingEntityRenderState;
import terramine.common.entity.mobs.hardmode.MimicEntity;

public class MimicChestLayerModel extends EntityModel<TerrariaLivingEntityRenderState> {

    protected final ModelPart bottom;
    protected final ModelPart lid;

    public MimicChestLayerModel(ModelPart part) {
        super(part);
        bottom = part.getChild("bottom");
        lid = part.getChild("lid");
    }

    @Override
    public void setupAnim(TerrariaLivingEntityRenderState mimic) {
    }

    public void prepareMobModel(TerrariaLivingEntityRenderState renderState, float partialTicks) {
        MimicModel.setChestRotations((MimicEntity) renderState.entity, partialTicks, lid, bottom);
    }

    public static LayerDefinition createLayer() {
        MeshDefinition mesh = new MeshDefinition();

        mesh.getRoot().addOrReplaceChild(
                "bottom",
                CubeListBuilder.create()
                        .texOffs(0, 19)
                        .addBox(1, -9, 0, 14, 10, 14),
                PartPose.offset(0, 9, 1)
        );
        mesh.getRoot().addOrReplaceChild(
                "lid",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(1, 0, 0, 14, 5, 14)
                        .texOffs(0, 0) // latch
                        .addBox(7, -1 - 1, 15 - 1, 2, 4, 1),
                PartPose.offset(0, 9, 1)
        );

        return LayerDefinition.create(mesh, 64, 64);
    }
}
