package coolclk.faker.feature.api;

public class SettingsDouble extends SettingsNumber<Double> {
    private Number decimalPlaces = 2;

    public SettingsDouble(Module parent, String name, Double value, Double minValue, Double maxValue) {
        super(parent, name, value, minValue, maxValue);
    }

    public void setDecimalPlaces(Number places) {
        this.decimalPlaces = places;
    }

    public Number getDecimalPlaces() {
        return this.decimalPlaces;
    }

    @Override
    public void setValue(Double value) {
        if (value < this.getMinValue()) {
            value = this.getMinValue();
        }
        if (value > this.getMaxValue()) {
            value = this.getMaxValue();
        }
        value = ((int) (value * Math.pow(10, this.getDecimalPlaces().doubleValue()))) / Math.pow(10, this.getDecimalPlaces().doubleValue());
        super.setValue(value);
    }

    public void setValueByPercent(Number percent) {
        this.setValue(this.getMinValue() + (this.getMaxValue() - this.getMinValue()) * percent.doubleValue());
    }
}
