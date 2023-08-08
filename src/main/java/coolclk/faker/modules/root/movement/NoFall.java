package coolclk.faker.modules.root.movement;

import coolclk.faker.modules.Module;
import coolclk.faker.util.ModuleUtil;

import java.util.Collections;

public class NoFall extends Module {
    public static NoFall INSTANCE = new NoFall();

    public NoFall() {
        super("NoFall");
    }

    @Override
    public void onEnabling() {
        if (ModuleUtil.gEP().fallDistance > 2) {
            ModuleUtil.gEP().onGround = true;
            ModuleUtil.gEP().fallDistance = 0;
        }
    }
}
