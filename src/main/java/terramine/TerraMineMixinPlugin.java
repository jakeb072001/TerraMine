package terramine;

import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class TerraMineMixinPlugin implements IMixinConfigPlugin {

	// Base package for mixins as defined in terramine.mixins.json
	private static final String BASE_PACKAGE = "terramine.mixin";

	@Override
	public void onLoad(String mixinPackage) {
	}

	@Override
	public String getRefMapperConfig() {
		return null;
	}

	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
		if (mixinClassName.startsWith(BASE_PACKAGE + ".compat.")) {
			String subPackageAndClassName = mixinClassName.split(BASE_PACKAGE + "\\.compat\\.")[1];
			String modid = subPackageAndClassName.split("\\.")[0];
			return FabricLoader.getInstance().isModLoaded(modid);
		}
		if (mixinClassName.equals(BASE_PACKAGE + ".item.crossnecklace.LivingEntityMixin")) { // compatibility with Artifacts, disables Cross Necklace invulnerability as they conflict
			return !FabricLoader.getInstance().isModLoaded("artifacts");
		}

		return true;
	}

	@Override
	public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
	}

	@Override
	public List<String> getMixins() {
		return null;
	}

	@Override
	public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
	}

	@Override
	public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
	}
}
