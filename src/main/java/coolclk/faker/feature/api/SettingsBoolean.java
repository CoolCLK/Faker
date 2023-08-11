package coolclk.faker.feature.api;

public class SettingsBoolean extends Settings<Boolean> {
    public SettingsBoolean(Module parent, String name, Boolean value) {
        super(parent, name, value);
    }

    public void toggleValue() {
        this.setValue(!this.getValue());
    }
}
