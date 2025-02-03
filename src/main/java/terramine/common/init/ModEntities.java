package terramine.common.init;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;
import terramine.TerraMine;
import terramine.common.entity.block.InstantPrimedTNTEntity;
import terramine.common.entity.misc.ClientItemEntity;
import terramine.common.entity.mobs.bosses.TestBoss;
import terramine.common.entity.mobs.hardmode.MimicEntity;
import terramine.common.entity.mobs.prehardmode.CrimeraEntity;
import terramine.common.entity.mobs.prehardmode.DemonEyeEntity;
import terramine.common.entity.mobs.prehardmode.EaterOfSoulsEntity;
import terramine.common.entity.mobs.prehardmode.devourer.DevourerBodyEntity;
import terramine.common.entity.mobs.prehardmode.devourer.DevourerEntity;
import terramine.common.entity.mobs.prehardmode.devourer.DevourerTailEntity;
import terramine.common.entity.projectiles.*;
import terramine.common.entity.projectiles.arrows.FlamingArrowEntity;
import terramine.common.entity.projectiles.arrows.JesterArrowEntity;
import terramine.common.entity.projectiles.arrows.UnholyArrowEntity;
import terramine.common.entity.projectiles.throwables.BombEntity;
import terramine.common.entity.projectiles.throwables.DynamiteEntity;
import terramine.common.entity.projectiles.throwables.GrenadeEntity;

public class ModEntities {

	public static final EntityType<MimicEntity> MIMIC = register(TerraMine.id("mimic"), FabricEntityType.Builder
			.createMob(MimicEntity::new, MobCategory.MONSTER, (builder) ->
					builder.defaultAttributes(MimicEntity::createMobAttributes))
			.sized(14 / 16F, 14 / 16F)
			.clientTrackingRange(64));

	public static final EntityType<DemonEyeEntity> DEMON_EYE = register(TerraMine.id("demon_eye"), FabricEntityType.Builder
			.createMob(DemonEyeEntity::new, MobCategory.MONSTER, (builder) ->
					builder.defaultAttributes(DemonEyeEntity::createMobAttributes).spawnRestriction(SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DemonEyeEntity::checkMobSpawnRules))
			.sized(0.53f, 0.8f));

	public static final EntityType<EaterOfSoulsEntity> EATER_OF_SOULS = register(TerraMine.id("eater_of_souls"), FabricEntityType.Builder
			.createMob(EaterOfSoulsEntity::new, MobCategory.MONSTER, (builder) ->
					builder.defaultAttributes(EaterOfSoulsEntity::createMobAttributes).spawnRestriction(SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EaterOfSoulsEntity::checkMobSpawnRules))
			.sized(1.2f, 0.9f));

	public static final EntityType<DevourerEntity> DEVOURER = register(TerraMine.id("devourer"), FabricEntityType.Builder
			.createMob(DevourerEntity::new, MobCategory.MONSTER, (builder) ->
					builder.defaultAttributes(DevourerEntity::createMobAttributes).spawnRestriction(SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DevourerEntity::checkMobSpawnRules))
			.sized(0.8f, 0.75f));

	public static final EntityType<DevourerBodyEntity> DEVOURER_BODY = register(TerraMine.id("devourer_body"), FabricEntityType.Builder
			.createMob(DevourerBodyEntity::new, MobCategory.MONSTER, (builder) ->
					builder.defaultAttributes(DevourerBodyEntity::createMobAttributes))
			.sized(0.8f, 0.75f)
			.noSummon());

	public static final EntityType<DevourerTailEntity> DEVOURER_TAIL = register(TerraMine.id("devourer_tail"), FabricEntityType.Builder
			.createMob(DevourerTailEntity::new, MobCategory.MONSTER, (builder) ->
					builder.defaultAttributes(DevourerTailEntity::createMobAttributes))
			.sized(0.8f, 0.75f)
			.noSummon());

	public static final EntityType<CrimeraEntity> CRIMERA = register(TerraMine.id("crimera"), FabricEntityType.Builder
			.createMob(CrimeraEntity::new, MobCategory.MONSTER, (builder) ->
					builder.defaultAttributes(CrimeraEntity::createMobAttributes).spawnRestriction(SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CrimeraEntity::checkMobSpawnRules))
			.sized(1.05f, 0.8f));

	/**
	 * Testing, remove later
	 */
	public static final EntityType<TestBoss> TEST_BOSS = register(TerraMine.id("test_boss"), FabricEntityType.Builder
			.createMob(TestBoss::new, MobCategory.MONSTER, (builder) ->
					builder.defaultAttributes(TestBoss::createMobAttributes).spawnRestriction(SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, TestBoss::checkMobSpawnRules))
			.sized(1f, 2f));

	public static final EntityType<ClientItemEntity> CLIENT_ITEM = register(TerraMine.id("client_item"), EntityType.Builder
			.of(ClientItemEntity::new, MobCategory.MISC)
			.sized(0.25F, 0.25F)
			.noSummon());

	public static final EntityType<FallingStarEntity> FALLING_STAR = register(TerraMine.id("falling_star"), EntityType.Builder
			.of(FallingStarEntity::new, MobCategory.MISC)
			.sized(0.25f, 0.25f));

	public static final EntityType<FallingMeteoriteEntity> METEORITE = register(TerraMine.id("meteorite"), EntityType.Builder
			.of(FallingMeteoriteEntity::new, MobCategory.MISC)
			.sized(1f, 1f));

	public static final EntityType<MagicMissileEntity> MAGIC_MISSILE = register(TerraMine.id("magic_missile"), EntityType.Builder
			.of(MagicMissileEntity::new, MobCategory.MISC)
			.sized(0.25f, 0.25f)
			.noSummon());

	public static final EntityType<FlamelashMissileEntity> FLAMELASH_MISSILE = register(TerraMine.id("flamelash_missile"), EntityType.Builder
			.of(FlamelashMissileEntity::new, MobCategory.MISC)
			.sized(0.25f, 0.25f)
			.noSummon());

	public static final EntityType<RainbowMissileEntity> RAINBOW_MISSILE = register(TerraMine.id("rainbow_missile"), EntityType.Builder
			.of(RainbowMissileEntity::new, MobCategory.MISC)
			.sized(0.25f, 0.25f)
			.noSummon());

	public static final EntityType<LaserEntity> LASER = register(TerraMine.id("laser"), EntityType.Builder
			.of(LaserEntity::new, MobCategory.MISC)
			.sized(0.60f, 0.10f)
			.noSummon());

	public static final EntityType<DynamiteEntity> DYNAMITE = register(TerraMine.id("dynamite"), EntityType.Builder
			.of(DynamiteEntity::new, MobCategory.MISC)
			.sized(0.25f, 0.25f)
			.noSummon());

	public static final EntityType<GrenadeEntity> GRENADE = register(TerraMine.id("grenade"), EntityType.Builder
			.of(GrenadeEntity::new, MobCategory.MISC)
			.sized(0.25f, 0.25f)
			.noSummon());

	public static final EntityType<BombEntity> BOMB = register(TerraMine.id("bomb"), EntityType.Builder
			.of(BombEntity::new, MobCategory.MISC)
			.sized(0.5f, 0.5f)
			.noSummon());

	public static final EntityType<InstantPrimedTNTEntity> INSTANT_TNT = register(TerraMine.id("instant_tnt"), EntityType.Builder
			.of(InstantPrimedTNTEntity::new, MobCategory.MISC)
			.fireImmune()
			.sized(0.98f, 0.98f)
			.clientTrackingRange(10)
			.updateInterval(10));

	public static final EntityType<FlamingArrowEntity> FLAMING_ARROW = createArrow(FlamingArrowEntity::new, TerraMine.id("flaming_arrow"));
	public static final EntityType<UnholyArrowEntity> UNHOLY_ARROW = createArrow(UnholyArrowEntity::new, TerraMine.id("unholy_arrow"));
	public static final EntityType<JesterArrowEntity> JESTER_ARROW = createArrow(JesterArrowEntity::new, TerraMine.id("jester_arrow"));

	private static <T extends Entity> EntityType<T> register(ResourceLocation resourceLocation, EntityType.Builder<T> entType) {
		return Registry.register(BuiltInRegistries.ENTITY_TYPE, resourceLocation, entType.build(ResourceKey.create(Registries.ENTITY_TYPE, resourceLocation)));
	}

	private static <T extends Entity> EntityType<T> createArrow(EntityType.EntityFactory<T> factory, ResourceLocation resourceLocation) {
		EntityType<T> entityType = EntityType.Builder.of(factory, MobCategory.MISC).sized(0.5f, 0.5f).clientTrackingRange(20).build(ResourceKey.create(Registries.ENTITY_TYPE, resourceLocation));
		return Registry.register(BuiltInRegistries.ENTITY_TYPE, resourceLocation, entityType);
	}

	public static void addToSpawn() {
		naturalSpawn(DEMON_EYE, MobCategory.MONSTER, 110, 2, 6);
	}

	public static <T extends Entity> void naturalSpawn(EntityType<T> entType, MobCategory category, int weight, int minGroup, int maxGroup) {
		BiomeModifications.addSpawn(BiomeSelectors.spawnsOneOf(EntityType.ZOMBIE).and(BiomeSelectors.foundInOverworld()), category, entType, weight, minGroup, maxGroup);
	}
}
