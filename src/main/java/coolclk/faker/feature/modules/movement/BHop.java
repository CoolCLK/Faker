package coolclk.faker.feature.modules.movement;

import coolclk.faker.feature.ModuleHandler;
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
    public void onEnabling() {
        float movement = Math.abs(ModuleUtil.gEP().movementInput.moveForward) + Math.abs(ModuleUtil.gEP().movementInput.moveStrafe);
        if (movement > 0) {
            movedStep += Math.abs(movement) / 20;
        } else {
            movedStep = 0;
        }

        if (movedStep >= hopInterval.getValue() && !ModuleUtil.gEP().isSneaking() && ((ModuleUtil.gEP().onGround || ModuleHandler.findModule(AirJump.class).getEnable()) && !ModuleUtil.gEP().isInWater() && !ModuleUtil.gEP().isInLava())) {
            ModuleUtil.gEP().jump();
            movedStep = 0;
        }
    }
}
