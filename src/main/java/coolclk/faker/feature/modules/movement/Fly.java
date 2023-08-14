package coolclk.faker.feature.modules.movement;

import coolclk.faker.feature.ModuleHandler;
import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.api.SettingsDouble;
import coolclk.faker.feature.api.SettingsFloat;
import coolclk.faker.feature.modules.ModuleCategory;
import coolclk.faker.feature.modules.render.FreeCam;
import coolclk.faker.util.ModuleUtil;
import org.lwjgl.input.Keyboard;

@ModuleInfo(name = "Fly", category = ModuleCategory.Movement, defaultKeycode = Keyboard.KEY_F)
public class Fly extends Module {
    public SettingsFloat flyHorizontalSpeed = new SettingsFloat(this, "flyHorizontalSpeed", 0.1F, 0F, 5F);
    public SettingsDouble flyVerticalSpeed = new SettingsDouble(this, "flyVerticalSpeed", 0.1D, 0D, 5D);

    @Override
    public void onRegister() {
        if (this.getEnable()) {
            this.toggleModule();
        }
    }

    @Override
    public void onEnabling() {
        ModuleUtil.gEP().capabilities.isFlying = true;
        ModuleUtil.gEP().moveEntityWithHeading(ModuleUtil.gEP().movementInput.moveStrafe * flyHorizontalSpeed.getValue(), ModuleUtil.gEP().movementInput.moveForward * flyHorizontalSpeed.getValue());
        if (ModuleUtil.gM().gameSettings.keyBindJump.isKeyDown() || ModuleUtil.gM().gameSettings.keyBindSneak.isKeyDown()) {
            ModuleUtil.gEP().motionY = (ModuleUtil.gM().gameSettings.keyBindJump.isKeyDown() ? flyVerticalSpeed.getValue() : 0) + (ModuleUtil.gM().gameSettings.keyBindSneak.isKeyDown() ? -flyVerticalSpeed.getValue() : 0);
        } else {
            ModuleUtil.gEP().motionY = 0;
        }
    }

    @Override
    public void onDisable() {
        ModuleUtil.gEP().capabilities.isFlying = ModuleHandler.findModule(FreeCam.class).getEnable();
    }
}
