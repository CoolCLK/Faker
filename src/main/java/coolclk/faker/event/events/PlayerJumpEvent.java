package coolclk.faker.event.events;

import coolclk.faker.event.api.Event;

public class PlayerJumpEvent extends Event {
    protected double upwardsMotion;

    public PlayerJumpEvent(double upwardsMotion) {
        this.upwardsMotion = upwardsMotion;
    }

    public double getUpwardsMotion() {
        return this.upwardsMotion;
    }

    public void setUpwardsMotion(double upwardsMotion) {
        this.upwardsMotion = upwardsMotion;
    }
}
