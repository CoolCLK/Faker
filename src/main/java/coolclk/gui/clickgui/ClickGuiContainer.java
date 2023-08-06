package coolclk.gui.clickgui;

import coolclk.gui.GuiHandler;
import coolclk.modules.Module;
import coolclk.modules.ModuleHandler;
import coolclk.modules.ModuleType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClickGuiContainer extends GuiScreen {
    public final static int ID = 1;

    private double backgroundAlpha = 0;
    private final List<ClickGuiButton> clickGuiButtonList = new ArrayList<ClickGuiButton>();

    public final static int buttonWidth = 50;
    public final static int buttonHeight = 15;
    public final static int groupButtonColor = 0xFF343434;
    public final static int childrenButtonMenuColor = 0xFF5C5C5C;
    public final static int childrenButtonDisableColor = 0xFF696969;
    public final static int childrenButtonEnableColor = 0xFF0091A6;

    private boolean menuOpen = true;
    public static boolean dragging = false;

    public ClickGuiContainer() {
        super();
        for (ModuleType group : ModuleHandler.getAllModules()) {
            List<ClickGuiButton> submodules = new ArrayList<ClickGuiButton>();
            int yPosition = group.getClickGuiPositionY();
            for (Module module : group.getModules()) {
                yPosition += buttonHeight;
                submodules.add(new ClickGuiButton(group.getClickGuiPositionX(), yPosition, buttonWidth, buttonHeight, module.getDisplayName(), new int[] { childrenButtonDisableColor, childrenButtonEnableColor, childrenButtonMenuColor }, 0xFFFFFFFF, module, group));
            }
            this.clickGuiButtonList.addAll(submodules);
            this.clickGuiButtonList.add(new ClickGuiButton(group.getClickGuiPositionX(), group.getClickGuiPositionY(), buttonWidth, buttonHeight, group.getDisplayName(), groupButtonColor, 0xFFFFFFFF, group, submodules));
        }
    }

    @Override
    public void initGui() {
        super.initGui();
        this.menuOpen = true;

        width = Minecraft.getMinecraft().displayWidth;
        height = Minecraft.getMinecraft().displayHeight;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.backgroundAlpha += ((this.menuOpen ? 0.25 : 0) - this.backgroundAlpha) * GuiHandler.easeOutQuad(0.125);
        Color backgroundColor = new Color(0, 0, 0, (int) (this.backgroundAlpha * 255));
        drawRect(0, 0, this.width, this.height, backgroundColor.getRGB());
        for (ClickGuiButton button : this.clickGuiButtonList) {
            button.drawButton(this.mc, mouseX, mouseY);
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        for (ClickGuiButton button : this.clickGuiButtonList) {
            button.mouseReleased(mouseX, mouseY);
        }
    }

    @Override
    public void onGuiClosed() {
        dragging = false;
        this.menuOpen = false;
    }
}
