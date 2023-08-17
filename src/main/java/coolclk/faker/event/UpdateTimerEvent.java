package coolclk.faker.event;

import net.minecraftforge.fml.common.eventhandler.Event;

public class UpdateTimerEvent extends Event {
    private float multiplier = 1;
    public float getMultiplier() {
        return this.multiplier;
    }

    public void addMultiplier(float multiplier) {
        this.multiplier *= multiplier;
    }
}
