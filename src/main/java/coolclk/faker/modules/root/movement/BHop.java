package coolclk.faker.modules.root.movement;

import coolclk.faker.modules.Module;
import coolclk.faker.util.ModuleUtil;

import java.util.Arrays;
import java.util.Collections;

public class BHop extends Module {
    public static BHop INSTANCE = new BHop();

    private float movedStep = 0;

    public BHop() {
        super("BHop", Collections.singletonList(new ModuleArgument("step", 0.5, 0, 4)));
    }

    @Override
    public void onEnabling() {
        float movement = Math.abs(ModuleUtil.gEP().movementInput.moveForward) + Math.abs(ModuleUtil.gEP().movementInput.moveStrafe);
        if (movement > 0) {
            movedStep += Math.abs(movement) / 20;
        } else {
            movedStep = 0;
        }

        if (movedStep > this.getArgument("step").getNumberValueD() && !ModuleUtil.gEP().isSneaking() && (ModuleUtil.gEP().onGround && !ModuleUtil.gEP().isInWater() && !ModuleUtil.gEP().isInLava())) {
            ModuleUtil.gEP().jump();
            movedStep = 0;
        }
    }
}
