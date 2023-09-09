package coolclk.faker.feature.modules.movement;

import coolclk.faker.event.events.PlayerUpdateEvent;
import coolclk.faker.feature.ModuleHandler;
import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.api.SettingsDouble;
import coolclk.faker.feature.api.SettingsFloat;
import coolclk.faker.feature.modules.ModuleCategory;
import coolclk.faker.feature.modules.render.FreeCam;
import coolclk.faker.util.ModuleUtil;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.input.Keyboard;

@ModuleInfo(name = "Fly", category = ModuleCategory.Movement, defaultKeycode = Keyboard.KEY_F)
public class Fly extends Module {
    public SettingsFloat flyHorizontalSpeed = new SettingsFloat(this, "flyHorizontalSpeed", 0.1F, 0F, 5F);
    public SettingsDouble flyVerticalSpeed = new SettingsDouble(this, "flyVerticalSpeed", 0.1D, 0D, 5D);
    private EntityPlayer entityPlayer;

    @Override
    public void onRegister() {
        if (this.getEnable()) {
            this.toggleModule();
        }
    }

    @Override
    public void onUpdate(PlayerUpdateEvent event) {
        entityPlayer = event.getEntityPlayer();
        event.getEntityPlayer().capabilities.isFlying = true;
        event.getEntityPlayer().moveEntityWithHeading(event.getEntityPlayer().movementInput.moveStrafe * flyHorizontalSpeed.getValue(), event.getEntityPlayer().movementInput.moveForward * flyHorizontalSpeed.getValue());
        if (ModuleUtil.gM().gameSettings.keyBindJump.isKeyDown() || ModuleUtil.gM().gameSettings.keyBindSneak.isKeyDown()) {
            event.getEntityPlayer().motionY = (ModuleUtil.gM().gameSettings.keyBindJump.isKeyDown() ? flyVerticalSpeed.getValue() : 0) + (ModuleUtil.gM().gameSettings.keyBindSneak.isKeyDown() ? -flyVerticalSpeed.getValue() : 0);
        } else {
            event.getEntityPlayer().motionY = 0;
        }
    }

    @Override
    public void onDisable() {
        entityPlayer.capabilities.isFlying = ModuleHandler.findModule(FreeCam.class).getEnable();
    }
}
