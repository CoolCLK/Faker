package coolclk.faker.modules.root.player;

import coolclk.faker.modules.Module;
import coolclk.faker.util.ModuleUtil;
import net.minecraft.client.resources.I18n;

import java.util.Collections;

import static coolclk.faker.launch.FakerForgeMod.LOGGER;

public class Timer extends Module {
    public static Timer INSTANCE = new Timer();

    public Timer() {
        super("Timer", Collections.singletonList(new ModuleArgument("speed", 1.08, 0.1, 2)));
    }

    public void onEnabling() {
        setTimerSpeed(this.getArgument("speed").getNumberValueF(), this.getDisplayName());
    }

    public static void setTimerSpeed(float speed, String errorModuleDisplayName) {
        try {
            net.minecraft.util.Timer timer = (net.minecraft.util.Timer) ModuleUtil.getInaccessibleVariable(ModuleUtil.gM(), "timer");
            if (timer.timerSpeed != speed) {
                timer.timerSpeed = speed;
            }
        } catch (Exception e) {
            LOGGER.error(I18n.format("modules.message.ModuleThrowsError", errorModuleDisplayName));
        }
    }
}
