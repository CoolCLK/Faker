package coolclk.faker.feature.modules.movement;

import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.api.SettingsFloat;
import coolclk.faker.feature.modules.ModuleCategory;
import coolclk.faker.util.ModuleUtil;

@ModuleInfo(name = "Speed", category = ModuleCategory.Movement)
public class Speed extends Module {
    public SettingsFloat speed = new SettingsFloat(this, "speed", 0.1F, 0F, 5F);

    @Override
    public void onEnabling() {
        ModuleUtil.gEP().movementInput.moveStrafe *= ModuleUtil.gEP().capabilities.getWalkSpeed() / speed.getValue();
        ModuleUtil.gEP().movementInput.moveForward *= ModuleUtil.gEP().capabilities.getWalkSpeed() / speed.getValue();
    }
}
