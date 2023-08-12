package coolclk.faker.feature.modules.combat;

import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.api.SettingsFloat;
import coolclk.faker.feature.modules.ModuleCategory;

@ModuleInfo(name = "Hitbox", category = ModuleCategory.Combat)
public class Hitbox extends Module {
    public SettingsFloat size = new SettingsFloat(this, "size", 1F, 1F, 3F);
}
