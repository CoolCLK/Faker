package coolclk.faker.events;

import coolclk.faker.modules.ModuleHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class PlayerEvents {
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        ModuleHandler.tickEvent();
    }
}
