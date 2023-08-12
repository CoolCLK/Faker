package coolclk.faker.gui.clickgui;

import coolclk.faker.feature.api.*;
import coolclk.faker.gui.GuiHandler;
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
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (this.visible) {
            super.drawButton(mc, mouseX, mouseY, partialTicks);
            if (this.isMouseOver()) {
                if (mouseLeftClick) {
                    this.module.toggleModule();
                    this.backgroundColor = this.module.getEnable() ? GuiHandler.Theme.MODULE_BUTTON_ENABLE_BACKGROUND_COLOR : GuiHandler.Theme.MODULE_BUTTON_DISABLE_BACKGROUND_COLOR;
                }
                if (mouseRightClick) {
                    this.fold = !this.fold;
                }
            }
            this.currentHeight = this.height;
            if (!this.fold) {
                this.module.onClickGuiUpdate();

                List<Settings<?>> arguments = this.module.getSettings();
                int newWidth = GuiHandler.Theme.BUTTON_WIDTH;
                int mouseHoveredLine = (mouseX >= this.xPosition && mouseX <= this.xPosition + this.width) ? (int) ((mouseY - this.yPosition - this.height) / (fontrenderer.FONT_HEIGHT + (GuiHandler.Theme.MODULE_BUTTON_SETTINGS_MARGIN * 2))) : -1;
                int lineIndex = 0;
                for (Settings<?> argument : arguments) {
                    if (argument.getDisplayVisible()) {
                        int argumentCurrentLines = argument.getDisplayLines(), argumentCurrentHeight = (int) (argumentCurrentLines * (this.fontrenderer.FONT_HEIGHT + (GuiHandler.Theme.MODULE_BUTTON_SETTINGS_MARGIN * 2)));
                        drawRect(this.xPosition, this.yPosition + this.currentHeight, this.xPosition + this.width, this.yPosition + this.currentHeight + argumentCurrentHeight, ((this.alpha & 0xFF) << 24) | GuiHandler.Theme.MODULE_BUTTON_SETTINGS_BACKGROUND_COLOR);

                        String argumentName = argument.getDisplayName();
                        if (fontrenderer.getStringWidth(argumentName) > newWidth - (GuiHandler.Theme.MODULE_BUTTON_SETTINGS_MARGIN * 2)) {
                            newWidth = (int) (fontrenderer.getStringWidth(argumentName) + (GuiHandler.Theme.MODULE_BUTTON_SETTINGS_MARGIN * 2));
                        }

                        if (argument.rootOf(SettingsBoolean.class)) {
                            if (mouseHoveredLine == lineIndex && mouseLeftClick) {
                                ((SettingsBoolean) argument).toggleValue();
                            }
                            this.drawString(fontrenderer, argumentName, (int) (this.xPosition + GuiHandler.Theme.MODULE_BUTTON_SETTINGS_MARGIN), (int) (this.yPosition + this.currentHeight + GuiHandler.Theme.MODULE_BUTTON_SETTINGS_MARGIN), ((SettingsBoolean) argument).getValue() ? ((this.alpha & 0xFF) << 24) | GuiHandler.Theme.MODULE_BUTTON_SETTINGS_ENABLE_COLOR : ((this.alpha & 0xFF) << 24) | GuiHandler.Theme.MODULE_BUTTON_SETTINGS_DISABLE_COLOR);
                        } else if (argument.rootOf(SettingsMode.class)) {
                            if (mouseLeftClick) {
                                if (mouseHoveredLine == lineIndex) {
                                    ((SettingsMode<?>) argument).toggleDisplayFold();
                                } else if (mouseHoveredLine > lineIndex && mouseHoveredLine < lineIndex + argumentCurrentLines) {
                                    int lineModeIndex = mouseHoveredLine - lineIndex - 1;
                                    SettingsMode<Object> modeArgument = (SettingsMode<Object>) argument;
                                    modeArgument.setValue(modeArgument.getValues().get(lineModeIndex));
                                }
                            }
                            for (int drawLine = 0; drawLine < argumentCurrentLines; drawLine++) {
                                String display = argument.getDisplayName();
                                if (drawLine > 0) {
                                    display = ((SettingsMode<?>) argument).getModeDisplayName(drawLine - 1);
                                }
                                if (fontrenderer.getStringWidth(display) + (GuiHandler.Theme.MODULE_BUTTON_SETTINGS_MARGIN * 4) > newWidth - (GuiHandler.Theme.MODULE_BUTTON_SETTINGS_MARGIN * 2)) {
                                    newWidth = (int) (fontrenderer.getStringWidth(display) + (GuiHandler.Theme.MODULE_BUTTON_SETTINGS_MARGIN * 6));
                                }
                                this.drawString(fontrenderer, display, (int) (this.xPosition + (GuiHandler.Theme.MODULE_BUTTON_SETTINGS_MARGIN * (drawLine == 0 ? 1 : 3))), (int) (this.yPosition + this.currentHeight + GuiHandler.Theme.MODULE_BUTTON_SETTINGS_MARGIN + (drawLine * (this.fontrenderer.FONT_HEIGHT + GuiHandler.Theme.MODULE_BUTTON_SETTINGS_MARGIN * 2))), (drawLine == 0 || ((SettingsMode<?>) argument).getValueDisplayLine() == drawLine - 1) ? ((this.alpha & 0xFF) << 24) | GuiHandler.Theme.MODULE_BUTTON_SETTINGS_ENABLE_COLOR : ((this.alpha & 0xFF) << 24) | GuiHandler.Theme.MODULE_BUTTON_SETTINGS_DISABLE_COLOR);
                            }
                        } else if (argument.rootOf(SettingsNumber.class)) {
                            if (mouseHoveredLine == lineIndex + 1 && mouseLeftDrag) {
                                double percent = (mouseX - this.xPosition - GuiHandler.Theme.MODULE_BUTTON_SETTINGS_MARGIN) / (this.width - (GuiHandler.Theme.MODULE_BUTTON_SETTINGS_MARGIN * 2));
                                ((SettingsNumber<?>) argument).setValueByPercent(percent);
                            }
                            this.drawString(fontrenderer, argumentName, (int) (this.xPosition + GuiHandler.Theme.MODULE_BUTTON_SETTINGS_MARGIN), (int) (this.yPosition + this.currentHeight + GuiHandler.Theme.MODULE_BUTTON_SETTINGS_MARGIN), 0xFFFFFFFF);
                            String argumentValue = argument.getDisplayValue();
                            if (fontrenderer.getStringWidth(argumentName + "  " + argumentValue) > newWidth - (GuiHandler.Theme.MODULE_BUTTON_SETTINGS_MARGIN * 2)) {
                                newWidth += fontrenderer.getStringWidth("  " + argumentValue);
                            }
                            this.drawString(fontrenderer, argumentValue, (int) (this.xPosition + this.width - GuiHandler.Theme.MODULE_BUTTON_SETTINGS_MARGIN - fontrenderer.getStringWidth(argumentValue)), (int) (this.yPosition + this.currentHeight + GuiHandler.Theme.MODULE_BUTTON_SETTINGS_MARGIN), 0xFFFFFFFF);
                            this.drawHorizontalSlideBar((int) (this.xPosition + GuiHandler.Theme.MODULE_BUTTON_SETTINGS_MARGIN), (int) (this.xPosition + this.width - GuiHandler.Theme.MODULE_BUTTON_SETTINGS_MARGIN), this.yPosition + this.currentHeight + ((argumentCurrentHeight + this.height) / 2), ((SettingsNumber<?>) argument).getValuePercent(), ((this.alpha & 0xFF) << 24) | GuiHandler.Theme.MODULE_BUTTON_ENABLE_BACKGROUND_COLOR);
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
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.currentHeight;
        }
    }

    @Override
    public void afterDrawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        int mouseXOffset = 0, mouseYOffset = -7;
        if (this.isMouseOver()) {
            String description = module.getDisplayDescription();
            if (description != null && !description.equals(module.getDescriptionTranslateKey())) {
                drawRect(mouseX - mouseXOffset, mouseY - mouseYOffset, mouseX - mouseXOffset + this.fontrenderer.getStringWidth(description), mouseY - mouseYOffset + this.fontrenderer.FONT_HEIGHT, ((this.alpha & 0xFF) << 24) | GuiHandler.Theme.MODULE_BUTTON_DESCRIPTION_BACKGROUND_COLOR);
                this.drawString(this.fontrenderer, description, mouseX - mouseXOffset, mouseY - mouseYOffset, ((this.alpha & 0xFF) << 24) | this.textColor);
            }
        }
    }

    @Override
    public void updateDisplayName() {
        this.displayString = module.getDisplayName();
    }
}
