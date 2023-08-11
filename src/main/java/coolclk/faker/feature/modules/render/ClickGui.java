package coolclk.faker.feature.modules.render;

import coolclk.faker.gui.clickgui.ClickGuiScreen;
import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.modules.ModuleCategory;
import coolclk.faker.feature.api.ModuleInfo;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

@ModuleInfo(name = "ClickGui", defaultKeycode = Keyboard.KEY_RSHIFT, category = ModuleCategory.Render)
public class ClickGui extends Module {
    @Override
    public void afterRegister() {
        ClickGuiScreen.INSTANCE.registerButtons();
    }

    @Override
    public void onEnable() {
        Minecraft.getMinecraft().displayGuiScreen(ClickGuiScreen.INSTANCE);
    }

    @Override
    public void onDisable() {
        ClickGuiScreen.INSTANCE.onGuiClosed();
    }
}
