package coolclk.faker.feature.modules.player;

import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.modules.ModuleCategory;
import coolclk.faker.util.ModuleUtil;
import net.minecraft.world.WorldSettings;

@ModuleInfo(name = "NoAntiBuild", category = ModuleCategory.Movement)
public class NoAntiBuild extends Module {
    @Override
    public void onEnabling() {
        if (!ModuleUtil.gEP().capabilities.allowEdit) {
            ModuleUtil.gEP().capabilities.allowEdit = true;
        }
    }

    @Override
    public void onDisable() {
        ModuleUtil.gEP().capabilities.allowEdit = ModuleUtil.gPC().getCurrentGameType() != WorldSettings.GameType.ADVENTURE;
    }
}
