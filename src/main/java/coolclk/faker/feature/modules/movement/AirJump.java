package coolclk.faker.feature.modules.movement;

import coolclk.faker.event.events.PlayerUpdateEvent;
import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.modules.ModuleCategory;

@ModuleInfo(name = "AirJump", category = ModuleCategory.Movement)
public class AirJump extends Module {
    @Override
    public void onUpdate(PlayerUpdateEvent event) {
        if (event.getEntityPlayer().movementInput.jump) {
            event.getEntityPlayer().onGround = true;
        }
    }
}
