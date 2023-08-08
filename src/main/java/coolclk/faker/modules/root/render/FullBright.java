package coolclk.faker.modules.root.render;

import coolclk.faker.modules.Module;
import net.minecraft.client.Minecraft;

public class FullBright extends Module {
    public static FullBright INSTANCE = new FullBright();

    private float oldGamma;

    public FullBright() {
        super("FullBright");
    }

    public void onEnable() {
        oldGamma = Minecraft.getMinecraft().gameSettings.gammaSetting;
        Minecraft.getMinecraft().gameSettings.gammaSetting = 100;
    }

    public void onEnabling() {
        if (Minecraft.getMinecraft().gameSettings.gammaSetting < 100) {
            oldGamma = Minecraft.getMinecraft().gameSettings.gammaSetting;
            this.toggleModule();
        }
    }

    public void onDisable() {
        Minecraft.getMinecraft().gameSettings.gammaSetting = oldGamma;
    }
}
