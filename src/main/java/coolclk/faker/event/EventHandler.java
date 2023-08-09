package coolclk.faker.event;

import coolclk.faker.modules.Module;
import coolclk.faker.modules.ModuleHandler;
import coolclk.faker.modules.ModuleType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class EventHandler {
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        for (ModuleType group : ModuleHandler.getAllModules()) {
            for (Module module : group.getModules()) {
                if (module.getKeyBinding().isPressed()) {
                    module.toggleModule();
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        ModuleHandler.tickEvent();
    }
}
