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
    /*
    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;)V", at = @At(value = "HEAD"), cancellable = true)
    public void sendPacket(Packet<?> packetIn, CallbackInfo ci) {
        if (MinecraftForge.EVENT_BUS.post(new PacketSendEvent(packetIn))) ci.cancel();
    }

    @Inject(method = "sendPacket(Lnet/minecraft/network/Packet;Lio/netty/util/concurrent/GenericFutureListener;[Lio/netty/util/concurrent/GenericFutureListener;)V", at = @At(value = "HEAD"), cancellable = true)
    public void sendPacketEvent(Packet<?> packetIn, GenericFutureListener<? extends Future<? super Void>> listener, GenericFutureListener<? extends Future<? super Void>>[] listeners, CallbackInfo ci) {
        if (MinecraftForge.EVENT_BUS.post(new PacketSendEvent(packetIn))) ci.cancel();
    }
    */

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
