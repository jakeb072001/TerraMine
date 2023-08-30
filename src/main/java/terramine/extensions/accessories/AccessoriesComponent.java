package terramine.extensions.accessories;

import com.google.common.collect.Multimap;
import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import terramine.common.misc.TerrariaInventory;

import java.util.Map;

public interface AccessoriesComponent extends ComponentV3 {
    Player getEntity();

    Map<String, Map<String, TerrariaInventory>> getInventory();

    void addTemporaryModifiers(Multimap<String, AttributeModifier> modifiers);

    void removeModifiers(Multimap<String, AttributeModifier> modifiers);
}
