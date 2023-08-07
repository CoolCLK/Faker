package coolclk.faker.events;

import coolclk.faker.launch.FakerForgeMod;
import coolclk.faker.gui.GuiHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class RegistryEvents {
    public void onFMLInitialization() {
        KeyboardEvents keyboardEvents = new KeyboardEvents();

        MinecraftForge.EVENT_BUS.register(keyboardEvents);
        MinecraftForge.EVENT_BUS.register(new PlayerEvents());

        registerGui();
        keyboardEvents.registerKey();
    }

    private void registerGui() {
        NetworkRegistry.INSTANCE.registerGuiHandler(FakerForgeMod.INSTANCE, new GuiHandler());
    }
}
