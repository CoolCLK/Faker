package coolclk.faker.launch.agent;

import net.minecraft.client.Minecraft;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

public class Bootstrap {
    public static void premain(String agentArgs, Instrumentation inst) throws UnmodifiableClassException {
        agentmain(agentArgs, inst);
    }

    public static void agentmain(String agentArgs, Instrumentation inst) throws UnmodifiableClassException {
        try {
            inst.addTransformer(ClassTransformer.class.newInstance(), true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        inst.retransformClasses(Minecraft.class);
    }
}
