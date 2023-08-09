package coolclk.faker.launch;

import coolclk.faker.event.EventHandler;
import coolclk.faker.gui.GuiHandler;
import coolclk.faker.modules.ModuleHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = FakerForgeMod.MODID, clientSideOnly = true)
public class FakerForgeMod {
    public final static String MODID = "faker";

    @Mod.Instance(FakerForgeMod.MODID) public static FakerForgeMod INSTANCE;

    public static Logger LOGGER = LogManager.getLogger();

    @Mod.EventHandler
    public void beforeFMLInitialization(FMLPreInitializationEvent event) {
        LOGGER = event.getModLog();

        ModuleHandler.registerConfig(event.getSuggestedConfigurationFile());
    }

    @Mod.EventHandler
    public void onFMLInitialization(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new EventHandler());
        NetworkRegistry.INSTANCE.registerGuiHandler(FakerForgeMod.INSTANCE, new GuiHandler());

        ModuleHandler.loadConfigs();
        ModuleHandler.register();
    }
}
