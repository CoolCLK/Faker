package coolclk.faker.feature.api;

import coolclk.faker.launch.FakerForgeMod;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Module implements IModule {
    private boolean enable = false;
    private String name = "";
    private String nameTranslateKey = "", descriptionTranslateKey = "";
    private KeyBinding keyBinding = null;
    private final List<Settings<?>> settings = new ArrayList<Settings<?>>();

    protected Logger LOGGER = FakerForgeMod.LOGGER;

    public Module() {
        if (this.getClass().isAnnotationPresent(ModuleInfo.class)) {
            ModuleInfo moduleInfo = this.getClass().getAnnotation(ModuleInfo.class);
            this.name = moduleInfo.name();
            this.nameTranslateKey = FakerForgeMod.MODID + ".module." + this.getName() + ".name";
            this.descriptionTranslateKey = FakerForgeMod.MODID + ".module." + this.getName() + ".description";
            this.updateKeyBinding(moduleInfo.defaultKeycode());
            MinecraftForge.EVENT_BUS.register(this);
            ClientRegistry.registerKeyBinding(this.getKeyBinding());
            moduleInfo.category().addModule(this);
        }
    }

    public void updateKeyBinding(int defaultKeyCode) {
        this.keyBinding = new KeyBinding(this.getDisplayName(), defaultKeyCode, FakerForgeMod.NAME);
    }

    public KeyBinding getKeyBinding() {
        return this.keyBinding;
    }

    public String getNameTranslateKey() {
        return this.nameTranslateKey;
    }

    public String getDescriptionTranslateKey() {
        return this.descriptionTranslateKey;
    }

    public String getName() {
        return this.name;
    }

    public String getDisplayName() {
        return I18n.format(this.getNameTranslateKey());
    }

    public String getDisplayDescription() {
        return I18n.format(this.getDescriptionTranslateKey());
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
        if (enable) {
            this.onEnable();
        } else {
            this.onDisable();
        }
    }

    public boolean getEnable() {
        return this.enable;
    }

    public void toggleModule() {
        this.setEnable(!this.getEnable());
    }

    public List<Settings<?>> getSettings() {
        return this.settings;
    }

    public void addSettings(Settings<?> settings) {
        this.settings.add(settings);
    }

    public void onRegister() {

    }

    public void afterRegister() {

    }

    public void onEnable() {

    }

    public void onEnabling() {

    }

    public void onDisable() {

    }

    public void onClickGuiUpdate() {

    }
}
