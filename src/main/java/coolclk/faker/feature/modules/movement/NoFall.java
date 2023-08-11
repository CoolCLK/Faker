package coolclk.faker.feature.modules.movement;

import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.api.SettingsModeString;
import coolclk.faker.feature.modules.ModuleCategory;
import coolclk.faker.util.ModuleUtil;

@ModuleInfo(name = "NoFall", category = ModuleCategory.Movement)
public class NoFall extends Module {
    public SettingsModeString modes = new SettingsModeString(this, "vanilla", "vanilla", "velocity");

    @Override
    public void onEnabling() {
        if (ModuleUtil.gEP().fallDistance > 2) {
            if (modes.getValue().equals("vanilla")) {
                ModuleUtil.gEP().onGround = true;
                ModuleUtil.gEP().fallDistance = 0;
            } else if (modes.getValue().equals("velocity")) {
                ModuleUtil.gEP().motionY *= 0.9;
                ModuleUtil.gEP().fallDistance *= 0.9F;
            }
        }
    }
}
