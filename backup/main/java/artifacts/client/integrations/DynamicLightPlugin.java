package terracraft.client.integrations;

import terracraft.common.init.Entities;
import dev.lambdaurora.lambdynlights.api.DynamicLightHandler;
import dev.lambdaurora.lambdynlights.api.DynamicLightHandlers;
import dev.lambdaurora.lambdynlights.api.DynamicLightsInitializer;

public class DynamicLightPlugin implements DynamicLightsInitializer {
    @Override
    public void onInitializeDynamicLights() {
        DynamicLightHandlers.registerDynamicLightHandler(Entities.FALLING_STAR, DynamicLightHandler.makeHandler(star -> 10, star -> false));
        DynamicLightHandlers.registerDynamicLightHandler(Entities.MAGIC_MISSILE, missile -> 8);
    }
}
