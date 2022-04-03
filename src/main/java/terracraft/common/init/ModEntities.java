package terracraft.common.init;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import terracraft.TerraCraft;
import terracraft.common.entity.DemonEyeEntity;
import terracraft.common.entity.FallingStarEntity;
import terracraft.common.entity.MagicMissileEntity;
import terracraft.common.entity.MimicEntity;

public class ModEntities {

	public static final EntityType<MimicEntity> MIMIC = Registry.register( Registry.ENTITY_TYPE, TerraCraft.id("mimic"),
			FabricEntityTypeBuilder.create(MobCategory.MISC, MimicEntity::new)
					.dimensions(EntityDimensions.fixed(14 / 16F, 14 / 16F))
					.trackRangeBlocks(64)
					.build());

	public static final EntityType<DemonEyeEntity> DEMON_EYE = Registry.register( Registry.ENTITY_TYPE, TerraCraft.id("demon_eye"),
			FabricEntityTypeBuilder.create(MobCategory.MISC, DemonEyeEntity::new)
					.dimensions(EntityDimensions.fixed(0.25f, 0.25f))
					.spawnGroup(MobCategory.MONSTER)
					.build());

	public static final EntityType<FallingStarEntity> FALLING_STAR = Registry.register( Registry.ENTITY_TYPE, TerraCraft.id("falling_star"),
			FabricEntityTypeBuilder.create(MobCategory.MISC, FallingStarEntity::new)
					.dimensions(EntityDimensions.fixed(0.25f, 0.25f))
					.build());

	public static final EntityType<MagicMissileEntity> MAGIC_MISSILE = Registry.register( Registry.ENTITY_TYPE, TerraCraft.id("magic_missile"),
			FabricEntityTypeBuilder.create(MobCategory.MISC, MagicMissileEntity::new)
					.dimensions(EntityDimensions.fixed(0.25f, 0.25f))
					.build());

	static {
		// Register mob attributes
		FabricDefaultAttributeRegistry.register(MIMIC, MimicEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(DEMON_EYE, DemonEyeEntity.createMobAttributes());
		FabricDefaultAttributeRegistry.register(FALLING_STAR, FallingStarEntity.createMobAttributes());
	}

	private ModEntities() {
	}
}
