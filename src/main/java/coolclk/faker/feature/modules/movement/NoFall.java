package coolclk.faker.feature.modules.movement;

import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.modules.ModuleCategory;
import coolclk.faker.util.ModuleUtil;

@ModuleInfo(name = "NoFall", category = ModuleCategory.Movement)
public class NoFall extends Module {
    @Override
    public void onEnabling() {
        if ((ModuleUtil.gEP()).fallDistance > 2) {
            ModuleUtil.gEP().onGround = true;
            (ModuleUtil.gEP()).fallDistance = 0;
        }
    }
}
