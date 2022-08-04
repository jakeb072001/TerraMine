package terramine.mixin.world;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.storage.WorldData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terramine.common.components.SyncedBooleanComponent;
import terramine.common.init.ModComponents;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {

    @Shadow @Final protected WorldData worldData;

    @Inject(at = @At("HEAD"), method = "runServer")
    public void getWorldLevelData(CallbackInfo ci) {
        SyncedBooleanComponent.setLevelData(worldData.overworldData());
    }
}
