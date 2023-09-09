package coolclk.faker.feature.modules.render;

import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.modules.ModuleCategory;
import net.minecraft.client.Minecraft;

@ModuleInfo(name = "FullBright", category = ModuleCategory.Render)
public class FullBright extends Module {
    private float oldGamma;

    @Override
    public void onEnable() {
        oldGamma = Minecraft.getMinecraft().gameSettings.gammaSetting;
        Minecraft.getMinecraft().gameSettings.gammaSetting = 100;
    }

    @Override
    public void onUpdate() {
        if (Minecraft.getMinecraft().gameSettings.gammaSetting < 100) {
            oldGamma = Minecraft.getMinecraft().gameSettings.gammaSetting;
            this.toggleModule();
        }
    }

    @Override
    public void onDisable() {
        Minecraft.getMinecraft().gameSettings.gammaSetting = oldGamma;
    }
}
