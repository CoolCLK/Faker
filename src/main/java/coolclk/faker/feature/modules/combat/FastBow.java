package coolclk.faker.feature.modules.combat;

import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.api.SettingsInteger;
import coolclk.faker.feature.modules.ModuleCategory;

@ModuleInfo(name = "FastBow", category = ModuleCategory.Combat)
public class FastBow extends Module {
    public SettingsInteger bowDuration = new SettingsInteger(this, "bowDuration", 72000, 0, 72000);
}
