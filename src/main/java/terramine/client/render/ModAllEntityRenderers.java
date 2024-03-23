package terramine.client.render;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.TntRenderer;
import terramine.client.render.entity.renderer.blocks.ChestEntityRenderer;
import terramine.client.render.entity.renderer.misc.ClientItemEntityRenderer;
import terramine.client.render.entity.renderer.misc.FallingStarRenderer;
import terramine.client.render.entity.renderer.misc.MeteoriteRenderer;
import terramine.client.render.entity.renderer.mobs.bosses.TestBossRenderer;
import terramine.client.render.entity.renderer.mobs.prehardmode.CrimeraRenderer;
import terramine.client.render.entity.renderer.mobs.prehardmode.DemonEyeRenderer;
import terramine.client.render.entity.renderer.mobs.prehardmode.EaterOfSoulsRenderer;
import terramine.client.render.entity.renderer.mobs.hardmode.MimicRenderer;
import terramine.client.render.entity.renderer.mobs.prehardmode.devourer.DevourerBodyRenderer;
import terramine.client.render.entity.renderer.mobs.prehardmode.devourer.DevourerHeadRenderer;
import terramine.client.render.entity.renderer.mobs.prehardmode.devourer.DevourerTailRenderer;
import terramine.client.render.entity.renderer.projectiles.magic.FlamelashMissileRenderer;
import terramine.client.render.entity.renderer.projectiles.magic.LaserRenderer;
import terramine.client.render.entity.renderer.projectiles.magic.MagicMissileRenderer;
import terramine.client.render.entity.renderer.projectiles.magic.RainbowMissileRenderer;
import terramine.client.render.entity.renderer.projectiles.throwables.BombRenderer;
import terramine.client.render.entity.renderer.projectiles.throwables.DynamiteRenderer;
import terramine.client.render.entity.renderer.projectiles.throwables.GrenadeRenderer;
import terramine.common.init.ModBlockEntityType;
import terramine.common.init.ModBlocks;
import terramine.common.init.ModEntities;

public class ModAllEntityRenderers {

    public static void register() {
        // Block RenderLayer
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CORRUPTED_GLASS, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CORRUPTED_ICE, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CRIMSON_GLASS, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CRIMSON_ICE, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CLOUD, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RAIN_CLOUD, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.VILE_MUSHROOM, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_VILE_MUSHROOM, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.VICIOUS_MUSHROOM, RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTTED_VICIOUS_MUSHROOM, RenderType.cutout());

        // Block Entity Renderer
        BlockEntityRendererRegistry.register(ModBlockEntityType.GOLD_CHEST, ChestEntityRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntityType.FROZEN_CHEST, ChestEntityRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntityType.IVY_CHEST, ChestEntityRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntityType.SANDSTONE_CHEST, ChestEntityRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntityType.WATER_CHEST, ChestEntityRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntityType.SKYWARE_CHEST, ChestEntityRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntityType.SHADOW_CHEST, ChestEntityRenderer::new);

        // Entity Renderers
        EntityRendererRegistry.register(ModEntities.MIMIC, MimicRenderer::new);
        EntityRendererRegistry.register(ModEntities.DEMON_EYE, DemonEyeRenderer::new);
        EntityRendererRegistry.register(ModEntities.EATER_OF_SOULS, EaterOfSoulsRenderer::new);
        EntityRendererRegistry.register(ModEntities.DEVOURER, DevourerHeadRenderer::new);
        EntityRendererRegistry.register(ModEntities.DEVOURER_BODY, DevourerBodyRenderer::new);
        EntityRendererRegistry.register(ModEntities.DEVOURER_TAIL, DevourerTailRenderer::new);
        EntityRendererRegistry.register(ModEntities.CRIMERA, CrimeraRenderer::new);
        /**
         * Testing, remove later
         */
        EntityRendererRegistry.register(ModEntities.TEST_BOSS, TestBossRenderer::new);

        EntityRendererRegistry.register(ModEntities.CLIENT_ITEM, ClientItemEntityRenderer::new);

        EntityRendererRegistry.register(ModEntities.FALLING_STAR, FallingStarRenderer::new);
        EntityRendererRegistry.register(ModEntities.METEORITE, MeteoriteRenderer::new);
        EntityRendererRegistry.register(ModEntities.MAGIC_MISSILE, MagicMissileRenderer::new);
        EntityRendererRegistry.register(ModEntities.FLAMELASH_MISSILE, FlamelashMissileRenderer::new);
        EntityRendererRegistry.register(ModEntities.RAINBOW_MISSILE, RainbowMissileRenderer::new);
        EntityRendererRegistry.register(ModEntities.LASER, LaserRenderer::new);
        EntityRendererRegistry.register(ModEntities.DYNAMITE, DynamiteRenderer::new);
        EntityRendererRegistry.register(ModEntities.GRENADE, GrenadeRenderer::new);
        EntityRendererRegistry.register(ModEntities.BOMB, BombRenderer::new);
        EntityRendererRegistry.register(ModEntities.INSTANT_TNT, TntRenderer::new);
    }
}
