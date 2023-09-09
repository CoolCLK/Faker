package coolclk.faker.feature.modules.movement;

import coolclk.faker.event.events.PlayerUpdateEvent;
import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.api.SettingsFloat;
import coolclk.faker.feature.modules.ModuleCategory;
import coolclk.faker.util.ModuleUtil;

@ModuleInfo(name = "Speed", category = ModuleCategory.Movement)
public class Speed extends Module {
    public SettingsFloat speed = new SettingsFloat(this, "speed", 0.1F, 0F, 5F);

    @Override
    public void onUpdate(PlayerUpdateEvent event) {
        event.getEntityPlayer().movementInput.moveStrafe *= event.getEntityPlayer().capabilities.getWalkSpeed() / speed.getValue();
        event.getEntityPlayer().movementInput.moveForward *= event.getEntityPlayer().capabilities.getWalkSpeed() / speed.getValue();
    }
}
