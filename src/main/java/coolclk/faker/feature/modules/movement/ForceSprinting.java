package coolclk.faker.feature.modules.movement;

import coolclk.faker.event.events.PlayerUpdateEvent;
import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.modules.ModuleCategory;
import coolclk.faker.util.ModuleUtil;

@ModuleInfo(name = "ForceSprinting", category = ModuleCategory.Movement)
public class ForceSprinting extends Module {
    @Override
    public void onUpdate(PlayerUpdateEvent event) {
        if (!event.getEntityPlayer().isSprinting() && event.getEntityPlayer().movementInput.moveForward > 0) {
            event.getEntityPlayer().setSprinting(true);
        }
    }
}
