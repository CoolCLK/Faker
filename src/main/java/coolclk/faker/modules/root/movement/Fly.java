package coolclk.faker.modules.root.movement;

import coolclk.faker.modules.Module;
import coolclk.faker.modules.root.render.FreeCam;
import coolclk.faker.util.ModuleUtil;

import java.util.Collections;

public class Fly extends Module {
    public static Fly INSTANCE = new Fly();

    public Fly() {
        super("Fly", Collections.singletonList(new ModuleArgument("flySpeed", 0.5, 0.1, 10)));
    }

    @Override
    public void onEnabling() {
        if (FreeCam.INSTANCE.getEnable()) {
            this.toggleModule();
            return;
        }
        ModuleUtil.gEP().motionY = (ModuleUtil.gM().gameSettings.keyBindJump.isKeyDown() ? this.getArgument("flySpeed").getNumberValueD() : 0) + (ModuleUtil.gM().gameSettings.keyBindSneak.isKeyDown() ? -this.getArgument("flySpeed").getNumberValueD() : 0);
    }
}
