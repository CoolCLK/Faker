package coolclk.faker.feature.modules.player;

import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.modules.ModuleCategory;
import coolclk.faker.util.ModuleUtil;
import net.minecraft.world.WorldSettings;

@ModuleInfo(name = "DisableDamage", category = ModuleCategory.Player)
public class DisableDamage extends Module {
    @Override
    public void onEnabling() {
        if (!ModuleUtil.gEP().capabilities.disableDamage) {
            ModuleUtil.gEP().capabilities.disableDamage = true;
        }
    }

    @Override
    public void onDisable() {
        ModuleUtil.gEP().capabilities.disableDamage = ModuleUtil.gPC().getCurrentGameType() == WorldSettings.GameType.CREATIVE;
    }
}
