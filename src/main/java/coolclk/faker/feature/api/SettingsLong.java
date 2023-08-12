package coolclk.faker.feature.api;

public class SettingsLong extends SettingsNumber<Long> {
    public SettingsLong(Module parent, String name, Long value, Long minValue, Long maxValue) {
        super(parent, name, value, minValue, maxValue);
    }

    @Override
    public void setValue(Long value) {
        if (value < this.getMinValue()) {
            value = this.getMinValue();
        }
        if (value > this.getMaxValue()) {
            value = this.getMaxValue();
        }
        super.setValue(value);
    }

    public void setValueByPercent(Number percent) {
        this.setValue((long) (this.getMinValue() + (this.getMaxValue() - this.getMinValue()) * percent.doubleValue()));
    }
}
