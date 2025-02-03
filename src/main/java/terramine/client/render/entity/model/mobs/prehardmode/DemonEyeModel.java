package terramine.client.render.entity.model.mobs.prehardmode;

import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import terramine.client.render.entity.states.TerrariaLivingEntityRenderState;

public class DemonEyeModel<T extends TerrariaLivingEntityRenderState> extends EntityModel<T> {

    protected final ModelPart root;
    protected final ModelPart full_eye;
    protected final ModelPart eye;
    protected final ModelPart veins;

    public DemonEyeModel(ModelPart part) {
        super(part);
        root = part;
        full_eye = part.getChild("full_eye");
        eye = full_eye.getChild("eye");
        veins = full_eye.getChild("veins");
    }

    public static LayerDefinition createLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition partdefinition = mesh.getRoot();

        PartDefinition all = partdefinition.addOrReplaceChild("full_eye", CubeListBuilder.create(), PartPose.offset(0.0F, 22.0F, 2.0F));

        all.addOrReplaceChild("eye", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.0F, -2.125F, 4.0F, 4.0F, 4.0F)
                .texOffs(9, 9).addBox(-1.5F, -1.5F, -2.375F, 3.0F, 3.0F, 3.0F),
                PartPose.offset(0.0F, 0.0F, 0.125F));

        all.addOrReplaceChild("veins", CubeListBuilder.create().texOffs(8, 11).addBox(1.0F, -2.0F, 0.0F, 0.0F, 4.0F, 4.0F)
                .texOffs(0, 8).addBox(-1.0F, -2.0F, 0.0F, 0.0F, 4.0F, 4.0F)
                .texOffs(8, 0).addBox(-2.0F, -1.0F, 0.0F, 4.0F, 0.0F, 4.0F)
                .texOffs(0, 8).addBox(-2.0F, 1.0F, 0.0F, 4.0F, 0.0F, 4.0F),
                PartPose.offset(0.0F, 0.0F, 2.0F));

        return LayerDefinition.create(mesh, 32, 32);
    }

    public void setupAnim(TerrariaLivingEntityRenderState livingEntityRenderState) {
        super.setupAnim((T) livingEntityRenderState);
        this.full_eye.rotateBy(Axis.XN.rotationDegrees(livingEntityRenderState.xRot));
    }
}
