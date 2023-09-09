package coolclk.faker.feature.modules.movement;

import coolclk.faker.event.events.PlayerUpdateEvent;
import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.modules.ModuleCategory;
import coolclk.faker.util.ModuleUtil;

@ModuleInfo(name = "NoFall", category = ModuleCategory.Movement)
public class NoFall extends Module {
    @Override
    public void onUpdate(PlayerUpdateEvent event) {
        if (event.getEntityPlayer().fallDistance > 2) {
            event.getEntityPlayer().fallDistance = 2;
        }
    }
}
