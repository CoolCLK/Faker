package coolclk.events;

import coolclk.Faker;
import coolclk.gui.GuiHandler;
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
        NetworkRegistry.INSTANCE.registerGuiHandler(Faker.INSTANCE, new GuiHandler());
    }
}
