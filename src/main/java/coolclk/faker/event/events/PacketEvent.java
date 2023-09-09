package coolclk.faker.event.events;

import coolclk.faker.event.api.CancelableEvent;
import net.minecraft.network.Packet;

public class PacketEvent extends CancelableEvent {
    public enum Type {
        SEND,
        RECEIVE
    }
    protected Packet<?> packet;
    protected final Type type;

    public PacketEvent(Type type, Packet<?> packet) {
        this.type = type;
        this.packet = packet;
    }

    public Type getType() {
        return this.type;
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
}
