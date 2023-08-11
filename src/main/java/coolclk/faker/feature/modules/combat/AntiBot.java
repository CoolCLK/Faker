package coolclk.faker.feature.modules.combat;

import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.modules.ModuleCategory;
import coolclk.faker.util.ModuleUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.List;

@ModuleInfo(name = "AntiBot", category = ModuleCategory.Combat)
public class AntiBot extends Module {
    private final List<Entity> bots = new ArrayList<Entity>();

    @Override
    public void onEnabling() {
        bots.clear();
        for (EntityPlayer player : ModuleUtil.gW().playerEntities) {
            for (Character character : player.getName().toCharArray()) {
                if (!"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_".contains(character.toString())) {
                    bots.add(player);
                }
            }
        }
    }

    @Override
    public void onDisable() {
        bots.clear();
    }

    public boolean isBot(Entity player) {
        return bots.contains(player);
    }
}
