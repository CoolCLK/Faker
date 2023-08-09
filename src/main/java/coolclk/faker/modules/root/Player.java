package coolclk.faker.modules.root;

import coolclk.faker.modules.ModuleType;
import coolclk.faker.modules.root.movement.*;
import coolclk.faker.modules.root.player.*;

public class Player extends ModuleType {
    public static Player INSTANCE = new Player();

    public Player() {
            super("player", 140, 10, Derp.INSTANCE, FastBreak.INSTANCE, FastEat.INSTANCE, Reach.INSTANCE, Scaffold.INSTANCE, Timer.INSTANCE);
    }
}
