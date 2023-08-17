package coolclk.faker.feature.modules.movement;

import coolclk.faker.event.PacketEvent;
import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.api.SettingsInteger;
import coolclk.faker.feature.modules.ModuleCategory;
import coolclk.faker.util.ModuleUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

@ModuleInfo(name = "Blink", category = ModuleCategory.Movement, defaultKeycode = Keyboard.KEY_B)
public class Blink extends Module {
    public SettingsInteger maxPacketCounts = new SettingsInteger(this, "maxPacketCounts", 5, 0, 60);
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
        for (int i = 0; i < packets.size(); i += (maxPacketCounts.getValue() >= packets.size()) ? (packets.size() / maxPacketCounts.getValue()) : 1) {
            ModuleUtil.gNM().sendPacket(packets.get(i));
        }
    }

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
