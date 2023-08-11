package coolclk.faker.feature.modules.render;

import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.api.SettingsFloat;
import coolclk.faker.feature.modules.ModuleCategory;

@ModuleInfo(name = "HurtCam", category = ModuleCategory.Render)
public class HurtCam extends Module {
    public SettingsFloat shakePercent = new SettingsFloat(this, "shakePercent", 0F, 0F, 1F) {
        @Override
        public String getDisplayValue() {
            return (this.getValuePercent().doubleValue() * 100) + "%";
        }
    };
}
