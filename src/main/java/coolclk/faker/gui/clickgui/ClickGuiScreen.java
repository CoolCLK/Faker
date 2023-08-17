package coolclk.faker.gui.clickgui;

import coolclk.faker.feature.ModuleHandler;
import coolclk.faker.gui.GuiHandler;
import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.modules.ModuleCategory;
import coolclk.faker.feature.modules.render.ClickGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static coolclk.faker.launch.forge.FakerForgeMod.LOGGER;

public class ClickGuiScreen extends GuiScreen {
    public static ClickGuiScreen INSTANCE = new ClickGuiScreen();
    public static String MESSAGE = "";

    private double alpha = 0;
    private final List<ClickGuiButton> clickGuiButtonList = new ArrayList<ClickGuiButton>();

    private boolean menuOpen = true;
    public static boolean dragging = false;

    public ClickGuiScreen() {
        super();
    }

    public void registerButtons() {
        LOGGER.debug("Register ClickGui buttons");
        for (ModuleCategory category : ModuleCategory.values()) {
            if (category == ModuleCategory.None) {
                continue;
            }
            List<ClickGuiModuleButton> submodules = new ArrayList<ClickGuiModuleButton>();
            int yPosition = category.getPositionX();
            for (Module module : category.getModules()) {
                yPosition += GuiHandler.Theme.BUTTON_HEIGHT;
                submodules.add(new ClickGuiModuleButton(category.getPositionX(), yPosition, module));
                LOGGER.debug("Register ClickGui module button " + module.getDisplayName());
            }
            this.clickGuiButtonList.addAll(submodules);
            this.clickGuiButtonList.add(new ClickGuiCategoryButton(category.getPositionX(), category.getPositionY(), category, submodules));
            LOGGER.debug("Register ClickGui category button " + category.name());
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
        for (ClickGuiButton button : this.clickGuiButtonList) {
            button.updateDisplayName();
        }

        this.alpha += ((this.menuOpen ? 255 : 0) - this.alpha) * GuiHandler.easeOutQuad(1);
        Color backgroundColor = new Color(0, 0, 0, (int) (this.alpha * 0.25));
        drawRect(0, 0, this.width, this.height, backgroundColor.getRGB());
        drawString(this.fontRendererObj, MESSAGE, 10, 10, 0xFFFFFFFF);

        List<ClickGuiButton> defaultButtons = new ArrayList<ClickGuiButton>(this.clickGuiButtonList);
        List<ClickGuiButton> overButtons = new ArrayList<ClickGuiButton>();
        for (ClickGuiButton button : this.clickGuiButtonList) {
            if (button.isCategoryButton()) {
                if (((ClickGuiCategoryButton) button).moveOver) {
                    ((ClickGuiCategoryButton) button).moveOver = false;
                    overButtons.add(button);
                    overButtons.addAll(((ClickGuiCategoryButton) button).subButtons);
                    defaultButtons.remove(button);
                    defaultButtons.removeAll(((ClickGuiCategoryButton) button).subButtons);
                }
            }
        }
        this.clickGuiButtonList.clear();
        this.clickGuiButtonList.addAll(defaultButtons);
        this.clickGuiButtonList.addAll(overButtons);

        for (ClickGuiButton button : this.clickGuiButtonList) {
            button.drawButton(mc, mouseX, mouseY, partialTicks);
            button.setAlpha((int) this.alpha);
        }
        for (ClickGuiButton button : this.clickGuiButtonList) {
            button.afterDrawButton(mc, mouseX, mouseY, partialTicks);
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
    protected void keyTyped(char text, int keycode) {
        if (keycode == Keyboard.KEY_ESCAPE) {
            ModuleHandler.findModule(ClickGui.class).toggleModule();
            this.menuOpen = false;
            dragging = false;
        }
    }

    public void updateDisplayName() {
    }
}
