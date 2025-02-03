package terramine.client.render.entity.model.projectiles.magic;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import terramine.client.render.entity.states.TerrariaEntityRenderState;

public class LaserModel<T extends TerrariaEntityRenderState> extends EntityModel<T> {

    protected final ModelPart laser;

    public LaserModel(ModelPart part) {
        super(part);
        laser = part.getChild("laser");
    }

    public static LayerDefinition createLayer() {
        MeshDefinition partDefinition = new MeshDefinition();

        partDefinition.getRoot().addOrReplaceChild(
                "laser",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-1.0F, -2.0F, -8.0F, 2.0F, 2.0F, 16.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -1.5F, 0.0F));

        return LayerDefinition.create(partDefinition, 16, 16);
    }
}
