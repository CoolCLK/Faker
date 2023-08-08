package coolclk.faker.modules.root.render;

import coolclk.faker.modules.Module;

import java.util.Collections;

public class HurtCam extends Module {
    public static HurtCam INSTANCE = new HurtCam();

    public HurtCam() {
        super("HurtCam", Collections.singletonList(new ModuleArgument("per", 1)));
    }
}
