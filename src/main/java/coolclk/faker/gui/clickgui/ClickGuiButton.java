package coolclk.faker.gui.clickgui;

import coolclk.faker.gui.GuiHandler;
import coolclk.faker.gui.InputHandler;
import coolclk.faker.modules.Module;
import coolclk.faker.modules.ModuleHandler;
import coolclk.faker.modules.ModuleType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class ClickGuiButton extends GuiButton {
    private int alpha = 255;
    public int defaultWidth;

    public int backgroundColor;
    private final int[] backgroundColors;
    private final int textColor;

    private final boolean canDrag;
    private boolean fold = true; // For module

    private final boolean isGroup;
    public final Module module;
    private final ModuleType moduleType;
    private final List<ClickGuiButton> groupSubmodules;

    private boolean dragging = false;
    private int dragOriginX = 0, dragOriginY = 0;

    private final static int argumentsHeight = 10;
    private final static int argumentsMargin = 2;

    private double currentWidth = this.width;
    public int moduleWidth;
    public int currentHeight = this.height;

    public ClickGuiButton(int x, int y, int widthIn, int heightIn, String buttonText, int[] backgroundColors, int textColor, Module myModule, ModuleType myGroup) {
        super(0, x, y, widthIn, heightIn, buttonText);
        this.defaultWidth = widthIn;
        this.moduleWidth = this.defaultWidth;
        this.backgroundColors = backgroundColors;
        this.textColor = textColor;
        this.canDrag = false;
        this.module = myModule;
        this.moduleType = myGroup;
        this.isGroup = false;
        this.groupSubmodules = new ArrayList<ClickGuiButton>();
    }

    public ClickGuiButton(int x, int y, int widthIn, int heightIn, String buttonText, int backgroundColor, int textColor, ModuleType myModuleType, List<ClickGuiButton> submodules) {
        super(0, x, y, widthIn, heightIn, buttonText);
        this.defaultWidth = widthIn;
        this.moduleWidth = this.defaultWidth;
        this.backgroundColors = new int[] {backgroundColor};
        this.textColor = textColor;
        this.canDrag = true;
        this.module = null;
        this.moduleType = myModuleType;
        this.isGroup = true;
        this.groupSubmodules = submodules;
    }

    @Override
    public void drawButton(@Nonnull Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        this.visible = this.isGroup || !this.moduleType.getFold();
        if (this.visible) {
            if (this.isMouseOver()) {
                if (this.isGroup) {
                    if (InputHandler.isMousePressed(InputHandler.BUTTON_RIGHT)) {
                        moduleType.setFold(!moduleType.getFold());
                    }
                } else {
                    assert this.module != null;
                    if (InputHandler.isMousePressed(InputHandler.BUTTON_LEFT)) {
                        module.toggleModule();
                    } else if (InputHandler.isMousePressed(InputHandler.BUTTON_RIGHT)) {
                        this.fold = !this.fold;
                    }
                }
            }
            FontRenderer fontrenderer = mc.fontRenderer;
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            if (!this.dragging && !ClickGuiScreen.dragging && this.isMouseOver() && InputHandler.isMousePressing(InputHandler.BUTTON_LEFT)) {
                this.dragOriginX = this.x - mouseX;
                this.dragOriginY = this.y - mouseY;
                this.dragging = true;
                ClickGuiScreen.dragging = true;
            } else if (ClickGuiScreen.dragging && !InputHandler.isMousePressing(InputHandler.BUTTON_LEFT)) {
                this.dragging = false;
                ClickGuiScreen.dragging = false;
            }
            if (this.dragging) {
                if (this.canDrag) {
                    this.x = mouseX + this.dragOriginX;
                    this.y = mouseY + this.dragOriginY;
                    assert this.moduleType != null;
                    moduleType.setClickGuiPosition(this.x, this.y);
                }
            }

            int buttonSideMargin = 10;
            if (this.defaultWidth - buttonSideMargin < fontrenderer.getStringWidth(I18n.format(this.displayString))) {
                this.defaultWidth = fontrenderer.getStringWidth(I18n.format(this.displayString)) + buttonSideMargin;
            }
            if (isGroup) {
                int y = this.y + this.height, targetWidth = this.defaultWidth;
                for (ClickGuiButton submodule : this.groupSubmodules) {
                    if (submodule.moduleWidth > targetWidth) {
                        targetWidth = submodule.moduleWidth;
                    }
                }
                this.currentWidth += ((targetWidth - this.currentWidth) * GuiHandler.easeOutQuad(0.75));
                this.width = (int) Math.round(this.currentWidth);
                for (ClickGuiButton submodule : this.groupSubmodules) {
                    submodule.width = this.width;
                    submodule.x = this.x;
                    submodule.y = y;
                    y += submodule.currentHeight;
                }
            } else {
                assert this.module != null;
                this.currentHeight = this.height;
                if (!fold) {
                    List<Module.ModuleArgument> arguments = module.getArguments();
                    int newWidth = this.defaultWidth;
                    for (Module.ModuleArgument argument : arguments) {
                        if (argument.getVisible()) {
                            int argumentCurrentHeight = argumentsHeight * (argument.getValueType() == Module.ModuleArgument.ArgumentType.NUMBER ? 2 : 1);
                            boolean hovering = mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y + this.currentHeight + argumentCurrentHeight - argumentsHeight && mouseY <= this.y + this.currentHeight + argumentCurrentHeight;
                            drawRect(this.x, this.y + this.currentHeight, this.x + this.width, this.y + this.currentHeight + argumentCurrentHeight, ((this.alpha & 0xFF) << 24) | backgroundColors[2]);
                            String argumentName = I18n.format("modules.module." + module.getName() + "." + argument.getName() + ".name");
                            if (fontrenderer.getStringWidth(argumentName) > newWidth - (argumentsMargin * 2)) {
                                newWidth = fontrenderer.getStringWidth(argumentName) + (argumentsMargin * 2);
                            }
                            switch (argument.getValueType()) {
                                case SWITCH: {
                                    if (hovering && InputHandler.isMousePressed(InputHandler.BUTTON_LEFT)) {
                                        argument.toggleBooleanValue();
                                    }
                                    this.drawString(fontrenderer, I18n.format("modules.module." + module.getName() + "." + argument.getName() + ".name"), this.x + argumentsMargin, this.y + this.currentHeight + argumentsMargin, argument.getBooleanValue() ? 0xFFFFFFFF : 0xFF333333);
                                    break;
                                }
                                case PERCENT:
                                case NUMBER: {
                                    if (hovering && InputHandler.isMousePressing(InputHandler.BUTTON_LEFT)) {
                                        double unformattedValue = argument.getNumberMinValue() + ((argument.getNumberMaxValue() - argument.getNumberMinValue()) * ((double) (mouseX - this.x - argumentsMargin) / (this.width - (argumentsMargin * 2))));
                                        argument.setNumberValue(Math.floor(unformattedValue * 100) / 100);
                                        ModuleHandler.saveConfigs();
                                    }
                                    this.drawString(fontrenderer, argumentName, this.x + argumentsMargin, this.y + this.currentHeight + argumentsMargin, 0xFFFFFFFF);
                                    String argumentValue = Double.toString(argument.getNumberValueD());
                                    if (argument.getValueType() == Module.ModuleArgument.ArgumentType.PERCENT) {
                                        argumentValue = (argument.getNumberValueD() * 100) + "%";
                                    }
                                    if (fontrenderer.getStringWidth(argumentName + "  " + argumentValue) > newWidth - (argumentsMargin * 2)) {
                                        newWidth += fontrenderer.getStringWidth("  " + argumentValue);
                                    }
                                    this.drawString(fontrenderer, argumentValue, this.x + this.width - argumentsMargin - fontrenderer.getStringWidth(argumentValue), this.y + this.currentHeight + argumentsMargin, 0xFFFFFFFF);
                                    this.currentHeight += argumentsHeight;
                                    this.drawHorizontalSlideBar(this.x + argumentsMargin, this.x + this.width - argumentsMargin, this.y + this.currentHeight + (this.height / 2), (argument.getNumberValueD() - argument.getNumberMinValue()) / (argument.getNumberMaxValue() - argument.getNumberMinValue()), ((this.alpha & 0xFF) << 24) | backgroundColors[1]);
                                    break;
                                }
                            }
                            this.currentHeight += argumentsHeight;
                        }
                    }
                    if (newWidth > this.defaultWidth && newWidth != this.moduleWidth) {
                        this.moduleWidth = newWidth;
                    }
                } else {
                    this.moduleWidth = this.defaultWidth;
                }
            }
            backgroundColor = GuiHandler.easeColorRGBA(this.backgroundColor, ((this.alpha & 0xFF) << 24) | backgroundColors[isGroup ? 0 : (module.getEnable() ? 1 : 0)], GuiHandler.easeOutQuad(1));
            drawRect(this.x, this.y, this.x + this.width, this.y + this.height, ((this.alpha & 0xFF) << 24) | this.backgroundColor);
            this.drawCenteredString(fontrenderer, I18n.format(this.displayString), this.x + this.width / 2, this.y + (this.height - 8) / 2, ((this.alpha & 0xFF) << 24) | this.textColor);
        }
    }

    public void drawHorizontalSlideBar(int startX, int endX, int y, double per, int color) {
        this.drawHorizontalLine(startX, endX, y, color);

        int buttonStartX = startX + ((int) Math.round((double) (endX - startX) * per));
        for (int[] buttonSize : new int[][] { new int[] { 5, 0xFFFFFFFF }, new int[] { 4, color } , new int[] { 2, 0xFFFFFFFF } }) {
            drawRect(buttonStartX - (buttonSize[0] / 2), y - (buttonSize[0] / 2), buttonStartX + (buttonSize[0] / 2), y + (buttonSize[0] / 2), buttonSize[1]);
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY) {
        this.dragging = false;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }
}
