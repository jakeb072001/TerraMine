package terramine.mixin.world;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.storage.WorldData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terramine.common.components.SyncedBooleanComponent;

// priority is one below Terrablender, fixes crash on world load on newer versions of Terrablender
@Mixin(value = MinecraftServer.class, priority = 994)
public class MinecraftServerMixin {

    @Shadow @Final protected WorldData worldData;

    @Inject(method = "<init>", at = @At("RETURN"), require = 1)
    private void onInit(CallbackInfo ci)
    {
        SyncedBooleanComponent.setLevelData(worldData.overworldData());
    }
}
