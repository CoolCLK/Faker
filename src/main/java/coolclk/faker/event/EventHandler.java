package coolclk.faker.event;

import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.modules.ModuleCategory;
import coolclk.faker.feature.ModuleHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class EventHandler {
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        for (Module module : ModuleCategory.getAllModules()) {
            if (module.getKeyBinding().isPressed()) {
                module.toggleModule();
            }
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        ModuleHandler.tickEvent();
    }

    @SubscribeEvent
    public void onRefreshResources(RefreshResourcesEvent event) {
        ModuleHandler.updateModules();
    }
}
