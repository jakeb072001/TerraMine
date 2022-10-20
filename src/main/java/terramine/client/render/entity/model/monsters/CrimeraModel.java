package terramine.client.render.entity.model.monsters;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import terramine.common.entity.CrimeraEntity;

public class CrimeraModel<T extends CrimeraEntity> extends HierarchicalModel<T> {

    protected final ModelPart Crimera;
    protected final ModelPart front_body;
    protected final ModelPart front_tendrils;
    protected final ModelPart left_front;
    protected final ModelPart cube_r1;
    protected final ModelPart cube_r2;
    protected final ModelPart right_front;
    protected final ModelPart cube_r3;
    protected final ModelPart cube_r4;
    protected final ModelPart inner_teeth;
    protected final ModelPart left_claw;
    protected final ModelPart cube_r5;
    protected final ModelPart cube_r6;
    protected final ModelPart right_claw;
    protected final ModelPart cube_r7;
    protected final ModelPart cube_r8;
    protected final ModelPart middle_body;
    protected final ModelPart middle_tendrils;
    protected final ModelPart left_middle_1;
    protected final ModelPart cube_r9;
    protected final ModelPart left_middle_2;
    protected final ModelPart cube_r10;
    protected final ModelPart right_middle_1;
    protected final ModelPart cube_r11;
    protected final ModelPart right_middle_2;
    protected final ModelPart cube_r12;
    protected final ModelPart back_body;
    protected final ModelPart back_tendrils;
    protected final ModelPart center_back;
    protected final ModelPart left_back;
    protected final ModelPart cube_r13;
    protected final ModelPart right_back;
    protected final ModelPart cube_r14;

    public CrimeraModel(ModelPart part) {
        super(RenderType::entityCutout);
        Crimera = part.getChild("Crimera");
        front_body = Crimera.getChild("front_body");
        front_tendrils = front_body.getChild("front_tendrils");
        left_front = front_tendrils.getChild("left_front");
        right_front = front_tendrils.getChild("right_front");
        inner_teeth = front_body.getChild("inner_teeth");
        left_claw = front_body.getChild("left_claw");
        right_claw = front_body.getChild("right_claw");
        middle_body = Crimera.getChild("middle_body");
        middle_tendrils = middle_body.getChild("middle_tendrils");
        left_middle_1 = middle_tendrils.getChild("left_middle_1");
        left_middle_2 = middle_tendrils.getChild("left_middle_2");
        right_middle_1 = middle_tendrils.getChild("right_middle_1");
        right_middle_2 = middle_tendrils.getChild("right_middle_2");
        back_body = Crimera.getChild("back_body");
        back_tendrils = back_body.getChild("back_tendrils");
        center_back = back_tendrils.getChild("center_back");
        left_back = back_tendrils.getChild("left_back");
        right_back = back_tendrils.getChild("right_back");
        cube_r1 = left_front.getChild("cube_r1");
        cube_r2 = left_front.getChild("cube_r2");
        cube_r3 = right_front.getChild("cube_r3");
        cube_r4 = right_front.getChild("cube_r4");
        cube_r5 = left_claw.getChild("cube_r5");
        cube_r6 = left_claw.getChild("cube_r6");
        cube_r7 = right_claw.getChild("cube_r7");
        cube_r8 = right_claw.getChild("cube_r8");
        cube_r9 = left_middle_1.getChild("cube_r9");
        cube_r10 = left_middle_2.getChild("cube_r10");
        cube_r11 = right_middle_1.getChild("cube_r11");
        cube_r12 = right_middle_2.getChild("cube_r12");
        cube_r13 = left_back.getChild("cube_r13");
        cube_r14 = right_back.getChild("cube_r14");
    }

    @Override
    public void setupAnim(@NotNull CrimeraEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float k = ((entity.getId() * 3) + ageInTicks) * 4f * ((float)Math.PI / 180);
        float f = k * 2;
        float xMod = 8.0f;
        float yMod = 4.0f;

        left_front.xRot = Mth.cos(k) * xMod * ((float)Math.PI / 180);
        right_front.xRot = Mth.cos(k) * xMod * ((float)Math.PI / 180);
        middle_tendrils.xRot = Mth.cos(k) * xMod * ((float)Math.PI / 180);
        back_tendrils.xRot = Mth.cos(k) * xMod * ((float)Math.PI / 180);

        cube_r1.yRot = 0.5f + Mth.cos(f) * yMod * ((float)Math.PI / 270);
        cube_r2.yRot = -cube_r4.yRot;
        cube_r3.yRot = -cube_r1.yRot;
        cube_r4.yRot = -0.3f + Mth.cos(f) * yMod * ((float)Math.PI / 270);
        cube_r9.yRot = 0.30f + Mth.cos(f) * yMod * ((float)Math.PI / 270);
        cube_r10.yRot = -cube_r12.yRot;
        cube_r11.yRot = -cube_r9.yRot;
        cube_r12.yRot = -0.15f + Mth.cos(f) * yMod * ((float)Math.PI / 270);
        cube_r13.yRot = 0.15f + Mth.cos(f) * yMod * ((float)Math.PI / 270);
        cube_r14.yRot = -cube_r13.yRot;
        center_back.yRot = Mth.cos(f) * yMod * ((float)Math.PI / 270);
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack matrixStack, @NotNull VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Crimera.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return Crimera;
    }

    public static LayerDefinition createLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        PartDefinition Crimera = partDefinition.addOrReplaceChild(
                "Crimera",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 22.0F, 0.0F));

        PartDefinition front_body = Crimera.addOrReplaceChild(
                "front_body",
                CubeListBuilder.create()
                        .texOffs(0, 0)
                        .addBox(-7.0F, -4.0F, -4.0F, 12.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -2.0F, -3.0F));

        PartDefinition front_tendrils = front_body.addOrReplaceChild(
                "front_tendrils",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 3.0F));

        PartDefinition left_front = front_tendrils.addOrReplaceChild(
                "left_front",
                CubeListBuilder.create(),
                PartPose.offset(6.0F, 0.0F, -1.0F));

        left_front.addOrReplaceChild(
                "cube_r1",
                CubeListBuilder.create()
                        .texOffs(0, 16)
                        .addBox(7.0F, -2.5F, -1.0F, 1.0F, 0.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(19, 31)
                        .addBox(7.5F, -3.0F, -1.0F, 0.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-8.0F, 2.0F, 1.0F, 0.0F, 0.4363F, 0.0F));

        left_front.addOrReplaceChild(
                "cube_r2",
                CubeListBuilder.create()
                        .texOffs(15, 30)
                        .addBox(4.5F, -3.0F, 4.0F, 0.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                        .texOffs(22, 16)
                        .addBox(4.0F, -2.5F, 4.0F, 1.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-8.0F, 2.0F, 1.0F, 0.0F, 0.6109F, 0.0F));

        PartDefinition right_front = front_tendrils.addOrReplaceChild(
                "right_front",
                CubeListBuilder.create(),
                PartPose.offset(-6.0F, 0.0F, -1.0F));

        right_front.addOrReplaceChild(
                "cube_r3",
                CubeListBuilder.create()
                        .texOffs(-5, 0)
                        .addBox(-8.0F, -2.5F, -1.0F, 1.0F, 0.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(19, 29)
                        .addBox(-7.5F, -3.0F, -1.0F, 0.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(6.0F, 2.0F, 1.0F, 0.0F, -0.4363F, 0.0F));

        right_front.addOrReplaceChild(
                "cube_r4",
                CubeListBuilder.create()
                        .texOffs(15, 28)
                        .addBox(-4.5F, -3.0F, 3.0F, 0.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                        .texOffs(20, 17)
                        .addBox(-5.0F, -2.5F, 3.0F, 1.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(6.0F, 2.0F, 1.0F, 0.0F, -0.6109F, 0.0F));

        front_body.addOrReplaceChild(
                "inner_teeth",
                CubeListBuilder.create()
                        .texOffs(40, 0)
                        .addBox(-4.0F, 0.0F, 0.0F, 6.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(19, 29)
                        .addBox(-1.5F, 0.5F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(19, 29)
                        .addBox(-3.0F, 2.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(19, 29)
                        .addBox(0.0F, 2.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -2.0F, -5.0F));

        PartDefinition left_claw = front_body.addOrReplaceChild(
                "left_claw",
                CubeListBuilder.create(),
                PartPose.offset(4.0F, 1.0F, -4.0F));

        left_claw.addOrReplaceChild(
                "cube_r5",
                CubeListBuilder.create()
                        .texOffs(14, 41)
                        .addBox(-1.5F, -1.5F, -4.0F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, -7.0F, 0.0F, -0.2618F, 0.0F));

        left_claw.addOrReplaceChild(
                "cube_r6",
                CubeListBuilder.create()
                        .texOffs(2, 31)
                        .addBox(5.0F, -3.0F, -13.0F, 3.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-6.0F, 1.0F, 7.0F, 0.0F, 0.0873F, 0.0F));

        PartDefinition right_claw = front_body.addOrReplaceChild(
                "right_claw",
                CubeListBuilder.create(),
                PartPose.offset(-6.0F, 1.0F, -4.0F));

        right_claw.addOrReplaceChild(
                "cube_r7",
                CubeListBuilder.create()
                        .texOffs(0, 41)
                        .addBox(-0.5F, -1.5F, -4.0F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, -7.0F, 0.0F, 0.2618F, 0.0F));

        right_claw.addOrReplaceChild(
                "cube_r8",
                CubeListBuilder.create()
                        .texOffs(37, 9)
                        .addBox(-8.0F, -3.0F, -13.0F, 3.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(6.0F, 1.0F, 7.0F, 0.0F, -0.0873F, 0.0F));

        PartDefinition middle_body = Crimera.addOrReplaceChild(
                "middle_body",
                CubeListBuilder.create()
                        .texOffs(0, 16)
                        .addBox(-5.0F, -3.0F, -3.0F, 8.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -2.0F, 4.0F));

        PartDefinition middle_tendrils = middle_body.addOrReplaceChild(
                "middle_tendrils",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 5.0F));

        PartDefinition left_middle_1 = middle_tendrils.addOrReplaceChild(
                "left_middle_1",
                CubeListBuilder.create(),
                PartPose.offset(4.0F, 0.0F, -1.0F));

        left_middle_1.addOrReplaceChild(
                "cube_r9",
                CubeListBuilder.create()
                        .texOffs(35, 15)
                        .addBox(2.5F, -3.0F, 8.0F, 0.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 29)
                        .addBox(2.0F, -2.5F, 8.0F, 1.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-6.0F, 2.0F, -8.0F, 0.0F, 0.1745F, 0.0F));

        PartDefinition left_middle_2 = middle_tendrils.addOrReplaceChild(
                "left_middle_2",
                CubeListBuilder.create(),
                PartPose.offset(5.0F, 0.0F, -4.0F));

        left_middle_2.addOrReplaceChild(
                "cube_r10",
                CubeListBuilder.create()
                        .texOffs(35, 14)
                        .addBox(2.5F, -3.0F, 6.0F, 0.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                        .texOffs(29, 0)
                        .addBox(2.0F, -2.5F, 6.0F, 1.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-7.0F, 2.0F, -5.0F, 0.0F, 0.3927F, 0.0F));

        PartDefinition right_middle_1 = middle_tendrils.addOrReplaceChild(
                "right_middle_1",
                CubeListBuilder.create(),
                PartPose.offset(-4.0F, 0.0F, -1.0F));

        right_middle_1.addOrReplaceChild(
                "cube_r11",
                CubeListBuilder.create()
                        .texOffs(35, 13)
                        .addBox(-2.5F, -3.0F, 8.0F, 0.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                        .texOffs(26, 16)
                        .addBox(-3.0F, -2.5F, 8.0F, 1.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(4.0F, 2.0F, -8.0F, 0.0F, -0.1745F, 0.0F));

        PartDefinition right_middle_2 = middle_tendrils.addOrReplaceChild(
                "right_middle_2",
                CubeListBuilder.create(),
                PartPose.offset(-5.0F, 0.0F, -4.0F));

        right_middle_2.addOrReplaceChild(
                "cube_r12",
                CubeListBuilder.create()
                        .texOffs(34, 16)
                        .addBox(-2.5F, -3.0F, 6.0F, 0.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                        .texOffs(24, 16)
                        .addBox(-3.0F, -2.5F, 6.0F, 1.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(5.0F, 2.0F, -5.0F, 0.0F, -0.3927F, 0.0F));

        PartDefinition back_body = Crimera.addOrReplaceChild(
                "back_body",
                CubeListBuilder.create()
                        .texOffs(28, 24)
                        .addBox(-4.0F, -2.0F, -3.0F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                        .texOffs(23, 34)
                        .addBox(-2.0F, -1.0F, 0.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -2.0F, 9.0F));

        PartDefinition back_tendrils = back_body.addOrReplaceChild(
                "back_tendrils",
                CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 3.0F));

        back_tendrils.addOrReplaceChild(
                "center_back",
                CubeListBuilder.create()
                        .texOffs(36, -2)
                        .addBox(-1.0F, -1.0F, 0.0F, 0.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                        .texOffs(10, 31)
                        .addBox(-1.5F, -0.5F, 0.0F, 1.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 3.0F));

        PartDefinition left_back = back_tendrils.addOrReplaceChild(
                "left_back",
                CubeListBuilder.create(),
                PartPose.offset(3.0F, 0.0F, 0.0F));

        left_back.addOrReplaceChild(
                "cube_r13",
                CubeListBuilder.create()
                        .texOffs(36, -2)
                        .addBox(0.5F, -3.0F, 12.0F, 0.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                        .texOffs(8, 31)
                        .addBox(0.0F, -2.5F, 12.0F, 1.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-5.0F, 2.0F, -12.0F, 0.0F, 0.1745F, 0.0F));

        PartDefinition right_back = back_tendrils.addOrReplaceChild(
                "right_back",
                CubeListBuilder.create(),
                PartPose.offset(-3.0F, 0.0F, 0.0F));

        right_back.addOrReplaceChild(
                "cube_r14",
                CubeListBuilder.create()
                        .texOffs(34, 16)
                        .addBox(-0.5F, -3.0F, 12.0F, 0.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                        .texOffs(31, 0)
                        .addBox(-1.0F, -2.5F, 12.0F, 1.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(3.0F, 2.0F, -12.0F, 0.0F, -0.1745F, 0.0F));

        return LayerDefinition.create(meshDefinition, 64, 64);
    }
}
