package coolclk.faker.event;

import coolclk.faker.event.events.KeyInputEvent;
import coolclk.faker.event.events.ModuleChangeStatEvent;
import coolclk.faker.event.events.PlayerTickEvent;
import coolclk.faker.feature.ModuleHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ForgeEventHandler {
    @SideOnly(value = Side.CLIENT)
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        EventHandler.post(new KeyInputEvent());
    }

    @SideOnly(value = Side.CLIENT)
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        EventHandler.post(new PlayerTickEvent());
    }

    @SideOnly(value = Side.CLIENT)
    @SubscribeEvent
    public void onModuleChangeStat(ModuleChangeStatEvent event) {
        ModuleHandler.saveConfigs();
    }

    @SideOnly(value = Side.CLIENT)
    @SubscribeEvent
    public void onClientConnectedToServer(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        ModuleHandler.disableUnlikeableModules();
    }
}
