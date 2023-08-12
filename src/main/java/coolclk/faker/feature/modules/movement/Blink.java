package coolclk.faker.feature.modules.movement;

import coolclk.faker.event.PacketSendEvent;
import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.modules.ModuleCategory;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@ModuleInfo(name = "Blink", category = ModuleCategory.Movement)
public class Blink extends Module {
    @SubscribeEvent
    public void onPacketSend(PacketSendEvent event) {
        if (this.getEnable()) {
            if (event.packet instanceof C03PacketPlayer) {
                event.setCanceled(true);
            }
        }
    }
}
