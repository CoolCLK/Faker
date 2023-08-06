package coolclk.gui.clickgui;

import coolclk.gui.GuiHandler;
import coolclk.gui.InputHandler;
import coolclk.modules.Module;
import coolclk.modules.ModuleType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;

import java.util.ArrayList;
import java.util.List;

public class ClickGuiButton extends GuiButton {
    public int backgroundColor;
    private final int[] backgroundColors;
    private final int textColor;

    private final boolean canDrag;
    private boolean fold = true; // For module

    private final boolean isGroup;
    private final Module module;
    private final ModuleType moduleType;
    private ClickGuiButton groupModule;
    private final List<ClickGuiButton> groupSubmodules;

    private boolean dragging = false;
    private int dragOriginX = 0, dragOriginY = 0;

    private final static int argumentsHeight = 10;
    private final static int argumentsMargin = 2;
    public int currentHeight = this.height;

    public ClickGuiButton(int x, int y, int widthIn, int heightIn, String buttonText, int[] backgroundColors, int textColor, Module myModule, ModuleType myGroup) {
        super(0, x, y, widthIn, heightIn, buttonText);
        this.backgroundColors = backgroundColors;
        this.textColor = textColor;
        this.canDrag = false;
        this.module = myModule;
        this.moduleType = myGroup;
        this.isGroup = false;
        this.groupSubmodules = new ArrayList<ClickGuiButton>();
    }

    public ClickGuiButton(int x, int y, int widthIn, int heightIn, String buttonText, int backgroundColors, int textColor, ModuleType myModuleType, List<ClickGuiButton> submodules) {
        super(0, x, y, widthIn, heightIn, buttonText);
        this.backgroundColors = new int[] {backgroundColors};
        this.textColor = textColor;
        this.canDrag = true;
        this.module = null;
        this.moduleType = myModuleType;
        this.isGroup = true;
        this.groupSubmodules = submodules;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        this.visible = this.isGroup || !this.moduleType.getFold();
        if (this.isMouseOver()) {
            if (this.isGroup) {
                if (InputHandler.isMousePressed(InputHandler.BUTTON_RIGHT)) {
                    moduleType.setFold(!moduleType.getFold());
                }
            } else {
                assert this.module != null;
                if (InputHandler.isMousePressed(InputHandler.BUTTON_LEFT)) {
                    module.setEnable(!module.getEnable());
                } else if (InputHandler.isMousePressed(InputHandler.BUTTON_RIGHT)) {
                    this.fold = !this.fold;
                }
            }
        }
        if (this.visible) {
            FontRenderer fontrenderer = mc.fontRendererObj;
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            this.mouseDragged(mc, mouseX, mouseY);

            if (isGroup) {
                int yPosition = this.yPosition + this.height;
                for (ClickGuiButton submodule : this.groupSubmodules) {
                    submodule.groupModule = this;
                    submodule.width = this.width;
                    submodule.xPosition = this.xPosition;
                    submodule.yPosition = yPosition;
                    yPosition += submodule.currentHeight;
                }
            } else {
                assert this.module != null;
                this.currentHeight = this.height;
                if (!fold) {
                    List<Module.ModuleArgument> arguments = module.getArguments();
                    int newWidth = this.width;
                    for (Module.ModuleArgument argument : arguments) {
                        int argumentCurrentHeight = argumentsHeight * (argument.getValueType() == Module.ModuleArgument.ArgumentType.NUMBER ? 2 : 1);
                        boolean hovering = mouseX >= this.xPosition && mouseX <= this.xPosition + this.width && mouseY >= this.yPosition + this.currentHeight + (argument.getValueType() == Module.ModuleArgument.ArgumentType.NUMBER ? argumentCurrentHeight : 0) && mouseY <= this.yPosition + this.currentHeight + argumentCurrentHeight;
                        drawRect(this.xPosition, this.yPosition + this.currentHeight, this.xPosition + this.width, this.yPosition + this.currentHeight + argumentCurrentHeight, backgroundColors[2]);
                        String argumentName = I18n.format("modules.module." + module.getName() + "." + argument.getName() + ".name");
                        if (mc.fontRendererObj.getStringWidth(argumentName) > newWidth - (argumentsMargin * 2)) {
                            newWidth = mc.fontRendererObj.getStringWidth(argumentName) + (argumentsMargin * 2);
                        }
                        switch (argument.getValueType()) {
                            case SWITCH: {
                                if (hovering && InputHandler.isMousePressed(InputHandler.BUTTON_LEFT)) {
                                    argument.toggleBooleanValue();
                                }
                                this.drawString(mc.fontRendererObj, I18n.format("modules.module." + module.getName() + "." + argument.getName() + ".name"), this.xPosition + argumentsMargin, this.yPosition + this.currentHeight + argumentsMargin, argument.getBooleanValue() ? 0xFFFFFFFF : 0xFF333333);
                                break;
                            }
                            case NUMBER: {
                                if (hovering && InputHandler.isMousePressing(InputHandler.BUTTON_LEFT)) {
                                    argument.setNumberValue((double) Math.round(argument.getNumberMinValue() + (argument.getNumberMaxValue() - argument.getNumberMinValue()) * ((double) (mouseX - this.xPosition + argumentsMargin) / (this.width - (argumentsMargin * 2))) * 10) / 10);
                                }
                                this.drawString(mc.fontRendererObj, argumentName, this.xPosition + argumentsMargin, this.yPosition + this.currentHeight + argumentsMargin, 0xFFFFFFFF);
                                String argumentValue = Double.toString(argument.getNumberValue());
                                if (mc.fontRendererObj.getStringWidth(argumentName + " " + argumentValue) > newWidth - (argumentsMargin * 2)) {
                                    newWidth += mc.fontRendererObj.getStringWidth(argumentValue);
                                }
                                this.drawString(mc.fontRendererObj, argumentValue, this.xPosition + this.width - argumentsMargin - mc.fontRendererObj.getStringWidth(argumentValue), this.yPosition + this.currentHeight + argumentsMargin, 0xFFFFFFFF);
                                this.currentHeight += argumentsHeight;
                                this.drawHorizontalSlideBar(this.xPosition + argumentsMargin, this.xPosition + this.width - argumentsMargin, this.yPosition + this.currentHeight + (this.height / 2), (argument.getNumberValue() - argument.getNumberMinValue()) / (argument.getNumberMaxValue() - argument.getNumberMinValue()), backgroundColors[1]);
                                break;
                            }
                        }
                        this.currentHeight += argumentsHeight;
                    }
                    if (newWidth != this.width) {
                        groupModule.setWidth(newWidth);
                    }
                }
            }
            backgroundColor = GuiHandler.easeColorRGBA(this.backgroundColor, backgroundColors[isGroup ? 0 : (module.getEnable() ? 1 : 0)], GuiHandler.easeOutQuad(1));
            drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, this.backgroundColor);
            this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, textColor);
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

    @Override
    public void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
        if (!ClickGuiContainer.dragging) {
            if (!this.dragging && this.isMouseOver() && InputHandler.isMousePressing(InputHandler.BUTTON_LEFT)) {
                this.dragOriginX = this.xPosition - mouseX;
                this.dragOriginY = this.yPosition - mouseY;
                this.dragging = true;
                ClickGuiContainer.dragging = true;
            } else if (this.dragging && !InputHandler.isMousePressing(InputHandler.BUTTON_LEFT)) {
                this.dragging = false;
                ClickGuiContainer.dragging = false;
            }
            if (this.dragging) {
                if (this.canDrag) {
                    this.xPosition = mouseX + this.dragOriginX;
                    this.yPosition = mouseY + this.dragOriginY;
                    assert this.moduleType != null;
                    moduleType.setClickGuiPosition(this.xPosition, this.yPosition);
                }
            }
        }
    }
}
