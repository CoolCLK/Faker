package coolclk.faker.feature.modules.player;

import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.api.SettingsInteger;
import coolclk.faker.feature.modules.ModuleCategory;
import coolclk.faker.util.ModuleUtil;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

@ModuleInfo(name = "BedNuker", category = ModuleCategory.Player)
public class BedNuker extends Module {
    public SettingsInteger range = new SettingsInteger(this, "range", 5, 0, 10);
    public SettingsInteger count = new SettingsInteger(this, "count", 1, 0, 6);

    @Override
    public void onEnabling() {
        int counted = 0;
        for (int x = -range.getValue(); x < range.getValue(); x++) {
            for (int z = -range.getValue(); z < range.getValue(); z++) {
                for (int y = -range.getValue(); y < range.getValue(); y++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    if (ModuleUtil.gEP().getEntityWorld().getBlockState(pos).getBlock() == Blocks.bed) {
                        ModuleUtil.gPC().onPlayerDestroyBlock(pos, null);
                        counted++;
                        if (counted >= count.getValue()) {
                            break;
                        }
                    }
                }
            }
        }
    }
}
