package coolclk.faker.event.events;

import coolclk.faker.event.api.Event;

import java.util.ArrayList;
import java.util.List;

public class KeyInputEvent extends Event {
    protected final static List<Integer> holdingKeys = new ArrayList<Integer>();
    protected final Integer downKey;
    protected final Integer pressedKey;

    public KeyInputEvent(Integer downKey) {
        this.downKey = downKey;
        this.pressedKey = getHoldingKeys().contains(this.getDownKey()) ? this.getDownKey() : 0;
        getHoldingKeys().add(this.getDownKey());
    }

    public static void clearHoldingKeys() {
        getHoldingKeys().clear();
    }

    public Integer getDownKey() {
        return this.downKey;
    }

    public Integer getPressedKey() {
        return this.pressedKey;
    }

    public boolean isPressed() {
        return this.getPressedKey() > 0;
    }

    protected static List<Integer> getHoldingKeys() {
        return holdingKeys;
    }
}
