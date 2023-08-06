package coolclk.modules.root.render;

import coolclk.Faker;
import coolclk.gui.clickgui.ClickGuiContainer;
import coolclk.modules.Module;
import org.lwjgl.input.Keyboard;

public class ClickGui extends Module {
    public ClickGui() {
        super("ClickGui");
    }

    public void onEnable() {
        getEntityPlayer().openGui(Faker.INSTANCE, ClickGuiContainer.ID, getEntityPlayer().getEntityWorld(), getEntityPlayer().getPosition().getX(), getEntityPlayer().getPosition().getY(), getEntityPlayer().getPosition().getZ());
    }

    public void onDisable() {
        getEntityPlayer().closeScreen();
    }

    public int getDefaultKeyCode() {
        return Keyboard.KEY_RSHIFT;
    }
}
