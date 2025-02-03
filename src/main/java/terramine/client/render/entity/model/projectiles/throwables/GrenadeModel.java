package terramine.client.render.entity.model.projectiles.throwables;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import terramine.client.render.entity.states.TerrariaEntityRenderState;

public class GrenadeModel extends EntityModel<TerrariaEntityRenderState> {
    protected final ModelPart grenade;

    public GrenadeModel(ModelPart root) {
        super(root);
        this.grenade = root.getChild("grenade");
    }

    public static LayerDefinition createLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        partDefinition.addOrReplaceChild("grenade", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 9).mirror().addBox(-1.0F, -4.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(8, 11).mirror().addBox(-1.5F, -3.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offset(0.0F, -2.0F, 0.0F));

        return LayerDefinition.create(meshDefinition, 16, 16);
    }
}
