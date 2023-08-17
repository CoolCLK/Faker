package coolclk.faker.feature.api;

public class SettingsNumber<T extends Number> extends Settings<T> {
    private final T minValue;
    private final T maxValue;

    private String forceChangingValue = "";
    private boolean isForceChangingValue = false;

    public SettingsNumber(Module parent, String name, T value, T minValue, T maxValue) {
        super(parent, name, value);
        this.minValue = minValue;
        if (maxValue.doubleValue() < minValue.doubleValue()) {
            maxValue = minValue;
        }
        this.maxValue = maxValue;
    }

    public T getMaxValue() {
        return this.maxValue;
    }

    public T getMinValue() {
        return this.minValue;
    }

    public Double getValuePercent() {
        double percent = (this.getValue().doubleValue() - this.getMinValue().doubleValue()) / (this.getMaxValue().doubleValue() - this.getMinValue().doubleValue());
        if (percent > 1) percent = 1D;
        if (percent < 0) percent = 0D;
        return percent;
    }

    public String getDisplayValue() {
        return Double.toString((double) ((int) (this.getValue().doubleValue() * 100)) / 100);
    }

    public String getForceChangingValue() {
        return this.forceChangingValue;
    }

    public void setForceChangingValue(String s) {
        this.forceChangingValue = s;
    }

    public void addForceChangingValue(String s) {
        this.setForceChangingValue(this.getForceChangingValue() + s);
    }

    public void addForceChangingValue(Character c) {
        this.addForceChangingValue(c.toString());
    }

    public void backspaceForceChangingValue() {
        if (this.getForceChangingValue().length() > 1) this.setForceChangingValue(this.getForceChangingValue().substring(0, this.getForceChangingValue().length() - 2));
    }

    public void setIsForceChangingValue(Boolean b) {
        this.isForceChangingValue = b;
        this.setForceChangingValue(this.getValue().toString());
    }

    public Boolean isForceChangingValue() {
        return this.isForceChangingValue;
    }

    public void setValueByPercent(Number percent) {

    }

    @Override
    public int getDisplayLines() {
        return super.getDisplayLines() * 2;
    }
}
