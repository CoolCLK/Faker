package coolclk.faker.launch.agent;

import java.lang.instrument.Instrumentation;

public class Bootstrap {
    public static void premain(String agentArgs, Instrumentation inst) {
        agentmain(agentArgs, inst);
    }

    public static void agentmain(String agentArgs, Instrumentation inst) {
        try {
            inst.addTransformer(ClassTransformer.class.newInstance(), true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
