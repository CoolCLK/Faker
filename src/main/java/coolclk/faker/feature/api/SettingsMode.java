package coolclk.faker.feature.api;

import net.minecraft.client.resources.I18n;

import java.util.Arrays;
import java.util.List;

public class SettingsMode<T> extends Settings<T> {
    protected final List<T> modes;
    protected boolean displayFold = false;

    public SettingsMode(Module parent, T value, List<T> modes) {
        super(parent, "mode", value);
        this.modes = modes;
    }

    public SettingsMode(Module parent, T value, T... mode) {
        this(parent, value, Arrays.asList(mode));
    }

    public List<T> getValues() {
        return this.modes;
    }

    @Override
    public void setValue(T value) {
        if (this.getValues().contains(value)) {
            super.setValue(value);
        }
    }

    @Override
    public int getDisplayLines() {
        if (getDisplayFold()) {
            return super.getDisplayLines();
        }
        return super.getDisplayLines() * (1 + this.modes.size());
    }

    public String getDisplayName() {
        return super.getDisplayName() + " " + (this.getDisplayFold() ? "+" : "-");
    }

    public String getModeDisplayName(T mode) {
        if (this.modes.contains(mode)) {
            return I18n.format("faker.settings.mode." + this.modes.get(this.modes.indexOf(mode)).toString() + ".name");
        }
        return "";
    }

    public String getModeDisplayName(int index) {
        if (index >= 0 && index < this.modes.size()) {
            return I18n.format("faker.settings.mode." + this.modes.get(index).toString() + ".name");
        }
        return "";
    }

    public boolean getDisplayFold() {
        return this.displayFold;
    }

    public void setDisplayFold(boolean fold) {
        this.displayFold = fold;
    }

    public void toggleDisplayFold() {
        this.setDisplayFold(!this.getDisplayFold());
    }
}
