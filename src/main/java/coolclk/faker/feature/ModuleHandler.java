package coolclk.faker.feature;

import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.modules.ModuleCategory;
import coolclk.faker.gui.clickgui.ClickGuiScreen;
import org.reflections.Reflections;

import java.io.File;

import static coolclk.faker.launch.FakerForgeMod.LOGGER;

public class ModuleHandler {
    public static File configFile;

    public static void registerConfig(File file) {
        configFile = file;
    }

    public static void registerModules() {
        LOGGER.debug("Register modules");
        for (Class<? extends Module> clazz : new Reflections().getSubTypesOf(Module.class)) {
            try {
                LOGGER.debug("Register module " + clazz.getName());
                if (clazz.isAnnotationPresent(ModuleInfo.class) && !moduleIsRegister(clazz)) {
                    Module module = clazz.newInstance();
                    module.onRegister();
                }
            } catch (Exception e) {
                LOGGER.error("Cannot register module class " + clazz.getName() + "! Try to ask the dev to solved it");
                throw new RuntimeException(e);
            }
        }
        for (Module module : ModuleCategory.getAllModules()) {
            module.afterRegister();
        }
    }

    public static <T extends Module> T findModule(Class<T> moduleClass) {
        for (Module module : ModuleCategory.getAllModules()) {
            if (module.getClass() == moduleClass) {
                return (T) module;
            }
        }
        throw new RuntimeException("Cannot found the module " + moduleClass.getName());
    }

    public static boolean moduleIsRegister(Class<? extends Module> moduleClass) {
        for (Module module : ModuleCategory.getAllModules()) {
            if (module.getClass() == moduleClass) {
                return true;
            }
        }
        return false;
    }

    public static void updateModules() {
        ClickGuiScreen.INSTANCE.updateDisplayName();
    }

    public static void tickEvent() {
        for (ModuleCategory category : ModuleCategory.values()) {
            for (Module module : category.getModules()) {
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
