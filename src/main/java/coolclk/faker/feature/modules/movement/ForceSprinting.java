package coolclk.faker.feature.modules.movement;

import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.modules.ModuleCategory;
import coolclk.faker.util.ModuleUtil;

@ModuleInfo(name = "ForceSprinting", category = ModuleCategory.Movement)
public class ForceSprinting extends Module {
    @Override
    public void onEnabling() {
        if (!ModuleUtil.gEP().isSprinting() && ModuleUtil.gEP().movementInput.moveForward > 0) {
            ModuleUtil.gEP().setSprinting(true);
        }
    }
}
