package terramine.client.render.entity.model.projectiles.throwables;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import terramine.client.render.entity.states.TerrariaEntityRenderState;

public class BombModel extends EntityModel<TerrariaEntityRenderState> {
    protected final ModelPart bomb;

    public BombModel(ModelPart root) {
        super(root);
        this.bomb = root.getChild("bomb");
    }

    public static LayerDefinition createLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        partDefinition.addOrReplaceChild("bomb", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(8, 12).addBox(-2.0F, 2.5F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 12).addBox(-2.0F, -3.5F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(18, 0).addBox(-2.0F, -2.0F, -3.5F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(16, 16).addBox(-2.0F, -2.0F, 2.5F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(8, 12).addBox(2.5F, -2.0F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 12).addBox(-3.5F, -2.0F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-0.5F, -5.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 3).addBox(0.5F, -6.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -5.0F, 0.0F));

        return LayerDefinition.create(meshDefinition, 32, 32);
    }
}
