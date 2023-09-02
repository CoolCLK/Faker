package coolclk.faker.event.api;

import org.reflections.Reflections;

import java.lang.reflect.Method;

public class Event {
    protected boolean canceled = false;

    public boolean isCanceled() {
        return this.canceled;
    }

    public void setCanceled(boolean cancel) {
        this.canceled = cancel;
    }

    public void call() {
        for (Method method : new Reflections().getMethodsAnnotatedWith(SubscribeEvent.class)) {
            if (method.getParameterTypes().length == 1 && method.getParameterTypes()[0] == this.getClass()) {
                try {
                    method.invoke(null, this);
                } catch (Exception e) {
                    System.err.print("Cannot call event " + this.getClass().getName() + ": ");
                    e.printStackTrace(System.err);
                }
            }
        }
    }
}
