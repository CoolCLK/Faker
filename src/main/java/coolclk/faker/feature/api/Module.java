package coolclk.faker.feature.api;

import coolclk.faker.event.EventHandler;
import coolclk.faker.event.events.ModuleChangeStatEvent;
import coolclk.faker.event.events.PlayerUpdateEvent;
import coolclk.faker.launch.forge.FakerForgeMod;
import coolclk.faker.util.I18nUtil;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class Module implements IModule {
    private boolean enable = false;
    private String name = "";
    private String nameTranslateKey = "", descriptionTranslateKey = "";
    private int keyBinding = 0;
    private final int defaultKeyBinding;
    private final List<Settings<?>> settings = new ArrayList<Settings<?>>();

    public Module() {
        if (this.getClass().isAnnotationPresent(ModuleInfo.class)) {
            ModuleInfo moduleInfo = this.getClass().getAnnotation(ModuleInfo.class);
            this.name = moduleInfo.name();
            this.nameTranslateKey = FakerForgeMod.MODID + ".module." + this.getName() + ".name";
            this.descriptionTranslateKey = FakerForgeMod.MODID + ".module." + this.getName() + ".description";
            this.defaultKeyBinding = moduleInfo.defaultKeycode();
            this.keyBinding = this.getDefaultKeyBinding();
            moduleInfo.category().addModule(this);
        } else this.defaultKeyBinding = 0;
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
        return I18nUtil.format(this.getNameTranslateKey());
    }

    public String getDisplayDescription() {
        return I18nUtil.format(this.getDescriptionTranslateKey());
    }

    public int getDefaultKeyBinding() {
        return this.defaultKeyBinding;
    }

    public int getKeyBinding() {
        return this.keyBinding;
    }

    public void setKeyBinding(int key) {
        this.keyBinding = key;
    }

    public void setEnable(boolean enable, boolean update) {
        this.enable = enable;
        if (update) {
            if (this.getCanKeepEnable()) EventHandler.post(new ModuleChangeStatEvent());
            if (enable) {
                this.onEnable();
            } else {
                this.onDisable();
                this.afterDisable();
            }
        }
    }

    public void setEnable(boolean enable) {
        this.setEnable(enable, true);
    }

    public boolean getCanKeepEnable() {
        return true;
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

    @SideOnly(value = Side.CLIENT)
    @SubscribeEvent
    public void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        this.setEnable(this.getEnable() && !this.getCanKeepEnable(), false);
    }

    public void onRegister() {

    }

    public void afterRegister() {

    }

    public void onEnable() {

    }

    public void onUpdate() {

    }

    public void onUpdate(PlayerUpdateEvent event) {

    }

    public void afterEnabling() {

    }

    public void onDisable() {

    }

    public void afterDisable() {

    }

    public void onClickGuiUpdate() {

    }

    public String getHUDInfo() {
        for (Settings<?> settings : this.settings) {
            if (settings.rootOf(SettingsMode.class)) {
                return settings.getDisplayValue();
            }
        }
        return "";
    }
}
