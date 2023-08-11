package coolclk.faker.feature.modules.combat;

import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.api.SettingsDouble;
import coolclk.faker.feature.modules.ModuleCategory;

@ModuleInfo(name = "Hitbox", category = ModuleCategory.Combat)
public class Hitbox extends Module {
    public SettingsDouble size = new SettingsDouble(this, "size", 1D, 1D, 3D);
}
