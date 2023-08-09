package coolclk.faker.modules.root;

import coolclk.faker.modules.ModuleType;
import coolclk.faker.modules.root.movement.*;

public class Movement extends ModuleType {
    public static Movement INSTANCE = new Movement();

    public Movement() {
        super("movement", 70, 10, BHop.INSTANCE, Fly.INSTANCE, ForceSprinting.INSTANCE, HighJump.INSTANCE, Jesus.INSTANCE, NoFall.INSTANCE, Scaffold.INSTANCE);
    }
}
