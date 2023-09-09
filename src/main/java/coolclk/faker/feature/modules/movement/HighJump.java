package coolclk.faker.feature.modules.movement;

import coolclk.faker.event.api.SubscribeEvent;
import coolclk.faker.event.events.PlayerJumpEvent;
import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.api.SettingsDouble;
import coolclk.faker.feature.modules.ModuleCategory;

@ModuleInfo(name = "HighJump", category = ModuleCategory.Movement)
public class HighJump extends Module {
    public SettingsDouble jumpHeight = new SettingsDouble(this, "jumpHeight", 0.42, 0.42, 3D);

    @SubscribeEvent
    public void onPlayerJump(PlayerJumpEvent event) {
        if (this.getEnable()) {
            event.setUpwardsMotion(jumpHeight.getValue());
        }
    }
}
