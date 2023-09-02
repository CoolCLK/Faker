package coolclk.faker.feature.api;

import coolclk.faker.event.events.ModuleChangeStatEvent;
import coolclk.faker.feature.modules.ModuleCategory;
import coolclk.faker.feature.modules.player.Timer;
import coolclk.faker.launch.forge.FakerForgeMod;
import coolclk.faker.util.ModuleUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Module implements IModule {
    private boolean enable = false;
    private Long enableTime = 0L;
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
            if (moduleInfo.category() != ModuleCategory.None) {
                MinecraftForge.EVENT_BUS.register(this);
                ClientRegistry.registerKeyBinding(this.getKeyBinding());
            }
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

    public void setEnable(boolean enable, boolean update) {
        this.enable = enable;
        if (update) {
            if (this.getCanKeepEnable()) MinecraftForge.EVENT_BUS.post(new ModuleChangeStatEvent());
            if (enable) {
                this.enableTime = System.currentTimeMillis();
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
        return this.enable && ModuleUtil.gEP() != null;
    }

    public Float getEnableTicks() {
        return (this.getEnable() ? (System.currentTimeMillis() - this.enableTime) : 0) / Timer.currentTimerSpeed;
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

    public void onEnabling() {

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
