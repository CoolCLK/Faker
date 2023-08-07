package coolclk.faker.modules.root.combat;

import coolclk.faker.modules.Module;
import coolclk.faker.modules.ModuleUtil;
import net.minecraft.entity.Entity;

public class AimAssist extends Module {
    public static AimAssist INSTANCE = new AimAssist();

    public AimAssist() {
        super("AimAssist");
    }

    public void onEnabling() {
        Entity target = ModuleUtil.findClosestEntity(ModuleUtil.gEP());
        if (target != null) {
            ModuleUtil.gEP().setAngles(
                    (float) (Math.atan((double) (target.getPosition().getZ() - ModuleUtil.gEP().getPosition().getZ()) / (target.getPosition().getX() - ModuleUtil.gEP().getPosition().getX())) * ModuleUtil.pTPD2d(ModuleUtil.gEP().getPosition().getX(), ModuleUtil.gEP().getPosition().getZ(), target.getPosition().getX(), target.getPosition().getZ())),
                    (float) (Math.atan((ModuleUtil.gEP().getPosition().add(0, ModuleUtil.gEP().getEyeHeight(), 0).getY() - target.getPosition().add(0, target.getEyeHeight(), 0).getY()) / ModuleUtil.bPTBPD(ModuleUtil.gEP().getPosition().add(0, ModuleUtil.gEP().getEyeHeight(), 0), target.getPosition().add(0, target.getEyeHeight(), 0))) * ModuleUtil.bPTBPD(ModuleUtil.gEP().getPosition().add(0, ModuleUtil.gEP().getEyeHeight(), 0), target.getPosition().add(0, target.getEyeHeight(), 0)))
            );
        }
    }
}
