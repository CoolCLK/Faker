package coolclk.faker.feature.modules.combat;

import coolclk.faker.event.UpdateTimerEvent;
import coolclk.faker.feature.api.*;
import coolclk.faker.feature.modules.ModuleCategory;
import net.minecraft.init.Items;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@ModuleInfo(name = "FastBow", category = ModuleCategory.Combat)
public class FastBow extends Module {
    public SettingsModeString mode = new SettingsModeString(this, "timer", "timer", "custom");
    public SettingsFloat timerSpeed = new SettingsFloat(this, "timerSpeed", 1.1F, 0F, 5F);
    public SettingsInteger bowSpeed = new SettingsInteger(this, "bowSpeed", 72000, 0, 72000);
    private float currentTimerSpeed = 1;

    @Override
    public void onClickGuiUpdate() {
        timerSpeed.setDisplayVisible(mode.getValue().equals("timer"));
        bowSpeed.setDisplayVisible(mode.getValue().equals("custom"));
    }

    @SubscribeEvent
    public void onPlayerUseItem(PlayerUseItemEvent.Start event) {
        if (this.getEnable() && event.item.getItem() == Items.bow) {
            if (mode.getValue().equals("timer")) {
                currentTimerSpeed = timerSpeed.getValue();
            } else if (mode.getValue().equals("custom")) {
                event.duration = bowSpeed.getValue();
            }
        }
    }

    @SubscribeEvent
    public void onPlayerUseItem(PlayerUseItemEvent.Stop event) {
        if (event.item.getItem() == Items.bow) {
            if (mode.getValue().equals("timer")) {
                currentTimerSpeed = 1;
            }
        }
    }

    @SubscribeEvent
    public void onUpdateTimer(UpdateTimerEvent event) {
        if (this.getEnable()) {
            if (mode.getValue().equals("timer")) {
                event.addMultiplier(currentTimerSpeed);
            }
        }
    }
}
