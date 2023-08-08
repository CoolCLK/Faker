package coolclk.faker.modules.root.player;

import coolclk.faker.modules.Module;

import java.util.Arrays;

public class Regen extends Module {
    public static Regen INSTANCE = new Regen();

    public Regen() {
        super("Regen", Arrays.asList(new ModuleArgument("heal", 1.5, 1, 20), new ModuleArgument("speed", 75, 0, 80)));
    }
}
