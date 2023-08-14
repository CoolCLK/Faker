package coolclk.faker.feature.api;

public class SettingsFloat extends SettingsNumber<Float> {
    private Number decimalPlaces = 2;

    public SettingsFloat(Module parent, String name, Float value, Float minValue, Float maxValue) {
        super(parent, name, value, minValue, maxValue);
    }

    public void setDecimalPlaces(Number places) {
        this.decimalPlaces = places;
    }

    public Number getDecimalPlaces() {
        return this.decimalPlaces;
    }

    @Override
    public void setValue(Float value) {
        if (value < this.getMinValue()) {
            value = this.getMinValue();
        }
        if (value > this.getMaxValue()) {
            value = this.getMaxValue();
        }
        value = Float.valueOf(Double.toString(((int) (value * Math.pow(10, this.getDecimalPlaces().doubleValue()))) / Math.pow(10, this.getDecimalPlaces().doubleValue())));
        super.setValue(value);
    }

    public void setValueByPercent(Number percent) {
        this.setValue((float) (this.getMinValue() + (this.getMaxValue() - this.getMinValue()) * percent.doubleValue()));
    }
}
