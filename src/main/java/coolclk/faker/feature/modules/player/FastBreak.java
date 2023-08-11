package coolclk.faker.feature.modules.player;

import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.api.SettingsFloat;
import coolclk.faker.feature.modules.ModuleCategory;
import coolclk.faker.util.ModuleUtil;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@ModuleInfo(name = "FastBreak", category = ModuleCategory.Player)
public class FastBreak extends Module {
    public SettingsFloat breakSpeed = new SettingsFloat(this, "breakSpeed", 1F, 1F, 8F);

    @SubscribeEvent
    public void onBreak(PlayerEvent.BreakSpeed event) {
        if (this.getEnable()) {
            if (event.entityPlayer == ModuleUtil.gEP()) {
                event.newSpeed = event.originalSpeed + breakSpeed.getValue();
            }
        }
    }
}
