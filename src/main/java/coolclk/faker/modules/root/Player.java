package coolclk.faker.modules.root;

import coolclk.faker.modules.ModuleType;
import coolclk.faker.modules.root.movement.*;
import coolclk.faker.modules.root.player.Derp;
import coolclk.faker.modules.root.player.FastBreak;
import coolclk.faker.modules.root.player.Reach;
import coolclk.faker.modules.root.player.Timer;

public class Player extends ModuleType {
    public static Player INSTANCE = new Player();

    public Player() {
        super("player", 140, 10, Derp.INSTANCE, FastBreak.INSTANCE, FastBreak.INSTANCE, Reach.INSTANCE, Scaffold.INSTANCE, Timer.INSTANCE);
    }
}
