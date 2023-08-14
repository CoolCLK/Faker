package coolclk.faker.gui;

import coolclk.faker.util.ModuleUtil;

import java.awt.*;

public class GuiHandler {
    public static class Theme {
        public final static int BUTTON_WIDTH = 50;
        public final static int BUTTON_HEIGHT = 15;
        public final static double MODULE_BUTTON_SETTINGS_MARGIN = 1;

        public final static int CATEGORY_BUTTON_BACKGROUND_COLOR = 0x343434;
        public final static int MODULE_BUTTON_HUD_INFO_COLOR = 0x5C5C5C;
        public final static int MODULE_BUTTON_DESCRIPTION_BACKGROUND_COLOR = 0x343434;
        public final static int MODULE_BUTTON_SETTINGS_BACKGROUND_COLOR = 0x5C5C5C;
        public final static int MODULE_BUTTON_SETTINGS_ENABLE_COLOR = 0xFFFFFF;
        public final static int MODULE_BUTTON_SETTINGS_DISABLE_COLOR = 0x333333;
        public final static int MODULE_BUTTON_DISABLE_BACKGROUND_COLOR = 0x545454;
        public final static int MODULE_BUTTON_ENABLE_BACKGROUND_COLOR = 0x0091A6;
    }

    public static int getRainbowColor(double speed, int movedColor) {
        return Color.getHSBColor((float) (((System.currentTimeMillis() / 1000 * speed) % 256 + movedColor) / 255), 1, 1).hashCode();
    }

    public static int getRainbowColor(double speed) {
        return getRainbowColor(speed, 0);
    }

    public static double easeOutQuad(double speed) {
        return Math.sin(((0.05 * (speed)) * Math.PI) / 2);
    }

    public static int easeColorRGBA(int originalColor, int targetColor, double speed) {
        Color from = new Color(originalColor, true), to = new Color(targetColor, true);
        int fr = from.getRed(), fg = from.getBlue(), fb = from.getBlue(), fa = from.getAlpha();
        int tr = to.getRed(), tg = to.getBlue(), tb = to.getBlue(), ta = to.getAlpha();
        fr += (int) Math.round((tr - fr) * speed);
        fg += (int) Math.round((tg - fg) * speed);
        fb += (int) Math.round((tb - fb) * speed);
        fa += (int) Math.round((ta - fa) * speed);
        if (ModuleUtil.inRange(fr, tr - 25, tr)) {
            fr = tr;
        }
        if (ModuleUtil.inRange(fg, tg - 25, tg)) {
            fg = tg;
        }
        if (ModuleUtil.inRange(fb, tb - 25, tb)) {
            fb = tb;
        }
        if (ModuleUtil.inRange(fa, ta - 25, ta)) {
            fa = ta;
        }
        return new Color(fr, fg, fb, fa).hashCode();
    }

    public static int easeColorRGB(int originalColor, int targetColor, double speed) {
        return new Color(easeColorRGBA(originalColor, targetColor, speed)).getRGB();
    }
}