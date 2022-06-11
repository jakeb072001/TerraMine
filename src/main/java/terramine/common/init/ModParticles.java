package terramine.common.init;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.PlayerCloudParticle;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import terramine.TerraMine;

public class ModParticles {

    public static final SimpleParticleType BLUE_POOF = FabricParticleTypes.simple();
    public static final SimpleParticleType GREEN_SPARK = FabricParticleTypes.simple();

    public static void registerServer() {
        Registry.register(Registry.PARTICLE_TYPE, TerraMine.id("blue_poof"), BLUE_POOF);
        Registry.register(Registry.PARTICLE_TYPE, TerraMine.id("green_spark"), GREEN_SPARK);
    }

    @Environment(EnvType.CLIENT)
    public static void registerClient() {
        ParticleFactoryRegistry.getInstance().register(BLUE_POOF, PlayerCloudParticle.Provider::new);
        ParticleFactoryRegistry.getInstance().register(GREEN_SPARK, FlameParticle.Provider::new);
    }
}
