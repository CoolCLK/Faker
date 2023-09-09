package coolclk.faker.event;

import coolclk.faker.Main;
import coolclk.faker.event.api.Event;
import coolclk.faker.event.api.SubscribeEvent;
import coolclk.faker.event.events.*;
import coolclk.faker.feature.ModuleHandler;
import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.modules.ModuleCategory;
import coolclk.faker.util.I18nUtil;
import net.minecraft.client.Minecraft;

public class EventHandler {
    public static void post(Event event) {
        try {
            event.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean postCanceled(Event event) {
        post(event);
        return event.isCanceled();
    }

    @SubscribeEvent
    public void onKeyInput(KeyInputEvent event) {
        if (event.getPressedKey() > 0) Main.logger.info("Key: " + event.getPressedKey());
        for (Module module : ModuleCategory.getAllModules()) {
            if (event.getPressedKey() == module.getKeyBinding() && event.isPressed()) {
                module.toggleModule();
            }
        }
    }

    @SubscribeEvent
    public void onPlayerTick(PlayerUpdateEvent event) {
        if (event.getShift() == PlayerUpdateEvent.Shift.AFTER) ModuleHandler.tickEvent(event);
    }

    @SubscribeEvent
    public void onModuleChangeStat(ModuleChangeStatEvent event) {
        ModuleHandler.saveConfigs();
    }

    @SubscribeEvent
    public void onResourceReloaded(ResourceReloadedEvent event) {
        I18nUtil.setLocale(Minecraft.getMinecraft().gameSettings.language);
        I18nUtil.refreshTranslateMap();
    }

    @SubscribeEvent
    public void onClientConnectedToServer(ClientConnectToServerEvent event) {
        ModuleHandler.disableUnableModules();
    }
}
