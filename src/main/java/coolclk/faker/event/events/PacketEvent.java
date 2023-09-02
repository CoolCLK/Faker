package coolclk.faker.event.events;

import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class PacketEvent extends Event {
    public Packet<?> packet;

    public PacketEvent(Packet<?> packet) {
        this.packet = packet;
    }

    public Packet<?> getPacket() {
        return this.packet;
    }

    public void setPacket(Packet<?> packet) {
        this.packet = packet;
    }

    public boolean isTypeOf(Class<?>... classes) {
        boolean typeOf = true;
        for (Class<?> clazz : classes) {
            Class<?> superclass = this.getPacket().getClass();
            boolean subTypeOf = false;
            while ((superclass = superclass.getSuperclass()) != null) if (this.getPacket().getClass() == clazz) subTypeOf = true;
            if (!subTypeOf) {
                typeOf = false;
                break;
            }
        }
        return typeOf;
    }

    @Cancelable
    public static class Send extends PacketEvent {
        public Send(Packet<?> packet) {
            super(packet);
        }
    }

    @Cancelable
    public static class Receive extends PacketEvent {
        public Receive(Packet<?> packet) {
            super(packet);
        }
    }
}
