package coolclk.faker.feature.api;

import coolclk.faker.event.EventHandler;
import coolclk.faker.event.events.ModuleChangeStatEvent;
import coolclk.faker.util.I18nUtil;

public class Settings<T> {
    private final String name;
    private T value;
    private final T defaultValue;
    private boolean displayVisible = true;

    public Settings(Module parent, String name, T value) {
        this.name = name;
        this.defaultValue = value;
        this.value = this.getDefaultValue();
        parent.addSettings(this);
    }

    public void setValue(T value, boolean update) {
        this.value = value;
        if (update) {
            EventHandler.post(new ModuleChangeStatEvent());
        }
    }

    public void setValue(T value) {
        this.setValue(value, true);
    }

    public T getDefaultValue() {
        return this.defaultValue;
    }

    public T getValue() {
        return this.value;
    }

    public String getName() {
        return I18nUtil.format(this.getTranslateKey());
    }

    protected String getTranslateKey() {
        return "faker.settings." + this.name + ".name";
    }

    public boolean rootOf(Class<?> clazz) {
        if (this.getClass() == clazz) {
            return true;
        }
        Class<?> superClass = this.getClass();
        while ((superClass = superClass.getSuperclass()) != null) {
            if (superClass == clazz) {
                return true;
            }
        }
        return false;
    }

    // For ClickGui

    public String getDisplayName() {
        return I18nUtil.format(this.getTranslateKey());
    }

    public String getDisplayValue() {
        return this.value.toString();
    }

    public boolean getDisplayVisible() {
        return this.displayVisible;
    }

    public void setDisplayVisible(boolean b) {
        this.displayVisible = b;
    }

    public int getDisplayLines() {
        return 1;
    }
}
