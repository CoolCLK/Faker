package coolclk.faker.modules.root.render;

import coolclk.faker.gui.clickgui.ClickGuiScreen;
import coolclk.faker.launch.FakerForgeMod;
import coolclk.faker.modules.Module;
import coolclk.faker.util.ModuleUtil;
import org.lwjgl.input.Keyboard;

public class ClickGui extends Module {
    public static ClickGui INSTANCE = new ClickGui();

    public ClickGui() {
        super("ClickGui");
    }

    public void onEnable() {
        ModuleUtil.gEP().openGui(FakerForgeMod.INSTANCE, ClickGuiScreen.ID, ModuleUtil.gEP().getEntityWorld(), ModuleUtil.gEP().getPosition().getX(), ModuleUtil.gEP().getPosition().getY(), ModuleUtil.gEP().getPosition().getZ());
    }

    public void onDisable() {
        ModuleUtil.gEP().closeScreen();
    }

    public int getDefaultKeyCode() {
        return Keyboard.KEY_RSHIFT;
    }
}
