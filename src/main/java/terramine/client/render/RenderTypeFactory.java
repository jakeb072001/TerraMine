package terramine.client.render;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

@FunctionalInterface
public interface RenderTypeFactory extends Function<ResourceLocation, RenderType> {
}
