package terramine.common.init;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import terramine.TerraMine;

public class ModSoundEvents {

	// Entity
	public static final SoundEvent MIMIC_HURT = register("entity.mimic.hurt");
	public static final SoundEvent MIMIC_DEATH = register("entity.mimic.death");
	public static final SoundEvent MIMIC_OPEN = register("entity.mimic.open");
	public static final SoundEvent MIMIC_CLOSE = register("entity.mimic.close");
	public static final SoundEvent DEMON_EYE_HURT = register("entity.demoneye.hurt");
	public static final SoundEvent DEMON_EYE_DEATH = register("entity.demoneye.death");
	public static final SoundEvent FALLING_STAR_FALL = register("fallingstar.fall");
	public static final SoundEvent FALLING_STAR_CRASH = register("fallingstar.crash");
	public static final SoundEvent MAGIC_MISSILE_SHOOT = register("item.magicmissile.use");
	public static final SoundEvent FLAMELASH_SHOOT = register("item.flamelash.use");
	public static final SoundEvent RAINBOW_ROD_SHOOT = register("item.rainbowrod.use");

	// Accessories
	public static final SoundEvent FART = register("item.whoopee_cushion.fart");
	public static final SoundEvent WATER_STEP = register("block.water.step");
	public static final SoundEvent DOUBLE_JUMP = register("generic.double_jump");
	public static final SoundEvent ROCKET_BOOTS = register("item.rocket_boots.flame");
	public static final SoundEvent SPECTRE_BOOTS = register("item.spectre_boots.flame");
	public static final SoundEvent SPEEDBOOTS_RUN = register("item.spectre_boots.run");
	public static final SoundEvent WINGS_FLAP = register("item.wings.flap");

	// Items
	public static final SoundEvent MAGIC_MIRROR_USE = register("item.magic_mirror.use");
	public static final SoundEvent MANA_CRYSTAL_USE = register("item.mana_crystal.use");
	public static final SoundEvent PHASEBLADE_SWING = register("item.phaseblade.swing");

	// Misc
	public static final SoundEvent BOMB = register("generic.bomb");
	public static final SoundEvent THROW = register("generic.throw");
	public static final SoundEvent MANA_FULL = register("mana.full");

	private static SoundEvent register(String name) {
		ResourceLocation id = TerraMine.id(name);
		return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
	}

	private ModSoundEvents() {
	}
}
