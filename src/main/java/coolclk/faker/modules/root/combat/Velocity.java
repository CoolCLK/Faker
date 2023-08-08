package coolclk.faker.modules.root.combat;

import coolclk.faker.modules.Module;
import coolclk.faker.util.ModuleUtil;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;

public class Velocity extends Module {
    public static Velocity INSTANCE = new Velocity();

    public Velocity() {
        super("Velocity", Arrays.asList(new ModuleArgument("full", true), new ModuleArgument("counteractPer", 0.5, 0.1, 1)));
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        if (this.getEnable()) {
            if (event.entity == ModuleUtil.gEP()) {
                if (this.getArgument("full").getBooleanValue()) {
                    ModuleUtil.gEP().setVelocity(0, 0, 0);
                } else {
                    float per = this.getArgument("counteractPer").getNumberValueF();
                    ModuleUtil.gEP().motionX *= per;
                    ModuleUtil.gEP().motionY *= per;
                    ModuleUtil.gEP().motionZ *= per;
                }
            }
        }
    }
}
