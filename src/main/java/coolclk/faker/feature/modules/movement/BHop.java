package coolclk.faker.feature.modules.movement;

import coolclk.faker.event.events.PlayerUpdateEvent;
import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.api.SettingsDouble;
import coolclk.faker.feature.modules.ModuleCategory;
import coolclk.faker.util.ModuleUtil;

@ModuleInfo(name = "BHop", category = ModuleCategory.Movement)
public class BHop extends Module {
    public SettingsDouble hopInterval = new SettingsDouble(this, "hopInterval", 0.5, 0D, 4D);
    private float movedStep = 0;

    @Override
    public void onUpdate(PlayerUpdateEvent event) {
        float movement = Math.abs(event.getEntityPlayer().movementInput.moveForward) + Math.abs(event.getEntityPlayer().movementInput.moveStrafe);
        if (movement > 0) {
            movedStep += Math.abs(movement) / 20;
        } else {
            movedStep = 0;
        }

        if (movedStep >= hopInterval.getValue() && !event.getEntityPlayer().isSneaking() && ((event.getEntityPlayer().onGround && event.getEntityPlayer().fallDistance <= 0 && event.getEntityPlayer().motionY <= 0) && !event.getEntityPlayer().isInWater() && !event.getEntityPlayer().isInLava())) {
            event.getEntityPlayer().jump();
            movedStep = 0;
        }
    }
}
