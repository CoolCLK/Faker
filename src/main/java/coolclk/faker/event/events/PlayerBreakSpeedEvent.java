package coolclk.faker.event.events;

import coolclk.faker.event.api.Event;
import net.minecraft.block.Block;

public class PlayerBreakSpeedEvent extends Event {
    protected Block block;
    protected float breakSpeed;

    public PlayerBreakSpeedEvent(Block block, float breakSpeed) {
        this.block = block;
        this.breakSpeed = breakSpeed;
    }

    public float getBreakSpeed() {
        return this.breakSpeed;
    }

    public void setBreakSpeed(float breakSpeed) {
        this.breakSpeed = breakSpeed;
    }

    public void addBreakSpeed(float breakSpeed) {
        this.setBreakSpeed(this.getBreakSpeed() + breakSpeed);
    }
}
