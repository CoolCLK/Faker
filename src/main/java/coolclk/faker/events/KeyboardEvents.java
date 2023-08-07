package coolclk.faker.events;

import coolclk.faker.modules.Module;
import coolclk.faker.modules.ModuleHandler;
import coolclk.faker.modules.ModuleType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class KeyboardEvents {
    public void registerKey() {
        ModuleHandler.registerKey();
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        for (ModuleType group : ModuleHandler.getAllModules()) {
            for (Module module : group.getModules()) {
                if (module.getKeyBinding().isPressed()) {
                    module.setEnable(!module.getEnable());
                }
            }
        }
    }
}
