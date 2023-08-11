package coolclk.faker.feature.api;

public class SettingsInteger extends SettingsNumber<Integer> {
    public SettingsInteger(Module parent, String name, Integer value, Integer minValue, Integer maxValue) {
        super(parent, name, value, minValue, maxValue);
    }

    @Override
    public void setValue(Integer value) {
        if (value < this.getMinValue()) {
            value = this.getMinValue();
        }
        if (value > this.getMaxValue()) {
            value = this.getMaxValue();
        }
        super.setValue(value);
    }
}
