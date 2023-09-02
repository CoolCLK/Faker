package coolclk.faker.injection.mixins;

import coolclk.faker.event.events.PacketEvent;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.ThreadQuickExitException;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(NetworkManager.class)
public abstract class MixinNetworkManager {
    @Shadow public abstract boolean isChannelOpen();

    @Shadow protected abstract void flushOutboundQueue();

    @Shadow protected abstract void dispatchPacket(Packet<?> inPacket, GenericFutureListener<? extends Future<? super Void>>[] futureListeners);

    @Shadow private Channel channel;

    @Shadow private INetHandler packetListener;

    /**
     * @author CoolCLK
     * @reason Add event PacketEvent
     */
    @Overwrite
    public void sendPacket(Packet<?> packetIn) {
        PacketEvent event = new PacketEvent.Send(packetIn);
        if (MinecraftForge.EVENT_BUS.post(event)) return;
        packetIn = event.packet;
        if (this.isChannelOpen()) {
            this.flushOutboundQueue();
            this.dispatchPacket(packetIn, null);
        }
    }

    /**
     * @author CoolCLK
     * @reason Add event PacketEvent
     */
    @Overwrite
    protected void channelRead0(ChannelHandlerContext channelHandlerContextIn, Packet packetIn) {
        if (this.channel.isOpen()) {
            try {
                PacketEvent event = new PacketEvent.Receive(packetIn);
                if (MinecraftForge.EVENT_BUS.post(event)) return;
                packetIn = event.packet;
                packetIn.processPacket(this.packetListener);
            }
            catch (ThreadQuickExitException ignored)  {  }
        }
    }
}
