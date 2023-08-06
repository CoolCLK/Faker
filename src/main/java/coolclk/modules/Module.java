package coolclk.modules;

import coolclk.Faker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class Module {
    public static class ModuleArgument {
        public enum ArgumentType {
            SWITCH,
            NUMBER
        }

        private final String name;
        private boolean booleanValue;
        private double numberValue;
        private final double numberMinValue;
        private final double numberMaxValue;
        private final ArgumentType valueType;

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

        public double getNumberValue() {
            return this.numberValue;
        }

        public Integer getIntegerNumberValue() {
            return Integer.parseInt(this.getLongNumberValue().toString());
        }

        public Long getLongNumberValue() {
            return Math.round(this.getNumberValue());
        }

        public double getNumberMinValue() {
            return this.numberMinValue;
        }

        public double getNumberMaxValue() {
            return this.numberMaxValue;
        }

        public void setNumberValue(double value) {
            if (value <= this.getNumberMinValue()) {
                value = this.getNumberMinValue();
            }
            if (value >= this.getNumberMaxValue()) {
                value = this.getNumberMaxValue();
            }
            this.numberValue = value;
        }
    }

    private final String name;
    private final List<ModuleArgument> arguments;

    private boolean enable = false;
    private final KeyBinding keyBinding;

    public Module(String name) {
        this.name = name;
        this.arguments = new ArrayList<ModuleArgument>();
        this.keyBinding = new KeyBinding(this.getDisplayName(), this.getDefaultKeyCode(), Faker.NAME);
    }

    public Module(String name, List<ModuleArgument> arguments) {
        this.name = name;
        this.arguments = arguments;
        this.keyBinding = new KeyBinding(this.getDisplayName(), this.getDefaultKeyCode(), Faker.NAME);
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
        if (this.enable) {
            this.onEnable();
        } else {
            this.onDisable();
        }
    }

    public boolean getEnable() {
        return this.enable;
    }

    public String getDisplayName() {
        return I18n.format("modules.module." + this.getName() + ".name");
    }

    public KeyBinding getKeyBinding() {
        return this.keyBinding;
    }

    public String getName() {
        return this.name;
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

    protected EntityPlayerSP getEntityPlayer() {
        return Minecraft.getMinecraft().thePlayer;
    }

    protected PlayerControllerMP getPlayerController() {
        return Minecraft.getMinecraft().playerController;
    }

    // 以下方法需要被覆写

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
