package coolclk.faker.modules.root.movement;

import coolclk.faker.modules.Module;
import coolclk.faker.modules.ModuleUtil;

import java.util.Arrays;

public class KeepRotating extends Module {
    public static KeepRotating INSTANCE = new KeepRotating();

    public KeepRotating() {
        super("KeepRotating", Arrays.asList(new ModuleArgument("horizontalSpeed", 5, 0, 360), new ModuleArgument("verticalSpeed", 1, 0, 180)));
    }

    public void onEnabling() {
        ModuleUtil.gEP().setAngles(ModuleUtil.gEP().rotationYaw + this.getArgument("horizontalSpeed").getNumberValueF(), ModuleUtil.gEP().rotationPitch + this.getArgument("verticalSpeed").getNumberValueF());
    }
}
