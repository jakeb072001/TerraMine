package terramine.client.render.entity.model.mobs.bosses;

import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;

/**
 * Testing, remove later
 */
public class TestBossModel<T extends ZombieRenderState> extends HumanoidModel<T> {

    public TestBossModel(ModelPart modelPart) {
        super(modelPart);
    }

    @Override
    public void setupAnim(T monster) {
        super.setupAnim(monster);
        AnimationUtils.animateZombieArms(this.leftArm, this.rightArm, this.isAggressive(), monster.attackTime, monster.ageInTicks);
    }

    public static LayerDefinition createLayer() {
        return LayerDefinition.create(createMesh(new CubeDeformation(0.0F), 0), 64, 64);
    }

    public boolean isAggressive() {
        return true;
    }
}
