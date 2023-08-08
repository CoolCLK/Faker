package coolclk.faker.modules.root.movement;

import coolclk.faker.modules.Module;
import coolclk.faker.util.ModuleUtil;

public class ForceSprinting extends Module {
    public static ForceSprinting INSTANCE = new ForceSprinting();

    public ForceSprinting() {
        super("ForceSprinting");
    }

    @Override
    public void onEnabling() {
        if (!ModuleUtil.gEP().isSprinting() && ModuleUtil.gEP().movementInput.moveForward > 0) {
            ModuleUtil.gEP().setSprinting(true);
        }
    }
}
