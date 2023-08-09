package coolclk.faker.modules.root.combat;

import coolclk.faker.modules.Module;
import coolclk.faker.util.ModuleUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.List;

public class AntiBot extends Module {
    public static AntiBot INSTANCE = new AntiBot();

    private final List<Entity> bots = new ArrayList<Entity>();

    public AntiBot() {
        super("AntiBot");
    }

    @Override
    public void onEnabling() {
        bots.clear();
        for (EntityPlayer player : ModuleUtil.gW().playerEntities) {
            for (Character character : player.getName().toCharArray()) {
                if (!character.toString().contains("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_")) {
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
