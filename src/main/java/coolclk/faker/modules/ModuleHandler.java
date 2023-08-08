package coolclk.faker.modules;

import coolclk.faker.modules.root.combat.Criticals;
import coolclk.faker.modules.root.combat.KillArea;
import coolclk.faker.modules.root.movement.BHop;
import coolclk.faker.modules.root.movement.ForceSprinting;
import coolclk.faker.modules.root.movement.NoFall;
import coolclk.faker.modules.root.player.Derp;
import coolclk.faker.modules.root.player.Reach;
import coolclk.faker.modules.root.player.Timer;
import coolclk.faker.modules.root.render.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import java.util.Arrays;
import java.util.List;

public class ModuleHandler {
    private final static List<ModuleType> modules = Arrays.asList(
            new ModuleType("combat", 10, 10, Criticals.INSTANCE, KillArea.INSTANCE),
            new ModuleType("movement", 70, 10, ForceSprinting.INSTANCE, BHop.INSTANCE, NoFall.INSTANCE),
            new ModuleType("player", 140, 10, Derp.INSTANCE, Reach.INSTANCE, Timer.INSTANCE),
            new ModuleType("render", 210, 10, ClickGui.INSTANCE, FreeCam.INSTANCE, FullBright.INSTANCE, HUD.INSTANCE, HurtCam.INSTANCE, NoInvisible.INSTANCE));

    public static List<ModuleType> getAllModules() {
        return modules;
    }

    public static void registerKey() {
        for (ModuleType group : ModuleHandler.getAllModules()) {
            for (Module module : group.getModules()) {
                MinecraftForge.EVENT_BUS.register(module);
                ClientRegistry.registerKeyBinding(module.getKeyBinding());
            }
        }
    }

    public static void tickEvent() {
        for (ModuleType group : ModuleHandler.getAllModules()) {
            for (Module module : group.getModules()) {
                if (module.getEnable()) {
                    module.onEnabling();
                }
            }
        }
    }

    public static void saveConfigs() {

    }

    public static void loadConfigs() {

    }
}
