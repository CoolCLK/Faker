package coolclk.faker.launch.agent;

import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.tools.agent.MixinAgent;

import java.lang.instrument.Instrumentation;

public class Bootstrap {
    public static void premain(String arg, Instrumentation instrumentation) {
        initMixin();
        MixinAgent.premain(arg, instrumentation);
    }

    public static void agentmain(String arg, Instrumentation instrumentation) {
        initMixin();
        MixinAgent.agentmain(arg, instrumentation);
    }

    public static void initMixin() {
        Mixins.addConfiguration("assets/faker/mixin/mixins.faker.json");
    }
}
