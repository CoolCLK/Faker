package coolclk.faker.event;

import coolclk.faker.event.api.Event;
import coolclk.faker.event.api.SubscribeEvent;
import coolclk.faker.event.events.KeyInputEvent;
import coolclk.faker.event.events.PlayerTickEvent;
import coolclk.faker.feature.ModuleHandler;
import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.modules.ModuleCategory;

public class EventHandler {
    public static void post(Event event) {
        try {
            event.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SubscribeEvent
    public static void onKeyInput(KeyInputEvent event) {
        for (Module module : ModuleCategory.getAllModules()) {
            if (module.getKeyBinding().isPressed()) {
                module.toggleModule();
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent event) {
        ModuleHandler.tickEvent();
    }
}
