package coolclk.faker.feature.modules.player;

import coolclk.faker.event.api.SubscribeEvent;
import coolclk.faker.event.events.PacketEvent;
import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.api.SettingsLong;
import coolclk.faker.feature.modules.ModuleCategory;
import coolclk.faker.util.ModuleUtil;
import net.minecraft.network.status.client.C01PacketPing;

import java.util.TimerTask;

@ModuleInfo(name = "PingSpoof", category = ModuleCategory.Player)
public class PingSpoof extends Module {
    public SettingsLong pingRange = new SettingsLong(this, "pingRange", 50L, 0L, 200L);
    private final java.util.Timer timer = new java.util.Timer();

    @SubscribeEvent
    public void onPacketSend(final PacketEvent event) {
        if (this.getEnable() && event.getType() == PacketEvent.Type.SEND) {
            if (event.isTypeOf(C01PacketPing.class)) {
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        ModuleUtil.gNM().sendPacket(event.getPacket());
                    }
                }, pingRange.getValue() + (long) (Math.random() * 100 - 50));
                event.setCanceled(true);
            }
        }
    }
}
