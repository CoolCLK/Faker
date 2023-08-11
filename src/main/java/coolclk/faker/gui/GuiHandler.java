package coolclk.faker.gui;

import net.minecraft.client.Minecraft;

import java.awt.*;

public class GuiHandler {
    public static class Theme {
        public final static int BUTTON_WIDTH = 50;
        public final static int BUTTON_HEIGHT = 15;
        public final static int MODULE_BUTTON_SETTINGS_MARGIN = 1;

        public final static int CATEGORY_BUTTON_BACKGROUND_COLOR = 0x343434;
        public final static int MODULE_BUTTON_DESCRIPTION_BACKGROUND_COLOR = 0x5C5C5C;
        public final static int MODULE_BUTTON_SETTINGS_BACKGROUND_COLOR = 0x5C5C5C;
        public final static int MODULE_BUTTON_SETTINGS_ENABLE_COLOR = 0xFFFFFF;
        public final static int MODULE_BUTTON_SETTINGS_DISABLE_COLOR = 0x333333;
        public final static int MODULE_BUTTON_DISABLE_BACKGROUND_COLOR = 0x545454;
        public final static int MODULE_BUTTON_ENABLE_BACKGROUND_COLOR = 0x0091A6;
    }

    public static int getRainbowColor(double speed) {
        return Color.getHSBColor((float) Math.abs(Math.sin(((double) Minecraft.getSystemTime() / 1000) * speed) * 255), 255, 255).getRGB();
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
        return new Color(r, g, b, a).hashCode();
    }

    public static int easeColorRGB(int originalColor, int targetColor, double speed) {
        return new Color(easeColorRGBA(originalColor, targetColor, speed)).getRGB();
    }
}