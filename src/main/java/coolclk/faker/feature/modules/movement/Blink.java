package coolclk.faker.feature.modules.movement;

import coolclk.faker.event.events.PacketEvent;
import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.api.SettingsInteger;
import coolclk.faker.feature.modules.ModuleCategory;
import coolclk.faker.util.ModuleUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

@ModuleInfo(name = "Blink", category = ModuleCategory.Movement, defaultKeycode = Keyboard.KEY_B)
public class Blink extends Module {
    public SettingsInteger maxPacketCounts = new SettingsInteger(this, "maxPacketCounts", 8, 0, 60);
    private final List<Packet<?>> packets = new ArrayList<Packet<?>>();

    @Override
    public boolean getCanKeepEnable() {
        return false;
    }

    @Override
    public void onEnable() {
        packets.clear();
    }

    @Override
    public void onDisable() {
        if (!packets.isEmpty()) {
            int sendCounts = 0;
            for (Packet<?> packet : packets) {
                if (Math.random() > 0.5 || packet == packets.get(packets.size() - 1)) {
                    ModuleUtil.gNM().sendPacket(packet);
                    if (sendCounts >= maxPacketCounts.getValue()) break;
                    sendCounts++;
                }
            }
        }
    }

    @SideOnly(value = Side.CLIENT)
    @SubscribeEvent
    public void onPacketSend(PacketEvent.Send event) {
        if (this.getEnable()) {
            if (event.isTypeOf(C03PacketPlayer.class)) {
                packets.add(event.getPacket());
                event.setCanceled(true);
            }
        }
    }
}
