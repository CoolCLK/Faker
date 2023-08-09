package coolclk.faker.modules.root.render;

import coolclk.faker.modules.Module;

import java.util.Arrays;

public class ESP extends Module {
    public static ESP INSTANCE = new ESP();

    public ESP() {
        super("ESP", Arrays.asList(new ModuleArgument("player", true), new ModuleArgument("collection", false), new ModuleArgument("chest", false), new ModuleArgument("bed", false)));
    }
}
