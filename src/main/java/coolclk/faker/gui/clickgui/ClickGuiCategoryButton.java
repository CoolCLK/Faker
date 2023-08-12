package coolclk.faker.gui.clickgui;

import coolclk.faker.feature.modules.ModuleCategory;
import coolclk.faker.gui.GuiHandler;
import net.minecraft.client.Minecraft;

import java.util.List;

public class ClickGuiCategoryButton extends ClickGuiButton {
    private final ModuleCategory moduleCategory;
    public final List<ClickGuiModuleButton> subButtons;
    private boolean dragging = false;
    private int dragOriginX = 0, dragOriginY = 0;
    public boolean moveOver = false;
    private double currentWidth = this.width;

    public ClickGuiCategoryButton(int x, int y, ModuleCategory category, List<ClickGuiModuleButton> subModuleButtons) {
        super(x, y);
        this.backgroundColor = GuiHandler.Theme.CATEGORY_BUTTON_BACKGROUND_COLOR;
        this.textColor = 0xFFFFFF;
        this.moduleCategory = category;
        this.subButtons = subModuleButtons;
        this.updateDisplayName();
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        super.drawButton(mc, mouseX, mouseY, partialTicks);
        if (this.isMouseOver()) {
            if (mouseRightClick) {
                this.moveOver = true;
                moduleCategory.toggleDisplayFold();
                for (ClickGuiModuleButton button : this.subButtons) {
                    button.visible = !moduleCategory.getDisplayFold();
                }
            }
        }
        if (!this.dragging && !ClickGuiScreen.dragging && this.isMouseOver() && mouseLeftDrag) {
            this.dragOriginX = this.xPosition - mouseX;
            this.dragOriginY = this.yPosition - mouseY;
            this.dragging = true;
            ClickGuiScreen.dragging = true;
        }
        if ((ClickGuiScreen.dragging || this.dragging) && !mouseLeftDrag) {
            this.dragging = false;
            ClickGuiScreen.dragging = false;
        }
        if (this.dragging) {
            this.xPosition = mouseX + this.dragOriginX;
            this.yPosition = mouseY + this.dragOriginY;
            moduleCategory.setPosition(this.xPosition, this.yPosition);
            this.moveOver = true;
        }
        int yPosition = this.yPosition + this.height, targetWidth = GuiHandler.Theme.BUTTON_WIDTH;
        for (ClickGuiModuleButton submodule : this.subButtons) {
            if (submodule.width > targetWidth) {
                targetWidth = submodule.width;
            }
        }
        this.currentWidth += ((targetWidth - this.currentWidth) * GuiHandler.easeOutQuad(0.75));
        this.width = (int) Math.round(this.currentWidth);
        for (ClickGuiModuleButton submodule : this.subButtons) {
            submodule.width = this.width;
            submodule.xPosition = this.xPosition;
            submodule.yPosition = yPosition;
            yPosition += submodule.currentHeight;
        }
    }

    @Override
    public void updateDisplayName() {
        this.displayString = moduleCategory.getDisplayName();
    }

    @Override
    public boolean isCategoryButton() {
        return true;
    }
}
