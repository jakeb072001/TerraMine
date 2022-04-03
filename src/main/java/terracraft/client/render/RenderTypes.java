package terracraft.client.render;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;


@Environment(EnvType.CLIENT)
public abstract class RenderTypes extends RenderType {

	public RenderTypes(String string, VertexFormat vertexFormat, VertexFormat.Mode mode, int i, boolean bl, boolean bl2, Runnable runnable, Runnable runnable2) {
		super(string, vertexFormat, mode, i, bl, bl2, runnable, runnable2);
		throw new IllegalStateException("This class must not be instantiated");
	}

	// See ForgeRenderTypes#getUnlitTranslucent
	private static final Function<ResourceLocation, RenderType> UNLIT_TRANSLUCENT = Util.memoize(Util.memoize(textureLocation -> {
		CompositeState renderState = CompositeState.builder()
				// TODO (from forge): .setShaderState(RENDERTYPE_ENTITY_TRANSLUCENT_UNLIT_SHADER)
				.setShaderState(RENDERTYPE_ENTITY_CUTOUT_NO_CULL_SHADER)
				.setTextureState(new TextureStateShard(textureLocation, false, false))
				.setTransparencyState(TRANSLUCENT_TRANSPARENCY)
				.setCullState(NO_CULL)
				.setLightmapState(LIGHTMAP)
				.setOverlayState(OVERLAY)
				.createCompositeState(true);
		return create("terracraft_entity_unlit", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, true, true, renderState);
	}));

	public static RenderType unlit(ResourceLocation textureLocation) {
		return UNLIT_TRANSLUCENT.apply(textureLocation);
	}
}
