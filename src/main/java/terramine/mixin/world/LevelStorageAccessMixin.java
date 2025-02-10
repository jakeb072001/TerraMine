package terramine.mixin.world;

import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.WorldData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import terramine.common.init.ModComponents;

@Mixin(LevelStorageSource.LevelStorageAccess.class)
public class LevelStorageAccessMixin {

    @Unique
    private final RandomSource random = RandomSource.create();

    /**
     * Randomly sets the world evil type to true or false, false being corruption and true being crimson.
     * Additionally, the random uses the world seed so that the evil type should match another world with the same seed.
     */
    @Inject(at = @At("HEAD"), method = "saveDataTag(Lnet/minecraft/core/RegistryAccess;Lnet/minecraft/world/level/storage/WorldData;Lnet/minecraft/nbt/CompoundTag;)V")
    public void randomEvilType(RegistryAccess registryAccess, WorldData worldData, CompoundTag compoundTag, CallbackInfo ci) {
        random.setSeed(worldData.worldGenOptions().seed());
        ModComponents.EVIL_TYPE.get(worldData.overworldData()).set(random.nextBoolean());
        ModComponents.ORE_TYPES.get(worldData.overworldData()).setIsCopper(random.nextBoolean());
        ModComponents.ORE_TYPES.get(worldData.overworldData()).setIsIron(random.nextBoolean());
        ModComponents.ORE_TYPES.get(worldData.overworldData()).setIsSilver(random.nextBoolean());
        ModComponents.ORE_TYPES.get(worldData.overworldData()).setIsGold(random.nextBoolean());
        ModComponents.ORE_TYPES.get(worldData.overworldData()).setIsCobalt(random.nextBoolean());
        ModComponents.ORE_TYPES.get(worldData.overworldData()).setIsMythril(random.nextBoolean());
        ModComponents.ORE_TYPES.get(worldData.overworldData()).setIsAdamantite(random.nextBoolean());
    }
}
