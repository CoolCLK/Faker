package coolclk.faker.feature.modules.player;

import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.api.SettingsFloat;
import coolclk.faker.feature.modules.ModuleCategory;

@ModuleInfo(name = "Timer", category = ModuleCategory.Player)
public class Timer extends Module {
    public static float currentTimerSpeed = 0;
    public SettingsFloat speed = new SettingsFloat(this, "speed", 1.08F, 0.1F, 2F);
}
