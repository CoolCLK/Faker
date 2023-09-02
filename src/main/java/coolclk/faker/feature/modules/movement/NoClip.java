package coolclk.faker.feature.modules.movement;

import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.modules.ModuleCategory;
import coolclk.faker.util.ModuleUtil;

@ModuleInfo(name = "NoClip", category = ModuleCategory.Movement)
public class NoClip extends Module {
    @Override
    public void onEnabling() {
        ModuleUtil.gEP().noClip = true;
    }

    @Override
    public void onDisable() {
        ModuleUtil.gEP().noClip = false;
    }
}
