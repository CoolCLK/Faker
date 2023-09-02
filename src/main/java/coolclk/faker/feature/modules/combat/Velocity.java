package coolclk.faker.feature.modules.combat;

import coolclk.faker.event.events.PacketEvent;
import coolclk.faker.event.events.UpdateTimerEvent;
import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.api.SettingsFloat;
import coolclk.faker.feature.api.SettingsModeString;
import coolclk.faker.feature.modules.ModuleCategory;
import coolclk.faker.util.ModuleUtil;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@ModuleInfo(name = "Velocity", category = ModuleCategory.Combat)
public class Velocity extends Module {
    public SettingsModeString mode = new SettingsModeString(this, "custom", "timer", "cancelPacket", "custom");
    public SettingsFloat timerSpeed = new SettingsFloat(this, "timerSpeed", 0.5F, 0F, 2F);
    public SettingsFloat counteractPercent = new SettingsFloat(this, "counteractPercent", 0.1F, 0F, 1F) {
        @Override
        public String getDisplayValue() {
            return (super.getValuePercent() * 100) + "%";
        }
    };

    private int slowing = 0;

    @Override
    public void onClickGuiUpdate() {
        counteractPercent.setDisplayVisible(mode.getValue().equals("custom"));
        timerSpeed.setDisplayVisible(mode.getValue().equals("timer"));
    }

    @Override
    public void onEnabling() {
        if (ModuleUtil.gEP().hurtTime > 0) {
            if (mode.getValue().equals("timer")) {
                slowing++;
            } else {
                float per = 1 - counteractPercent.getValue();
                ModuleUtil.gEP().motionX *= per;
                ModuleUtil.gEP().motionY *= per;
                ModuleUtil.gEP().motionZ *= per;
            }
        }
    }

    @SideOnly(value = Side.CLIENT)
    @SubscribeEvent
    public void onUpdateTimer(UpdateTimerEvent event) {
        if (this.getEnable()) {
            if (mode.getValue().equals("timer")) {
                if (slowing > 0) {
                    event.addMultiplier(timerSpeed.getValue());
                }
                slowing--;
            }
        }
    }

    @SideOnly(value = Side.CLIENT)
    @SubscribeEvent
    public void onUpdateTimer(PacketEvent.Receive event) {
        if (this.getEnable()) {
            if (mode.getValue().equals("cancelPacket") && event.isTypeOf(S08PacketPlayerPosLook.class)) {
                event.setPacket(new S08PacketPlayerPosLook(ModuleUtil.gEP().posX, ModuleUtil.gEP().posY, ModuleUtil.gEP().posZ, ((S08PacketPlayerPosLook) event.getPacket()).getYaw(), ((S08PacketPlayerPosLook) event.getPacket()).getPitch(), ((S08PacketPlayerPosLook) event.getPacket()).func_179834_f()));
            }
        }
    }
}
