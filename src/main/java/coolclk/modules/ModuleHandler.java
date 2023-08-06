package coolclk.modules;

import coolclk.modules.root.combat.KillArea;
import coolclk.modules.root.render.ClickGui;
import coolclk.modules.root.render.HUD;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import java.util.Arrays;
import java.util.List;

public class ModuleHandler {
    private final static List<ModuleType> modules = Arrays.asList(
            new ModuleType("combat", 10, 10, new KillArea()),
            new ModuleType("movement", 70, 10),
            new ModuleType("render", 140, 10, new HUD(), new ClickGui()));

    public static List<ModuleType> getAllModules() {
        return modules;
    }

    public static Module getModule(String groupName, String moduleName) {
        for (ModuleType group : getAllModules()) {
            if (group.getName().equals(groupName)) {
                for (Module module : group.getModules()) {
                    if (module.getName().equals(moduleName)) {
                        return module;
                    }
                }
                break;
            }
        }
        return null;
    }

    public static void registerKey() {
        for (ModuleType group : ModuleHandler.getAllModules()) {
            for (Module module : group.getModules()) {
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
}
