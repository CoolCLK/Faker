package coolclk.faker.feature.modules.render;

import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.api.SettingsFloat;
import coolclk.faker.feature.api.SettingsModeString;
import coolclk.faker.feature.modules.ModuleCategory;

@ModuleInfo(name = "ESP", category = ModuleCategory.Render)
public class ESP extends Module {
    public SettingsModeString modes = new SettingsModeString(this, "outline", "outline");
    public SettingsFloat outlineWidth = new SettingsFloat(this, "outlineWidth", 1F, 0F, 2F);
    public SettingsFloat outlineAlpha = new SettingsFloat(this, "outlineAlpha", 255F, 0F, 255F);
}
