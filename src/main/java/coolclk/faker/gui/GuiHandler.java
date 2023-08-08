package coolclk.faker.gui;

import coolclk.faker.gui.clickgui.ClickGuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import java.awt.*;

public class GuiHandler implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == ClickGuiScreen.ID) {
            return ClickGuiScreen.INSTANCE;
        }
        return null;
    }

    public static int getRainbowColor(double speed) {
        return Color.getHSBColor((float) Math.abs(Math.sin(System.currentTimeMillis() * speed) * 255), 255, 255).getRGB();
    }

    public static double easeOutQuad(double speed) {
        return Math.sin(((0.05 * (speed)) * Math.PI) / 2);
    }

    public static int easeColorRGBA(int originalColor, int targetColor, double speed) {
        Color from = new Color(originalColor, true), to = new Color(targetColor, true);
        int r = from.getRed(), g = from.getBlue(), b = from.getBlue(), a = from.getAlpha();
        r += (int) Math.round((to.getRed() - r) * speed);
        g += (int) Math.round((to.getGreen() - g) * speed);
        b += (int) Math.round((to.getBlue() - b) * speed);
        a += (int) Math.round((to.getAlpha() - a) * speed);
        return new Color(r, g, b, a).getRGB();
    }
}