package coolclk.faker.event;

import coolclk.faker.gui.GuiHandler;
import coolclk.faker.launch.FakerForgeMod;
import coolclk.faker.modules.Module;
import coolclk.faker.modules.ModuleHandler;
import coolclk.faker.modules.ModuleType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EventHandler {
    public void onFMLInitialization() {
        NetworkRegistry.INSTANCE.registerGuiHandler(FakerForgeMod.INSTANCE, new GuiHandler());
        ModuleHandler.loadConfigs();
        ModuleHandler.registerKey();
    }

    @SideOnly(Side.CLIENT)
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
