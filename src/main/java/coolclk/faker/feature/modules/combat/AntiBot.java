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
    public void onEnable() {
        bots.clear();
    }

    @Override
    public void onEnabling() {
        List<Entity> possibleBots = new ArrayList<Entity>();
        for (EntityPlayer player : ModuleUtil.gW().playerEntities) {
            for (int i = 0; i < player.getName().length(); i++) {
                if (!"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_1234567890".contains(Character.toString(player.getName().charAt(i)))) {
                    possibleBots.add(player);
                    break;
                }
            }
        }
        bots.clear();
        bots.addAll(possibleBots);
    }

    @Override
    public void onDisable() {
        bots.clear();
    }

    public boolean isBot(Entity player) {
        return bots.contains(player);
    }
}
