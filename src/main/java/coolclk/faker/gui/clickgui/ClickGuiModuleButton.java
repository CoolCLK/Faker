package coolclk.faker.gui.clickgui;

import coolclk.faker.feature.ModuleHandler;
import coolclk.faker.feature.api.*;
import coolclk.faker.gui.GuiHandler;
import coolclk.faker.gui.InputHandler;
import net.minecraft.client.Minecraft;

import java.util.List;

public class ClickGuiModuleButton extends ClickGuiButton {
    private boolean fold = true;

    private final Module module;

    public int currentHeight = this.height;

    public ClickGuiModuleButton(int x, int y, Module module) {
        super(x, y);
        this.backgroundColor = GuiHandler.Theme.MODULE_BUTTON_DISABLE_BACKGROUND_COLOR;
        this.textColor = 0xFFFFFF;
        this.module = module;
        this.updateDisplayName();
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            super.drawButton(mc, mouseX, mouseY);
            if (this.isMouseOver()) {
                if (InputHandler.isMousePressed(InputHandler.BUTTON_LEFT)) {
                    this.module.toggleModule();
                    this.backgroundColor = this.module.getEnable() ? GuiHandler.Theme.MODULE_BUTTON_ENABLE_BACKGROUND_COLOR : GuiHandler.Theme.MODULE_BUTTON_DISABLE_BACKGROUND_COLOR;
                }
                if (InputHandler.isMousePressed(InputHandler.BUTTON_RIGHT)) {
                    this.fold = !this.fold;
                }
            }
            this.currentHeight = this.height;
            if (!this.fold) {
                this.module.onClickGuiUpdate();

                List<Settings<?>> arguments = this.module.getSettings();
                int newWidth = GuiHandler.Theme.BUTTON_WIDTH;
                int mouseHoveredLine = (mouseX >= this.xPosition && mouseX <= this.xPosition + this.width) ? ((mouseY - this.yPosition - this.height) / (fontrenderer.FONT_HEIGHT + (GuiHandler.Theme.MODULE_BUTTON_SETTINGS_MARGIN * 2))) : -1;
                int lineIndex = 0;
                for (Settings<?> argument : arguments) {
                    if (argument.getDisplayVisible()) {
                        int argumentCurrentLines = argument.getDisplayLines(), argumentCurrentHeight = argumentCurrentLines * (this.fontrenderer.FONT_HEIGHT + (GuiHandler.Theme.MODULE_BUTTON_SETTINGS_MARGIN * 2));
                        drawRect(this.xPosition, this.yPosition + this.currentHeight, this.xPosition + this.width, this.yPosition + this.currentHeight + argumentCurrentHeight, ((this.alpha & 0xFF) << 24) | GuiHandler.Theme.MODULE_BUTTON_SETTINGS_BACKGROUND_COLOR);

                        String argumentName = argument.getDisplayName();
                        if (fontrenderer.getStringWidth(argumentName) > newWidth - (GuiHandler.Theme.MODULE_BUTTON_SETTINGS_MARGIN * 2)) {
                            newWidth = fontrenderer.getStringWidth(argumentName) + (GuiHandler.Theme.MODULE_BUTTON_SETTINGS_MARGIN * 2);
                        }

                        if (argument.rootOf(SettingsBoolean.class)) {
                            if (mouseHoveredLine == lineIndex && InputHandler.isMousePressed(InputHandler.BUTTON_LEFT)) {
                                ((SettingsBoolean) argument).toggleValue();
                            }
                            this.drawString(fontrenderer, argumentName, this.xPosition + GuiHandler.Theme.MODULE_BUTTON_SETTINGS_MARGIN, this.yPosition + this.currentHeight + GuiHandler.Theme.MODULE_BUTTON_SETTINGS_MARGIN, ((SettingsBoolean) argument).getValue() ? ((this.alpha & 0xFF) << 24) | GuiHandler.Theme.MODULE_BUTTON_SETTINGS_ENABLE_COLOR : ((this.alpha & 0xFF) << 24) | GuiHandler.Theme.MODULE_BUTTON_SETTINGS_DISABLE_COLOR);
                        } else if (argument.rootOf(SettingsMode.class)) {
                            if (InputHandler.isMousePressed(InputHandler.BUTTON_LEFT)) {
                                if (mouseHoveredLine == lineIndex) {
                                    ((SettingsMode<?>) argument).toggleDisplayFold();
                                } else if (mouseHoveredLine > lineIndex && mouseHoveredLine < lineIndex + argumentCurrentLines) {
                                    int lineModeIndex = mouseHoveredLine - lineIndex - 1;
                                    if (argument.rootOf(SettingsModeString.class)) {
                                        ((SettingsModeString) argument).setValue(((SettingsModeString) argument).getValues().get(lineModeIndex));
                                    }
                                }
                            }
                            for (int line = 0; line < argumentCurrentLines; line++) {
                                String display = argument.getDisplayName();
                                if (line > 0) {
                                    display = ((SettingsMode<?>) argument).getModeDisplayName(line - 1);
                                }
                                if (fontrenderer.getStringWidth(display) > newWidth - (GuiHandler.Theme.MODULE_BUTTON_SETTINGS_MARGIN * 2)) {
                                    newWidth = fontrenderer.getStringWidth(display) + (GuiHandler.Theme.MODULE_BUTTON_SETTINGS_MARGIN * 2);
                                }
                                this.drawString(fontrenderer, display, this.xPosition + GuiHandler.Theme.MODULE_BUTTON_SETTINGS_MARGIN, this.yPosition + this.currentHeight + GuiHandler.Theme.MODULE_BUTTON_SETTINGS_MARGIN + (line * (this.fontrenderer.FONT_HEIGHT + GuiHandler.Theme.MODULE_BUTTON_SETTINGS_MARGIN * 2)), (lineIndex == 0 || argument.getValue().equals(((SettingsMode<?>) argument).getValues().get(lineIndex - 1))) ? ((this.alpha & 0xFF) << 24) | GuiHandler.Theme.MODULE_BUTTON_SETTINGS_ENABLE_COLOR : ((this.alpha & 0xFF) << 24) | GuiHandler.Theme.MODULE_BUTTON_SETTINGS_DISABLE_COLOR);
                            }
                        } else if (argument.rootOf(SettingsNumber.class)) {
                            if (mouseHoveredLine == lineIndex + 1 && InputHandler.isMousePressing(InputHandler.BUTTON_LEFT)) {
                                if (argument.rootOf(SettingsInteger.class)) {
                                    ((SettingsInteger) argument).setValue((int) (((SettingsInteger) argument).getMinValue() + ((((SettingsInteger) argument).getMaxValue() - ((SettingsInteger) argument).getMinValue()) * ((double) (mouseX - this.xPosition - GuiHandler.Theme.MODULE_BUTTON_SETTINGS_MARGIN) / (this.width - (GuiHandler.Theme.MODULE_BUTTON_SETTINGS_MARGIN * 2))))));
                                } else if (argument.rootOf(SettingsFloat.class)) {
                                    ((SettingsFloat) argument).setValue((float) (((SettingsFloat) argument).getMinValue() + ((((SettingsFloat) argument).getMaxValue() - ((SettingsFloat) argument).getMinValue()) * ((double) (mouseX - this.xPosition - GuiHandler.Theme.MODULE_BUTTON_SETTINGS_MARGIN) / (this.width - (GuiHandler.Theme.MODULE_BUTTON_SETTINGS_MARGIN * 2))))));
                                } else if (argument.rootOf(SettingsDouble.class)) {
                                    ((SettingsDouble) argument).setValue(((SettingsDouble) argument).getMinValue() + ((((SettingsDouble) argument).getMaxValue() - ((SettingsDouble) argument).getMinValue()) * ((double) (mouseX - this.xPosition - GuiHandler.Theme.MODULE_BUTTON_SETTINGS_MARGIN) / (this.width - (GuiHandler.Theme.MODULE_BUTTON_SETTINGS_MARGIN * 2)))));
                                }
                                ModuleHandler.saveConfigs();
                            }
                            this.drawString(fontrenderer, argumentName, this.xPosition + GuiHandler.Theme.MODULE_BUTTON_SETTINGS_MARGIN, this.yPosition + this.currentHeight + GuiHandler.Theme.MODULE_BUTTON_SETTINGS_MARGIN, 0xFFFFFFFF);
                            String argumentValue = argument.getDisplayValue();
                            if (fontrenderer.getStringWidth(argumentName + "  " + argumentValue) > newWidth - (GuiHandler.Theme.MODULE_BUTTON_SETTINGS_MARGIN * 2)) {
                                newWidth += fontrenderer.getStringWidth("  " + argumentValue);
                            }
                            this.drawString(fontrenderer, argumentValue, this.xPosition + this.width - GuiHandler.Theme.MODULE_BUTTON_SETTINGS_MARGIN - fontrenderer.getStringWidth(argumentValue), this.yPosition + this.currentHeight + GuiHandler.Theme.MODULE_BUTTON_SETTINGS_MARGIN, 0xFFFFFFFF);
                            this.drawHorizontalSlideBar(this.xPosition + GuiHandler.Theme.MODULE_BUTTON_SETTINGS_MARGIN, this.xPosition + this.width - GuiHandler.Theme.MODULE_BUTTON_SETTINGS_MARGIN, this.yPosition + this.currentHeight + ((argumentCurrentHeight + this.height) / 2), ((SettingsNumber<?>) argument).getValuePercent().doubleValue(), ((this.alpha & 0xFF) << 24) | GuiHandler.Theme.MODULE_BUTTON_ENABLE_BACKGROUND_COLOR);
                        }
                        lineIndex += argument.getDisplayLines();
                        this.currentHeight += argumentCurrentHeight;
                    }
                }
                if (newWidth > GuiHandler.Theme.BUTTON_WIDTH && newWidth != this.width) {
                    this.width = newWidth;
                }
            } else {
                this.width = GuiHandler.Theme.BUTTON_WIDTH;
            }
        }
    }

    @Override
    public void afterDrawButton(Minecraft mc, int mouseX, int mouseY) {
        int mouseXOffset = -5, mouseYOffset = 0;
        if (this.isMouseOver()) {
            String description = module.getDisplayDescription();
            if (description != null && !description.equals(module.getDescriptionTranslateKey())) {
                drawRect(mouseX - mouseXOffset, mouseY - mouseYOffset, mouseX - mouseXOffset + this.fontrenderer.getStringWidth(description), mouseY - mouseYOffset + this.fontrenderer.FONT_HEIGHT, ((this.alpha & 0xFF) << 24) | GuiHandler.Theme.MODULE_BUTTON_DESCRIPTION_BACKGROUND_COLOR);
                this.drawString(this.fontrenderer, description, mouseX, mouseY, ((this.alpha & 0xFF) << 24) | this.textColor);
            }
        }
    }

    @Override
    public void updateDisplayName() {
        this.displayString = module.getDisplayName();
    }
}
