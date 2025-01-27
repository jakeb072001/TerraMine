package terramine.common.compat;

public class OriginsCompat implements CompatHandler {

	@Override
	public void run() {
		/**
		RegistryEntryAddedCallback.event(ApoliRegistries.ENTITY_CONDITION).register((rawId, id, conditionFactory) -> {
			// Held-up umbrella blocks origins:exposed_to_sun condition
			if (conditionFactory.getSerializerId().equals(ResourceLocation.fromNamespaceAndPath(Origins.MODID, "exposed_to_sun"))) {
				//noinspection unchecked
				ConditionFactoryAccessor<LivingEntity> conditionAccess = (ConditionFactoryAccessor<LivingEntity>) conditionFactory;

				// Wrapper around original condition
				conditionAccess.setCondition((instance, entity) -> conditionAccess.getCondition().apply(instance, entity)
						&& !UmbrellaItem.isHeldUpInEitherHand(entity));
			}
		});
		 **/
	}

	@Override
	public String modId() {
		return "origins";
	}
}
