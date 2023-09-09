package coolclk.faker.feature.modules.player;

import coolclk.faker.event.api.SubscribeEvent;
import coolclk.faker.event.events.PlayerBreakSpeedEvent;
import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.api.SettingsFloat;
import coolclk.faker.feature.modules.ModuleCategory;

@ModuleInfo(name = "FastBreak", category = ModuleCategory.Player)
public class FastBreak extends Module {
    public SettingsFloat breakSpeed = new SettingsFloat(this, "breakSpeed", 1F, 1F, 8F);

    @SubscribeEvent
    public void onBreak(PlayerBreakSpeedEvent event) {
        if (this.getEnable()) {
            event.addBreakSpeed(breakSpeed.getValue());
        }
    }
}
