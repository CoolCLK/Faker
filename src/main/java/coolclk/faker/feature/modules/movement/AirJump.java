package coolclk.faker.feature.modules.movement;

import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.modules.ModuleCategory;
import coolclk.faker.util.ModuleUtil;

@ModuleInfo(name = "AirJump", category = ModuleCategory.Movement)
public class AirJump extends Module {
    @Override
    public void onEnabling() {
        if (!ModuleUtil.gEP().onGround) {
            ModuleUtil.gEP().onGround = true;
        }
    }
}
