package coolclk.faker.feature.modules.player;

import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.api.SettingsFloat;
import coolclk.faker.feature.api.SettingsInteger;
import coolclk.faker.feature.modules.ModuleCategory;

@ModuleInfo(name = "Regen", category = ModuleCategory.Player)
public class Regen extends Module {
    public SettingsInteger healSpeed = new SettingsInteger(this, "healSpeed", 80, 0, 80);
    public SettingsFloat healHealth = new SettingsFloat(this, "healHealth", 1F, 1F, 20F);
}
