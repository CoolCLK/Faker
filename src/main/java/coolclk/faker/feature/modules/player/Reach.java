package coolclk.faker.feature.modules.player;

import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.api.SettingsDouble;
import coolclk.faker.feature.modules.ModuleCategory;

@ModuleInfo(name = "Reach", category = ModuleCategory.Combat)
public class Reach extends Module {
    public SettingsDouble blockDistance = new SettingsDouble(this, "blockDistance", 4.5D, 4.5D, 6D);
    public SettingsDouble entityDistance = new SettingsDouble(this, "entityDistance", 3.5D, 3.5D, 6D);
}
