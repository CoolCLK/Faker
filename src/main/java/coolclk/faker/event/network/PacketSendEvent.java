package coolclk.faker.event.network;

import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class PacketSendEvent extends Event {
    public Packet<?> packet;

    public PacketSendEvent(Packet<?> packet) {
        this.packet = packet;
    }
}
