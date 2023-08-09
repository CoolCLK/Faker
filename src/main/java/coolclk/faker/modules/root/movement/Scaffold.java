package coolclk.faker.modules.root.movement;

import coolclk.faker.modules.Module;
import coolclk.faker.util.ModuleUtil;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class Scaffold extends Module {
    public static Scaffold INSTANCE = new Scaffold();

    public Scaffold() {
        super("Scaffold");
    }

    @Override
    public void onEnabling() {
        BlockPos airPos = ModuleUtil.gEP().getPosition().add(0, -1, 0);
        if (ModuleUtil.gEP().getEntityWorld().getBlockState(airPos).getBlock() == Blocks.AIR) {
            ModuleUtil.gPC().processRightClickBlock(ModuleUtil.gEP(), ModuleUtil.gW(), airPos, EnumFacing.UP, new Vec3d(0, 0, 0), EnumHand.MAIN_HAND);
        }
    }
}
