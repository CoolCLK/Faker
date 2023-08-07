package coolclk.faker.modules.root.movement;

import coolclk.faker.modules.Module;
import coolclk.faker.modules.ModuleUtil;

public class ForceSprinting extends Module {
    public static ForceSprinting INSTANCE = new ForceSprinting();

    public ForceSprinting() {
        super("ForceSprinting");
    }

    @Override
    public void onEnabling() {
        if (!ModuleUtil.gEP().isSprinting() && Math.abs(ModuleUtil.gEP().movementInput.moveForward) + Math.abs(ModuleUtil.gEP().movementInput.moveStrafe) > 0) {
            ModuleUtil.gEP().setSprinting(true);
        }
    }
}
