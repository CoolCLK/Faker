package coolclk.faker.feature.api;

import net.minecraft.client.resources.I18n;

public class Settings<T> {
    private final String name;
    private T value;
    private boolean displayVisible = true;

    public Settings(Module parent, String name, T value) {
        this.name = name;
        this.value = value;
        parent.addSettings(this);
    }

    public void setValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return this.value;
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

    public String getDisplayType() {
        return "";
    }

    public String getDisplayName() {
        return I18n.format(this.getTranslateKey());
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

    public void toggleDisplayVisible() {
        this.setDisplayVisible(!this.getDisplayVisible());
    }

    public int getDisplayLines() {
        return 1;
    }
}
