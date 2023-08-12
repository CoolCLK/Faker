package coolclk.faker.gui.clickgui;

import coolclk.faker.gui.GuiHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.input.Mouse;

public class ClickGuiButton extends GuiButton {
    protected int backgroundColor = 0xFFFFFF;
    private int currentBackgroundColor = 0xFFFFFF;
    protected int textColor = 0x000000;
    protected int alpha = 255;
    protected FontRenderer fontrenderer = Minecraft.getMinecraft().fontRendererObj;

    protected boolean mouseLeftClick = false;
    protected boolean mouseRightClick = false;
    protected boolean mouseLeftDrag = false;
    protected boolean mouseRightDrag = false;

    public ClickGuiButton(int x, int y) {
        super(0, x, y, GuiHandler.Theme.BUTTON_WIDTH, GuiHandler.Theme.BUTTON_HEIGHT, "");
    }

    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        boolean mouseLeftDown = Mouse.isCreated() && Mouse.isButtonDown(0), mouseRightDown = Mouse.isCreated() && Mouse.isButtonDown(1);
        mouseLeftClick = mouseLeftDown && !mouseLeftDrag;
        mouseRightClick = mouseRightDown && !mouseRightDrag;
        mouseLeftDrag = mouseLeftDown;
        mouseRightDrag = mouseRightDown;

        this.fontrenderer = mc.fontRendererObj;
        this.hovered = false;
        if (this.visible) {
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;

            this.currentBackgroundColor = GuiHandler.easeColorRGB(this.currentBackgroundColor, this.backgroundColor, GuiHandler.easeOutQuad(1));
            drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, ((this.alpha & 0xFF) << 24) | this.currentBackgroundColor);
            this.drawCenteredString(this.fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, ((this.alpha & 0xFF) << 24) | this.textColor);
        }
    }

    public void afterDrawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {

    }

    public void drawHorizontalSlideBar(int startX, int endX, int y, double per, int color) {
        this.drawHorizontalLine(startX, endX, y, color);

        int buttonStartX = startX + ((int) Math.round((double) (endX - startX) * per));
        for (int[] buttonSize : new int[][] { new int[] { 5, ((this.alpha & 0xFF) << 24) | 0xFFFFFF }, new int[] { 4, color } , new int[] { 2, ((this.alpha & 0xFF) << 24) | 0xFFFFFF } }) {
            drawRect(buttonStartX - (buttonSize[0] / 2), y - (buttonSize[0] / 2), buttonStartX + (buttonSize[0] / 2), y + (buttonSize[0] / 2), buttonSize[1]);
        }
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public void updateDisplayName() {

    }

    public boolean isCategoryButton() {
        return false;
    }
}
