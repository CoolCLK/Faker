package coolclk.faker.modules.root.player;

import coolclk.faker.modules.Module;

import java.util.Arrays;

public class Reach extends Module {
    public static Reach INSTANCE = new Reach();

    public Reach() {
        super("Reach", Arrays.asList(new ModuleArgument("entityDistance", 4, 3, 6), new ModuleArgument("blockDistance", 4.5, 4, 6)));
    }
}
