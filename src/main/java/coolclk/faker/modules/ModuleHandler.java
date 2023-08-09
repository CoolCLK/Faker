package coolclk.faker.modules;

import coolclk.faker.modules.root.combat.AntiBot;
import coolclk.faker.modules.root.combat.Criticals;
import coolclk.faker.modules.root.combat.KillArea;
import coolclk.faker.modules.root.combat.Velocity;
import coolclk.faker.modules.root.movement.*;
import coolclk.faker.modules.root.player.Derp;
import coolclk.faker.modules.root.player.FastBreak;
import coolclk.faker.modules.root.player.Reach;
import coolclk.faker.modules.root.player.Timer;
import coolclk.faker.modules.root.render.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import java.util.Arrays;
import java.util.List;

public class ModuleHandler {
    private final static List<ModuleType> modules = Arrays.asList(
            new ModuleType("combat", 10, 10, AntiBot.INSTANCE, Criticals.INSTANCE, KillArea.INSTANCE, Velocity.INSTANCE, Reach.INSTANCE),
            new ModuleType("movement", 70, 10, BHop.INSTANCE, Fly.INSTANCE, ForceSprinting.INSTANCE, HighJump.INSTANCE, Jesus.INSTANCE, NoFall.INSTANCE, Scaffold.INSTANCE),
            new ModuleType("player", 140, 10, Derp.INSTANCE, FastBreak.INSTANCE, FastBreak.INSTANCE, Reach.INSTANCE, Scaffold.INSTANCE, Timer.INSTANCE),
            new ModuleType("render", 210, 10, ClickGui.INSTANCE, ESP.INSTANCE, FreeCam.INSTANCE, FullBright.INSTANCE, HUD.INSTANCE, HurtCam.INSTANCE, NoInvisible.INSTANCE, NoWeather.INSTANCE));

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
