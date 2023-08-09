package coolclk.faker.modules;

import coolclk.faker.modules.root.Combat;
import coolclk.faker.modules.root.Movement;
import coolclk.faker.modules.root.Player;
import coolclk.faker.modules.root.Render;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class ModuleHandler {
    private static final List<ModuleType> modules = Arrays.asList(
            Combat.INSTANCE,
            Movement.INSTANCE,
            Player.INSTANCE,
            Render.INSTANCE);

    public static File configFile;

    public static List<ModuleType> getAllModules() {
        return modules;
    }

    public static void registerConfig(File file) {
        configFile = file;
    }

    public static void register() {
        for (ModuleType group : ModuleHandler.getAllModules()) {
            for (Module module : group.getModules()) {
                MinecraftForge.EVENT_BUS.register(module);
                ClientRegistry.registerKeyBinding(module.getKeyBinding());
            }
        }
    }

    public static void tickEvent() {
        for (ModuleType group : ModuleHandler.getAllModules()) {
            for (Module module : group.getModules()) {
                if (module.getEnable()) {
                    module.onEnabling();
                }
            }
        }
    }

    public static void loadConfigs() {
    }

    public static void saveConfigs() {
    }
}
