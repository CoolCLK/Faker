package coolclk.faker.launch.forge;

import coolclk.faker.event.ForgeEventHandler;
import coolclk.faker.feature.ModuleHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = FakerForgeMod.MODID, name = FakerForgeMod.NAME, clientSideOnly = true)
public class FakerForgeMod {
    public final static String MODID = "faker";
    public final static String NAME = "Faker";

    public static Logger LOGGER = LogManager.getLogger();

    @Mod.EventHandler
    public void beforeFMLInitialization(FMLPreInitializationEvent event) {
        LOGGER = event.getModLog();
        if (!LOGGER.isDebugEnabled()) {
            LOGGER.warn("If you are not in the dev, you can ignore this message.");
        }

        ModuleHandler.registerConfig(event.getSuggestedConfigurationFile());
    }

    @Mod.EventHandler
    public void onFMLInitialization(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new ForgeEventHandler());

        ModuleHandler.registerModules();
    }
}
