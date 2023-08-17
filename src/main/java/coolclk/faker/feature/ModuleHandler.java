package coolclk.faker.feature;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import coolclk.faker.feature.api.*;
import coolclk.faker.feature.modules.ModuleCategory;
import org.reflections.Reflections;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import static coolclk.faker.launch.forge.FakerForgeMod.LOGGER;

public class ModuleHandler {
    public static File configFolder;

    public static void registerConfig(File file) {
        configFolder = new File(file.getParent() + "/Faker/");
        configFolder.mkdirs();
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
        ModuleHandler.loadConfigs();
    }

    public static <T extends Module> T findModule(Class<T> moduleClass) {
        for (Module module : ModuleCategory.getAllModules()) {
            if (module.getClass() == moduleClass) {
                return (T) module;
            }
        }
        throw new RuntimeException("Cannot found the module " + moduleClass.getName());
    }

    public static Module findModule(String moduleName) {
        for (Module module : ModuleCategory.getAllModules()) {
            if (module.getName().equals(moduleName)) {
                return module;
            }
        }
        return null;
    }

    public static boolean moduleIsRegister(Class<? extends Module> moduleClass) {
        for (Module module : ModuleCategory.getAllModules()) {
            if (module.getClass() == moduleClass) {
                return true;
            }
        }
        return false;
    }

    public static void disableUnlikeableModules() {
        for (Module module : ModuleCategory.getAllModules()) {
            if (!module.getCanKeepEnable()) {
                module.setEnable(false, false);
            }
        }
    }

    public static void tickEvent() {
        for (ModuleCategory category : ModuleCategory.values()) {
            for (Module module : category.getModules()) {
                if (module.getEnable()) {
                    module.onEnabling();
                    module.afterEnabling();
                }
            }
        }
    }

    public static void loadConfigs() {
        try {
            File configFile = new File(configFolder.getPath() + "/defaultConfig.json");
            if (configFile.exists()) {
                FileReader reader = new FileReader(configFile);
                List<ModuleConfiguration> configurations = new Gson().fromJson(reader, new TypeToken<List<ModuleConfiguration>>() {  }.getType());
                for (ModuleConfiguration configuration : configurations) {
                    Module module = ModuleHandler.findModule(configuration.name);
                    if (module != null) {
                        if (module.getCanKeepEnable()) {
                            LOGGER.debug("Load settings \"enable\" for module " + module.getDisplayName() + " from config");
                            module.setEnable(configuration.enable, false);
                        }
                        for (Settings<?> settings : configuration.settings) {
                            Settings<?> targetSettings = null;
                            for (Settings<?> activeSettings : module.getSettings()) {
                                if (activeSettings.getName().equals(settings.getName())) {
                                    targetSettings = activeSettings;
                                }
                            }
                            if (targetSettings != null) {
                                boolean failed = false;
                                try {
                                    if (targetSettings.rootOf(SettingsBoolean.class)) {
                                        ((SettingsBoolean) targetSettings).setValue(Boolean.valueOf(settings.getValue().toString()), false);
                                    } else if (targetSettings.rootOf(SettingsMode.class)) {
                                        if (targetSettings.rootOf(SettingsModeString.class)) {
                                            ((SettingsModeString) targetSettings).setValue(settings.getValue().toString(), false);
                                        } else {
                                            failed = true;
                                        }
                                    } else if (targetSettings.rootOf(SettingsNumber.class)) {
                                        if (targetSettings.rootOf(SettingsDouble.class)) {
                                            ((SettingsDouble) targetSettings).setValue(Double.valueOf(settings.getValue().toString()), false);
                                        } else if (targetSettings.rootOf(SettingsFloat.class)) {
                                            ((SettingsFloat) targetSettings).setValue(Float.valueOf(settings.getValue().toString()), false);
                                        } else if (targetSettings.rootOf(SettingsInteger.class)) {
                                            ((SettingsInteger) targetSettings).setValue(Integer.valueOf(settings.getValue().toString()), false);
                                        } else if (targetSettings.rootOf(SettingsLong.class)) {
                                            ((SettingsLong) targetSettings).setValue(Long.valueOf(settings.getValue().toString()), false);
                                        } else {
                                            failed = true;
                                        }
                                    } else {
                                        failed = true;
                                    }
                                } catch (Exception e) {
                                    failed = true;
                                }
                                if (!failed) {
                                    LOGGER.debug("Load settings " + settings.getDisplayName() + " for module " + module.getDisplayName() + " from config");
                                } else {
                                    LOGGER.warn("Load settings " + settings.getDisplayName() + " for module " + module.getDisplayName() + " failed");
                                }
                            } else {
                                LOGGER.warn("Cannot found settings \"" + settings.getName() + "\" of module " + module.getDisplayName() + "! Mod will ignore it");
                            }
                        }
                    } else {
                        LOGGER.warn("Cannot found module \"" + configuration.name + "\"! Mod will ignore it");
                    }
                }
                reader.close();
            } else {
                configFile.createNewFile();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveConfigs() {
        try {
            File configFile = new File(configFolder.getPath() + "/defaultConfig.json");
            if (!configFile.exists()) {
                configFile.createNewFile();
            }
            FileWriter writer = new FileWriter(configFile);
            List<ModuleConfiguration> configurations = new ArrayList<ModuleConfiguration>();
            for (Module module : ModuleCategory.getAllModules()) {
                configurations.add(new ModuleConfiguration(module));
            }
            writer.write(new Gson().toJson(configurations));
            writer.flush();
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static class ModuleConfiguration {
        String name;
        Boolean enable;
        List<Settings<?>> settings;

        public ModuleConfiguration(Module module) {
            this.name = module.getName();
            this.enable = false;
            if (module.getCanKeepEnable()) {
                this.enable = module.getEnable();
            }
            this.settings = module.getSettings();
        }
    }
}
