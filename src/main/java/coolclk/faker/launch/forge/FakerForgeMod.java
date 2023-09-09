package coolclk.faker.launch.forge;

import coolclk.faker.Main;
import coolclk.faker.feature.ModuleHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

@Mod(modid = FakerForgeMod.MODID, name = FakerForgeMod.NAME, clientSideOnly = true)
public class FakerForgeMod {
    public final static String MODID = "faker";
    public final static String NAME = "Faker";

    @Mod.EventHandler
    public void beforeFMLInitialization(FMLPreInitializationEvent event) {
        Main.isForge = true;
        ModuleHandler.setDataFolder(new File(event.getSuggestedConfigurationFile().getParent() + "/Faker/"));
        Main.setup();
    }
}
