package coolclk.faker.modules.root;

import coolclk.faker.modules.ModuleType;
import coolclk.faker.modules.root.combat.AntiBot;
import coolclk.faker.modules.root.combat.Criticals;
import coolclk.faker.modules.root.combat.KillArea;
import coolclk.faker.modules.root.combat.Velocity;
import coolclk.faker.modules.root.player.Reach;

public class Combat extends ModuleType {
    public static Combat INSTANCE = new Combat();

    public Combat() {
        super("combat", 10, 10, AntiBot.INSTANCE, Criticals.INSTANCE, KillArea.INSTANCE, Velocity.INSTANCE, Reach.INSTANCE);
    }
}
