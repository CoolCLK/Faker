package coolclk.faker.modules.root.player;

import coolclk.faker.modules.Module;

import java.util.Arrays;
import java.util.Collections;

public class FastEat extends Module {
    public static FastEat INSTANCE = new FastEat();

    public FastEat() {
        super("FastEat", Collections.singletonList(new ModuleArgument("speed", 12, 0, 32)));
    }
}
