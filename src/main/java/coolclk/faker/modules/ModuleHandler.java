package coolclk.faker.modules;

import coolclk.faker.modules.root.combat.AimAssist;
import coolclk.faker.modules.root.combat.KillArea;
import coolclk.faker.modules.root.movement.BHop;
import coolclk.faker.modules.root.movement.ForceSprinting;
import coolclk.faker.modules.root.movement.KeepRotating;
import coolclk.faker.modules.root.render.ClickGui;
import coolclk.faker.modules.root.render.HUD;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import java.util.Arrays;
import java.util.List;

public class ModuleHandler {
    private final static List<ModuleType> modules = Arrays.asList(
            new ModuleType("combat", 10, 10, AimAssist.INSTANCE, KillArea.INSTANCE),
            new ModuleType("movement", 70, 10, ForceSprinting.INSTANCE, BHop.INSTANCE, KeepRotating.INSTANCE),
            new ModuleType("render", 140, 10, HUD.INSTANCE, ClickGui.INSTANCE));

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
