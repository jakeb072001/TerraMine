package terramine.client.render;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.TntRenderer;
import terramine.TerraMine;
import terramine.client.render.entity.*;
import terramine.client.render.entity.devourer.DevourerBodyRenderer;
import terramine.client.render.entity.devourer.DevourerHeadRenderer;
import terramine.client.render.entity.devourer.DevourerTailRenderer;
import terramine.common.init.ModBlockEntityType;
import terramine.common.init.ModBlocks;
import terramine.common.init.ModEntities;

public class ModEntityRenderers {

    public static void register() {
        // Block RenderLayer
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CORRUPTED_GLASS, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CORRUPTED_ICE, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CRIMSON_GLASS, RenderType.translucent());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CRIMSON_ICE, RenderType.translucent());

        // Block Entity Renderer
        BlockEntityRendererRegistry.register(ModBlockEntityType.GOLD_CHEST, ChestEntityRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntityType.FROZEN_CHEST, ChestEntityRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntityType.IVY_CHEST, ChestEntityRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntityType.SANDSTONE_CHEST, ChestEntityRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntityType.WATER_CHEST, ChestEntityRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntityType.SKYWARE_CHEST, ChestEntityRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntityType.SHADOW_CHEST, ChestEntityRenderer::new);
        registerTextures();

        // Entity Renderers
        EntityRendererRegistry.register(ModEntities.MIMIC, MimicRenderer::new);
        EntityRendererRegistry.register(ModEntities.DEMON_EYE, DemonEyeRenderer::new);
        EntityRendererRegistry.register(ModEntities.EATER_OF_SOULS, EaterOfSoulsRenderer::new);
        EntityRendererRegistry.register(ModEntities.DEVOURER, DevourerHeadRenderer::new);
        EntityRendererRegistry.register(ModEntities.DEVOURER_BODY, DevourerBodyRenderer::new);
        EntityRendererRegistry.register(ModEntities.DEVOURER_TAIL, DevourerTailRenderer::new);
        EntityRendererRegistry.register(ModEntities.CRIMERA, CrimeraRenderer::new);
        EntityRendererRegistry.register(ModEntities.FALLING_STAR, FallingStarRenderer::new);
        EntityRendererRegistry.register(ModEntities.MAGIC_MISSILE, MagicMissileRenderer::new);
        EntityRendererRegistry.register(ModEntities.FLAMELASH_MISSILE, FlamelashMissileRenderer::new);
        EntityRendererRegistry.register(ModEntities.RAINBOW_MISSILE, RainbowMissileRenderer::new);
        EntityRendererRegistry.register(ModEntities.INSTANT_TNT, TntRenderer::new);
    }

    private static void registerTextures() {
        ClientSpriteRegistryCallback.event(Sheets.CHEST_SHEET).register((texture, registry) -> {
            registry.register(TerraMine.id("block/chests/gold/gold_chest"));
            registry.register(TerraMine.id("block/chests/frozen/frozen_chest"));
            registry.register(TerraMine.id("block/chests/ivy/ivy_chest"));
            registry.register(TerraMine.id("block/chests/sandstone/sandstone_chest"));
            registry.register(TerraMine.id("block/chests/water/water_chest"));
            registry.register(TerraMine.id("block/chests/skyware/skyware_chest"));
            registry.register(TerraMine.id("block/chests/shadow/shadow_chest"));
            registry.register(TerraMine.id("block/chests/player/piggy_bank/piggy_bank"));
            registry.register(TerraMine.id("block/chests/player/safe/safe"));
        });
    }
}
