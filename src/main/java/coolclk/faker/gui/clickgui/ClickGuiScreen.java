package coolclk.faker.gui.clickgui;

import coolclk.faker.gui.GuiHandler;
import coolclk.faker.modules.Module;
import coolclk.faker.modules.ModuleHandler;
import coolclk.faker.modules.ModuleType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClickGuiScreen extends GuiScreen {
    public final static int ID = 1;
    public static ClickGuiScreen INSTANCE = new ClickGuiScreen();

    private double alpha = 0;
    private final List<ClickGuiButton> clickGuiButtonList = new ArrayList<ClickGuiButton>();

    public final static int buttonWidth = 50;
    public final static int buttonHeight = 15;
    public final static int groupButtonColor = 0x343434;
    public final static int childrenButtonMenuColor = 0x5C5C5C;
    public final static int childrenButtonDisableColor = 0x696969;
    public final static int childrenButtonEnableColor = 0x0091A6;

    private boolean menuOpen = true;
    public static boolean dragging = false;

    public ClickGuiScreen() {
        super();
        for (ModuleType group : ModuleHandler.getAllModules()) {
            List<ClickGuiButton> submodules = new ArrayList<ClickGuiButton>();
            int yPosition = group.getClickGuiPositionY();
            for (Module module : group.getModules()) {
                yPosition += buttonHeight;
                submodules.add(new ClickGuiButton(group.getClickGuiPositionX(), yPosition, buttonWidth, buttonHeight, module.getI18nKey(), new int[] { childrenButtonDisableColor, childrenButtonEnableColor, childrenButtonMenuColor }, 0xFFFFFF, module, group));
            }
            this.clickGuiButtonList.addAll(submodules);
            this.clickGuiButtonList.add(new ClickGuiButton(group.getClickGuiPositionX(), group.getClickGuiPositionY(), buttonWidth, buttonHeight, group.getI18nKey(), groupButtonColor, 0xFFFFFFFF, group, submodules));
        }
    }

    @Override
    public void initGui() {
        super.initGui();
        this.menuOpen = true;
        dragging = false;

        width = Minecraft.getMinecraft().displayWidth;
        height = Minecraft.getMinecraft().displayHeight;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.alpha += ((this.menuOpen ? 255 : 0) - this.alpha) * GuiHandler.easeOutQuad(1);
        Color backgroundColor = new Color(0, 0, 0, (int) (this.alpha * 0.25));
        drawRect(0, 0, this.width, this.height, backgroundColor.getRGB());
        for (ClickGuiButton button : this.clickGuiButtonList) {
            button.setAlpha((int) this.alpha);
            if (button.module != null) {
                button.module.onClickGuiUpdate();
            }
            button.drawButton(this.mc, mouseX, mouseY, partialTicks);
        }

        if (this.alpha < 50 && !menuOpen) {
            try {
                super.keyTyped((char) Keyboard.KEY_ESCAPE, Keyboard.KEY_ESCAPE);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        for (ClickGuiButton button : this.clickGuiButtonList) {
            button.mouseReleased(mouseX, mouseY);
        }
    }

    @Override
    protected void keyTyped(char text, int keycode) {
        if (keycode == Keyboard.KEY_ESCAPE) {
            this.menuOpen = false;
            dragging = false;
        }
    }
}
