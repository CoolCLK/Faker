package coolclk.faker.gui;

import coolclk.faker.launch.FakerForgeMod;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;
import java.util.List;

public class InputHandler {
    public final static int BUTTON_NONE = 0;
    public final static int BUTTON_LEFT = 0;
    public final static int BUTTON_RIGHT = 1;
    public final static int BUTTON_WHEEL = 2;

    private static final List<Integer> mousePressed = new ArrayList<Integer>();

    public static boolean isMousePressed(int button) {
        if (Mouse.isCreated() && Mouse.getEventButtonState() && Mouse.getEventButton() == button) {
            if (!mousePressed.contains(button)) {
                mousePressed.add(button);
                return true;
            }
        } else if (mousePressed.contains(button)) {
            mousePressed.remove((Integer) button);
        }
        return false;
    }

    public static boolean isMousePressing(int button) {
        return Mouse.isCreated() && Mouse.isButtonDown(button);
    }
}
