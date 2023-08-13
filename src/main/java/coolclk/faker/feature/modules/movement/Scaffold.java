package coolclk.faker.feature.modules.movement;

import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.modules.ModuleCategory;
import coolclk.faker.util.ModuleUtil;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

@ModuleInfo(name = "Scaffold", category = ModuleCategory.None)
public class Scaffold extends Module {
    @Override
    public void onEnabling() {
        BlockPos airPos = ModuleUtil.gEP().getPosition().add(0, -1, 0);
        if (ModuleUtil.gEP().getEntityWorld().getBlockState(airPos).getBlock() == Blocks.air) {
            ModuleUtil.gPC().onPlayerRightClick(ModuleUtil.gEP(), ModuleUtil.gW(), ModuleUtil.gEP().getHeldItem(), airPos, EnumFacing.DOWN, new Vec3(0, 0, 0));
        }
    }
}
