package terramine.mixin.client;

import net.minecraft.client.gui.screens.worldselection.WorldOpenFlows;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.ReloadableServerResources;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.WorldData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terramine.common.init.ModComponents;

@Mixin(WorldOpenFlows.class) // todo: get working on server side, doesn't cause issues on server but servers will only ever have corruption (unless the world is created on client and moved to server)
public class WorldOpenFlowsMixin {

    @Unique
    private final RandomSource random = RandomSource.create();

    /**
     * Randomly sets the world evil type to true or false, false being corruption and true being crimson.
     * This method is what runs during world creation so this should only happen then and should not re-roll the evil type at any point.
     * Additionally, the random uses the world seed so that the evil type should match another world with the same seed.
     */
    @Inject(at = @At("HEAD"), method = "createLevelFromExistingSettings")
    public void randomEvilType(LevelStorageSource.LevelStorageAccess levelStorageAccess, ReloadableServerResources reloadableServerResources, RegistryAccess.Frozen frozen, WorldData worldData, CallbackInfo ci) {
        random.setSeed(worldData.worldGenSettings().seed());
        ModComponents.EVIL_TYPE.get(worldData.overworldData()).set(random.nextBoolean());
    }
}
