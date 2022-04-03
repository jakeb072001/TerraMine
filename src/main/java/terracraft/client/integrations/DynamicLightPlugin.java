package terracraft.client.integrations;

import dev.lambdaurora.lambdynlights.api.DynamicLightHandler;
import dev.lambdaurora.lambdynlights.api.DynamicLightHandlers;
import dev.lambdaurora.lambdynlights.api.DynamicLightsInitializer;
import terracraft.common.init.ModEntities;

public class DynamicLightPlugin implements DynamicLightsInitializer {
    @Override
    public void onInitializeDynamicLights() {
        DynamicLightHandlers.registerDynamicLightHandler(ModEntities.FALLING_STAR, DynamicLightHandler.makeHandler(star -> 10, star -> false));
        DynamicLightHandlers.registerDynamicLightHandler(ModEntities.MAGIC_MISSILE, missile -> 8);
    }
}
