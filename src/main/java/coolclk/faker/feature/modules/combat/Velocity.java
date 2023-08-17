package coolclk.faker.feature.modules.combat;

import coolclk.faker.event.UpdateTimerEvent;
import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.api.SettingsFloat;
import coolclk.faker.feature.api.SettingsModeString;
import coolclk.faker.feature.modules.ModuleCategory;
import coolclk.faker.util.ModuleUtil;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@ModuleInfo(name = "Velocity", category = ModuleCategory.Combat)
public class Velocity extends Module {
    public SettingsModeString mode = new SettingsModeString(this, "custom", "timer", "custom");
    public SettingsFloat timerSpeed = new SettingsFloat(this, "timerSpeed", 0.5F, 0F, 2F);
    public SettingsFloat counteractPercent = new SettingsFloat(this, "counteractPercent", 0.5F, 0F, 1F) {
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

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        if (this.getEnable()) {
            if (event.entityLiving == ModuleUtil.gEP()) {
                if (mode.getValue().equals("timer")) {
                    slowing = 5;
                } else {
                    float per = 1 - counteractPercent.getValue();
                    ModuleUtil.gEP().motionX *= per;
                    ModuleUtil.gEP().motionY *= per;
                    ModuleUtil.gEP().motionZ *= per;
                }
            }
        }
    }

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
}
