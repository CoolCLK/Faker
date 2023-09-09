package coolclk.faker.event.api;

import coolclk.faker.Main;
import coolclk.faker.event.events.KeyInputEvent;
import org.reflections.Reflections;

import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class Event {
    protected static List<Method> processMethods = new ArrayList<Method>();
    protected static boolean scanned = false, emtpy = false;

    protected void updateProcessMethods() {
        processMethods.clear();
        for (Method method : new Reflections().getMethodsAnnotatedWith(SubscribeEvent.class)) {
            if (method.getParameterTypes().length == 1 && method.getParameterTypes()[0] == this.getClass()) {
                processMethods.add(method);
            }
        }
    }

    protected void checkProcessMethods() {
        if (processMethods.isEmpty()) updateProcessMethods();
        if (processMethods.isEmpty()) emtpy = true;
    }

    public void call() {
        if (!scanned) {
            checkProcessMethods();
            scanned = true;
        }
        if (!emtpy) {
            if (!processMethods.isEmpty()) {
                for (Method method : processMethods) {
                    try {
                        method.invoke(Modifier.isStatic(method.getModifiers()) ? null : method.getDeclaringClass().newInstance(), this);
                    } catch (Exception e) {
                        Main.logger.error("Cannot call event " + this.getClass().getName() + ": ");
                        e.printStackTrace(new PrintStream(new OutputStream() {
                            @Override
                            public void write(int b) {
                                Main.logger.error(new String(new byte[]{(byte) b}));
                            }
                        }));
                    }
                }
            }
        }
    }

    public boolean isCancelable() {
        return false;
    }

    public boolean isCanceled() {
        return false;
    }
}
