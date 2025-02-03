package terramine.client.render.entity.model.misc;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import terramine.client.render.entity.states.TerrariaEntityRenderState;

public class MeteoriteModel<T extends TerrariaEntityRenderState> extends EntityModel<T> {

    protected final ModelPart meteorite;

    public MeteoriteModel(ModelPart part) {
        super(part);
        meteorite = part.getChild("meteorite");
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
