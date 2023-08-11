package coolclk.faker.feature.modules.movement;

import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.api.SettingsDouble;
import coolclk.faker.feature.modules.ModuleCategory;
import coolclk.faker.util.ModuleUtil;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@ModuleInfo(name = "HighJump", category = ModuleCategory.Movement)
public class HighJump extends Module {
    public SettingsDouble jumpHeight = new SettingsDouble(this, "jumpHeight", 0.42, 0.42, 3D);

    @SubscribeEvent
    public void onLivingJump(LivingEvent.LivingJumpEvent event) {
        if (this.getEnable()) {
            if (event.entityLiving == ModuleUtil.gEP() && ModuleUtil.gEP().onGround) {
                event.entityLiving.motionY = jumpHeight.getValue();
            }
        }
    }
}
