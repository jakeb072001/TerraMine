package terramine.client.render.entity.model.mobs.bosses;

import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.*;
import terramine.common.entity.mobs.BossEntityAI;

/**
 * Testing, remove later
 */
public class TestBossModel<T extends BossEntityAI> extends HumanoidModel<T> {

    public TestBossModel(ModelPart modelPart) {
        super(modelPart);
    }

    public void setupAnim(T monster, float f, float g, float h, float i, float j) {
        super.setupAnim(monster, f, g, h, i, j);
        AnimationUtils.animateZombieArms(this.leftArm, this.rightArm, this.isAggressive(), this.attackTime, h);
    }

    public static LayerDefinition createLayer() {
        return LayerDefinition.create(createMesh(new CubeDeformation(0.0F), 0), 64, 64);
    }

    public boolean isAggressive() {
        return true;
    }
}
