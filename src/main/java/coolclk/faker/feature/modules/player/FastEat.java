package coolclk.faker.feature.modules.player;

import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.api.SettingsInteger;
import coolclk.faker.feature.modules.ModuleCategory;

@ModuleInfo(name = "FastEat", category = ModuleCategory.Player)
public class FastEat extends Module {
    public SettingsInteger duration = new SettingsInteger(this, "duration", 32, 0, 32);
}
