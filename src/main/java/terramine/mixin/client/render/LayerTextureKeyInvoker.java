package terramine.mixin.client.render;

import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(EquipmentLayerRenderer.LayerTextureKey.class)
public interface LayerTextureKeyInvoker {
    @Invoker("<init>")
    static EquipmentLayerRenderer.LayerTextureKey create(EquipmentClientInfo.LayerType layerType, EquipmentClientInfo.Layer layer) {
        throw new AssertionError();
    }
}
