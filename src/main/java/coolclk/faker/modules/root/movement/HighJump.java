package coolclk.faker.modules.root.movement;

import coolclk.faker.modules.Module;
import coolclk.faker.util.ModuleUtil;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;
import java.util.Collections;

public class HighJump extends Module {
    public static HighJump INSTANCE = new HighJump();

    public HighJump() {
        super("HighJump", Collections.singletonList(new ModuleArgument("height", 0.42, 0.1, 3)));
    }

    @SubscribeEvent
    public void onLivingJump(LivingEvent.LivingJumpEvent event) {
        if (this.getEnable()) {
            if (event.entityLiving == ModuleUtil.gEP()) {
                event.entityLiving.motionY = this.getArgument("height").getNumberValueD();
            }
        }
    }
}
