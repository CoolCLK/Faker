package coolclk.faker.feature;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import coolclk.faker.event.events.PlayerUpdateEvent;
import coolclk.faker.feature.api.*;
import coolclk.faker.feature.modules.ModuleCategory;
import org.reflections.Reflections;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static coolclk.faker.Main.logger;

public class ModuleHandler {
    public static File dataFolder;
    public static File configFolder;

    public static String usingConfig = "default";

    public static void register() {
        registerFolder();
        registerModules();
    }

    public static void setDataFolder(File folder) {
        dataFolder = folder;
    }

    protected static void registerFolder() {
        if (dataFolder.exists() || dataFolder.mkdirs()) {
            configFolder = new File(dataFolder.getPath() + "/configs/");
            if (!configFolder.exists() && !configFolder.mkdirs()) logger.warn("Cannot create the config folder");
        } else logger.warn("Cannot create the data folder");
    }

    protected static void registerModules() {
        logger.debug("Register modules");
        for (Class<? extends Module> clazz : new Reflections().getSubTypesOf(Module.class)) {
            try {
                logger.debug("Register module " + clazz.getName());
                if (clazz.isAnnotationPresent(ModuleInfo.class) && !moduleIsRegister(clazz)) {
                    Module module = clazz.newInstance();
                    module.onRegister();
                }
            } catch (Exception e) {
                logger.error("Cannot register module class " + clazz.getName() + "! Try to ask the dev to solved it");
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

    public static void disableUnableModules() {
        for (Module module : ModuleCategory.getAllModules()) {
            if (!module.getCanKeepEnable()) {
                module.setEnable(false, false);
            }
        }
    }

    public static void tickEvent(PlayerUpdateEvent event) {
        for (ModuleCategory category : ModuleCategory.values()) {
            for (Module module : category.getModules()) {
                if (module.getEnable()) {
                    try {
                        module.onUpdate();
                        module.onUpdate(event);
                    } catch (Exception e) {
                        logger.warn("When enable module " + module.getDisplayName() + ", throws an exception: ");
                        e.printStackTrace(System.out);
                    } finally {
                        module.afterEnabling();
                    }
                }
            }
        }
    }

    public static void loadConfigs() {
        File configFile = new File(dataFolder.getPath() + "/config.json");
        if (configFile.exists()) {
            try {
                Map<String, Object> configuration = new Gson().fromJson(new FileReader(configFile), new TypeToken<Map<String, Object>>() {  }.getType());
                if (configuration.containsKey("config")) {
                    usingConfig = (String) configuration.get("config");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        loadConfig(usingConfig);
    }

    public static void loadConfig(String name) {
        File configFile = new File(configFolder.getPath() + "/" + name + ".json");
        try {
            if (configFile.exists()) {
                Reader reader = new FileReader(configFile);
                List<ModuleConfiguration> configurations = new Gson().fromJson(reader, new TypeToken<List<ModuleConfiguration>>() {  }.getType());
                if (configurations != null) {
                    for (ModuleConfiguration configuration : configurations) {
                        if (configuration.name != null) {
                            Module module = ModuleHandler.findModule(configuration.name);
                            if (module != null) {
                                if (configuration.enable != null && module.getCanKeepEnable()) {
                                    logger.debug("Load settings \"enable\" for module " + module.getDisplayName() + " from config");
                                    module.setEnable(configuration.enable, false);
                                }
                                if (configuration.settings != null) {
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
                                                logger.debug("Load settings " + settings.getDisplayName() + " for module " + module.getDisplayName() + " from config");
                                            } else {
                                                logger.warn("Load settings " + settings.getDisplayName() + " for module " + module.getDisplayName() + " failed");
                                            }
                                        } else {
                                            logger.warn("Cannot found settings \"" + settings.getName() + "\" of module " + module.getDisplayName() + "! Mod will ignore it");
                                        }
                                    }
                                }
                                if (configuration.keyBinding != null) module.setKeyBinding(configuration.keyBinding);
                            } else {
                                logger.warn("Cannot found module \"" + configuration.name + "\"! System will ignore it");
                            }
                        }
                    }
                }
                reader.close();
            } else if (!configFile.createNewFile()) logger.warn("Cannot create the config file");
        } catch (Exception e) {
            System.err.print("Cannot load the config file " + configFile.getPath() + ": ");
            e.printStackTrace(System.out);
        }
    }

    public static void saveConfigs() {
        File configFile = new File(dataFolder.getPath() + "/config.json");
        try {
            if (!configFile.exists() && !configFile.createNewFile()) logger.warn("Cannot create the config file");
            Map<String, Object> configuration = new Gson().fromJson(new FileReader(configFile), new TypeToken<Map<String, Object>>() {  }.getType());
            configuration.put("config", usingConfig);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        saveConfig(ModuleHandler.usingConfig);
    }

    public static void saveConfig(String name) {
        try {
            File configFile = new File(configFolder.getPath() + "/" + name + ".json");
            if (!configFile.exists()) {
                if (!configFile.createNewFile()) logger.warn("Cannot create the config file");
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
        Integer keyBinding;
        List<Settings<?>> settings = new ArrayList<Settings<?>>();

        public ModuleConfiguration(Module module) {
            boolean save = false;
            if (module.getEnable() && module.getCanKeepEnable()) {
                this.enable = true;
                save = true;
            }
            if (module.getKeyBinding() != module.getDefaultKeyBinding()) {
                this.keyBinding = module.getKeyBinding();
                save = true;
            }
            this.keyBinding = module.getKeyBinding();
            for (Settings<?> settings : module.getSettings()) {
                if (settings.getValue() != settings.getDefaultValue()) {
                    this.settings.add(settings);
                    save = true;
                }
            }
            if (save) {
                this.name = module.getName();
            }
        }
    }
}
