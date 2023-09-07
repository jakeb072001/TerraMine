package terramine.client.render.entity.renderer.blocks;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BrightnessCombiner;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.state.BlockState;
import terramine.common.block.chests.BaseChest;
import terramine.common.entity.block.ChestEntity;

@Environment(EnvType.CLIENT)
public class ChestEntityRenderer<T extends ChestEntity> extends ChestRenderer<T> {
    private static final String BOTTOM = "bottom";
    private static final String LID = "lid";
    private static final String LOCK = "lock";
    private final ModelPart lid;
    private final ModelPart bottom;
    private final ModelPart lock;

    public ChestEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(context);

        ModelPart modelPart = context.bakeLayer(ModelLayers.CHEST);
        this.bottom = modelPart.getChild(BOTTOM);
        this.lid = modelPart.getChild(LID);
        this.lock = modelPart.getChild(LOCK);
    }

    @Override
    public void render(T blockEntity, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j) {
        Level level = blockEntity.getLevel();
        boolean bl = level != null;
        BlockState blockState = bl ? blockEntity.getBlockState() : Blocks.CHEST.defaultBlockState().setValue(ChestBlock.FACING, Direction.SOUTH);
        Block block = blockState.getBlock();
        if (!(block instanceof ChestBlock)) {
            return;
        }
        BaseChest chestBlock = (BaseChest)block;
        poseStack.pushPose();
        float g = blockState.getValue(ChestBlock.FACING).toYRot();
        poseStack.translate(0.5, 0.5, 0.5);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(-g));
        poseStack.translate(-0.5, -0.5, -0.5);
        DoubleBlockCombiner.NeighborCombineResult<? extends ChestEntity> properties;
        properties = bl ? chestBlock.combine(blockState, level, blockEntity.getBlockPos(), true) : DoubleBlockCombiner.Combiner::acceptNone;
        float h = properties.apply(BaseChest.opennessCombiner(blockEntity)).get(f);
        h = 1.0f - h;
        h = 1.0f - h * h * h;
        int k = ((Int2IntFunction)properties.apply(new BrightnessCombiner())).applyAsInt(i);
        Material material = new Material(Sheets.CHEST_SHEET, chestBlock.getTexture());
        VertexConsumer vertexConsumer = material.buffer(multiBufferSource, RenderType::entityCutout);
        render(poseStack, vertexConsumer, this.lid, this.lock, this.bottom, h, k, j);
        poseStack.popPose();
    }

    private static void render(PoseStack poseStack, VertexConsumer vertexConsumer, ModelPart modelPart, ModelPart modelPart2, ModelPart modelPart3, float f, int i, int j) {
        modelPart2.xRot = modelPart.xRot = -(f * 1.5707964f);
        modelPart.render(poseStack, vertexConsumer, i, j);
        modelPart2.render(poseStack, vertexConsumer, i, j);
        modelPart3.render(poseStack, vertexConsumer, i, j);
    }
}
