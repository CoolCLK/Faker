package coolclk.faker.modules;

import coolclk.faker.launch.FakerForgeMod;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class Module {
    public static class ModuleArgument {
        public enum ArgumentType {
            SWITCH,
            NUMBER,
            PERCENT
        }

        private final String name;
        private boolean booleanValue;
        private double numberValue;
        private final double numberMinValue;
        private final double numberMaxValue;
        private final ArgumentType valueType;

        private boolean visible = true;

        public ModuleArgument(String name, boolean value) {
            this.name = name;
            this.booleanValue = value;
            this.valueType = ArgumentType.SWITCH;

            this.numberMinValue = -1;
            this.numberMaxValue = -1;
        }

        public ModuleArgument(String name, double value, double minValue, double maxValue) {
            this.name = name;
            this.numberValue = value;
            this.numberMinValue = minValue;
            this.numberMaxValue = maxValue;
            this.valueType = ArgumentType.NUMBER;
        }

        public ModuleArgument(String name, double value) {
            this.name = name;
            this.numberValue = value;
            this.numberMinValue = 0;
            this.numberMaxValue = 1;
            this.valueType = ArgumentType.PERCENT;
        }

        public String getName() {
            return this.name;
        }

        @Deprecated
        public void setBooleanValue(boolean value) {
            if (this.valueType == ArgumentType.SWITCH) {
                this.booleanValue = value;
            }
        }

        public void toggleBooleanValue() {
            if (this.valueType == ArgumentType.SWITCH) {
                this.setBooleanValue(!this.getBooleanValue());
            }
        }

        public ArgumentType getValueType() {
            return this.valueType;
        }

        public boolean getBooleanValue() {
            return this.booleanValue;
        }

        public double getNumberValueD() {
            return this.numberValue;
        }

        public float getNumberValueF() {
            return Float.parseFloat(Double.toString(this.numberValue));
        }

        public Long getNumberValueL() {
            return Math.round(this.getNumberValueD());
        }

        public Integer getNumberValueI() {
            return this.getNumberValueL().intValue();
        }

        public double getNumberMinValue() {
            return this.numberMinValue;
        }

        public double getNumberMaxValue() {
            return this.numberMaxValue;
        }

        public void setNumberValue(double value) {
            if (value < this.getNumberMinValue()) {
                value = this.getNumberMinValue();
            }
            if (value > this.getNumberMaxValue()) {
                value = this.getNumberMaxValue();
            }
            this.numberValue = value;
        }

        public boolean getVisible() {
            return this.visible;
        }

        public void setVisible(boolean visible) {
            this.visible = visible;
        }
    }

    private final String name;
    private final List<ModuleArgument> arguments;

    private boolean enable = false;
    private final KeyBinding keyBinding;

    public Module(String name) {
        this.name = name;
        this.arguments = new ArrayList<ModuleArgument>();
        this.keyBinding = new KeyBinding(this.getDisplayName(), this.getDefaultKeyCode(), FakerForgeMod.NAME);
    }

    public Module(String name, List<ModuleArgument> arguments) {
        this.name = name;
        this.arguments = arguments;
        this.keyBinding = new KeyBinding(this.getDisplayName(), this.getDefaultKeyCode(), FakerForgeMod.NAME);
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
        if (this.enable) {
            this.onEnable();
        } else {
            this.onDisable();
        }
        ModuleHandler.saveConfigs();
    }

    public void toggleModule() {
        this.setEnable(!this.getEnable());
    }

    public boolean getEnable() {
        return this.enable;
    }

    public String getDisplayName() {
        return I18n.format(this.getI18nKey());
    }

    public KeyBinding getKeyBinding() {
        return this.keyBinding;
    }

    public String getName() {
        return this.name;
    }

    public String getI18nKey() {
        return "modules.module." + this.getName() + ".name";
    }

    public List<ModuleArgument> getArguments() {
        return this.arguments;
    }

    public ModuleArgument getArgument(String name) {
        for (ModuleArgument argument : this.getArguments()) {
            if (argument.getName().equals(name)) {
                return argument;
            }
        }
        return null;
    }

    // 以下方法才需要去覆写

    public void onClickGuiUpdate() {

    }

    public void onEnable() {

    }

    public void onDisable() {

    }

    public void onEnabling() {

    }

    public int getDefaultKeyCode() {
        return Keyboard.KEY_NONE;
    }
}
