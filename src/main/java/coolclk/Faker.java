package coolclk;

import coolclk.events.RegistryEvents;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Faker.MODID, name = Faker.NAME, version = Faker.VERSION, clientSideOnly = true)
public class Faker {
    public final static String MODID = "faker";
    public final static String NAME = "Faker";
    public final static String VERSION = "1.0.0";

    @Mod.Instance(Faker.MODID) public static Faker INSTANCE;

    public static Logger LOGGER = LogManager.getLogger();

    @Mod.EventHandler
    public void beforeFMLInitialization(FMLPreInitializationEvent event) {
        LOGGER = event.getModLog();
    }

    @Mod.EventHandler
    public void onFMLInitialization(FMLInitializationEvent event) {
        new RegistryEvents().onFMLInitialization();
    }
}
