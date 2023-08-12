package coolclk.faker.feature.modules.render;

import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.modules.ModuleCategory;
import coolclk.faker.util.ModuleUtil;
import net.minecraft.entity.Entity;

@ModuleInfo(name = "ESP", category = ModuleCategory.Render)
public class ESP extends Module {
    @Override
    public void onEnabling() {
        for (Entity entity : ModuleUtil.findEntitiesWithDistance(ModuleUtil.gEP(), -1)) {
            entity.ignoreFrustumCheck = true;
        }
    }
}
