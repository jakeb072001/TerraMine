package terramine.mixin.compat.origins;

/**
@Mixin(value = ConditionFactory.class, remap = false)
public interface ConditionFactoryAccessor<T> {

	@Accessor
	BiFunction<SerializableData.Instance, T, Boolean> getCondition();

	@Accessor
	void setCondition(BiFunction<SerializableData.Instance, T, Boolean> condition);
}
**/