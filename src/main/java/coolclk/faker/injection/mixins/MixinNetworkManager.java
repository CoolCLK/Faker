package coolclk.faker.injection.mixins;

import coolclk.faker.event.PacketSendEvent;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(NetworkManager.class)
public abstract class MixinNetworkManager {
    @Shadow public abstract boolean isChannelOpen();

    @Shadow protected abstract void flushOutboundQueue();

    @Shadow protected abstract void dispatchPacket(Packet<?> inPacket, GenericFutureListener<? extends Future<? super Void>>[] futureListeners);

    /**
     * @author CoolCLK
     * @reason Add event PacketSendEvent
     */
    @Overwrite
    public void sendPacket(Packet<?> packetIn) {
        if (MinecraftForge.EVENT_BUS.post(new PacketSendEvent(packetIn))) return;
        if (this.isChannelOpen()) {
            this.flushOutboundQueue();
            this.dispatchPacket(packetIn, null);
        }
    }
}
