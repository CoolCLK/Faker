package coolclk.faker.feature.api;

public class SettingsNumber<T extends Number> extends Settings<T> {
    private final T minValue, maxValue;

    public SettingsNumber(Module parent, String name, T value, T minValue, T maxValue) {
        super(parent, name, value);
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public T getMaxValue() {
        return this.maxValue;
    }

    public T getMinValue() {
        return this.minValue;
    }

    public Double getValuePercent() {
        return (this.getValue().doubleValue() - this.getMinValue().doubleValue()) / (this.getMaxValue().doubleValue() - this.getMinValue().doubleValue());
    }

    public String getDisplayValue() {
        return Double.toString((double) ((int) (this.getValue().doubleValue() * 100)) / 100);
    }

    public void setValueByPercent(Number percent) {

    }

    @Override
    public int getDisplayLines() {
        return super.getDisplayLines() * 2;
    }
}
