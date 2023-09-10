package terramine.common.init;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.PlayerCloudParticle;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import terramine.TerraMine;

public class ModParticles {

    public static final SimpleParticleType BLUE_POOF = registerServer(TerraMine.id("blue_poof"), FabricParticleTypes.simple());
    public static final SimpleParticleType GREEN_SPARK = registerServer(TerraMine.id("green_spark"), FabricParticleTypes.simple());

    public static SimpleParticleType registerServer(ResourceLocation name, SimpleParticleType type) {
        return Registry.register(BuiltInRegistries.PARTICLE_TYPE, name, type);
    }

    @Environment(EnvType.CLIENT)
    public static void registerClient() {
        ParticleFactoryRegistry.getInstance().register(BLUE_POOF, PlayerCloudParticle.Provider::new);
        ParticleFactoryRegistry.getInstance().register(GREEN_SPARK, FlameParticle.Provider::new);
    }
}
