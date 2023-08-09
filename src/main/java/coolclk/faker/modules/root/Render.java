package coolclk.faker.modules.root;

import coolclk.faker.modules.ModuleType;
import coolclk.faker.modules.root.render.*;

public class Render extends ModuleType {
    public static Render INSTANCE = new Render();

    public Render() {
        super("render", 210, 10, ClickGui.INSTANCE, ESP.INSTANCE, FreeCam.INSTANCE, FullBright.INSTANCE, HUD.INSTANCE, HurtCam.INSTANCE, NoInvisible.INSTANCE, NoWeather.INSTANCE);
    }
}
