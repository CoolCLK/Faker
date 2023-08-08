package coolclk.faker.modules.root.player;

import coolclk.faker.modules.Module;
import coolclk.faker.util.ModuleUtil;

import java.util.Arrays;

public class Derp extends Module {
    public static Derp INSTANCE = new Derp();

    public Derp() {
        super("Derp", Arrays.asList(new ModuleArgument("random", false), new ModuleArgument("horizontalSpeed", 10, 0, 180)));
    }

    @Override
    public void onClickGuiUpdate() {
        this.getArgument("horizontalSpeed").setVisible(!this.getArgument("random").getBooleanValue());
    }

    @Override
    public void onEnabling() {
        float yaw = ModuleUtil.gEP().rotationYawHead, motionYaw;
        if (this.getArgument("random").getBooleanValue()) {
            float rYaw = (float) (Math.random() * 360);
            motionYaw = rYaw - yaw;
        } else {
            motionYaw = this.getArgument("horizontalSpeed").getNumberValueF() / 20;
        }
        yaw += motionYaw;

        ModuleUtil.gEP().rotationYawHead = yaw;
        ModuleUtil.gEP().renderYawOffset = motionYaw;
    }

    @Override
    public void onDisable() {
        ModuleUtil.gEP().renderYawOffset = 0;
    }
}
