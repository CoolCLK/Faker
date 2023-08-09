package coolclk.faker.modules.root.combat;

import coolclk.faker.modules.Module;
import coolclk.faker.util.ModuleUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;

public class Velocity extends Module {
    public static Velocity INSTANCE = new Velocity();

    public Velocity() {
        super("Velocity", Arrays.asList(new ModuleArgument("full", true), new ModuleArgument("counteractPer", 0.5)));
    }

    @Override
    public void onClickGuiUpdate() {
        this.getArgument("counteractPer").setVisible(!this.getArgument("full").getBooleanValue());
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        if (event.getEntityLiving() instanceof EntityPlayer && ((EntityPlayer) event.getEntityLiving()).isUser()) {
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
