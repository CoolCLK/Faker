package coolclk.faker.feature.api;

import coolclk.faker.util.I18nUtil;

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
        return super.getDisplayLines() * (1 + this.getValues().size());
    }

    public String getDisplayName() {
        return super.getDisplayName() + " " + (this.getDisplayFold() ? "+" : "-");
    }

    @Override
    public String getDisplayValue() {
        return this.getModeDisplayName(this.getValue());
    }

    public String getModeDisplayName(T mode) {
        if (this.getValues().contains(mode)) {
            return I18nUtil.format("faker.settings.mode." + this.getValues().get(this.getValues().indexOf(mode)).toString() + ".name");
        }
        return "";
    }

    public String getModeDisplayName(int index) {
        if (index >= 0 && index < this.getValues().size()) {
            return I18nUtil.format("faker.settings.mode." + this.getValues().get(index).toString() + ".name");
        }
        return "";
    }

    public int getValueDisplayLine() {
        return this.getValues().indexOf(this.getValue());
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
