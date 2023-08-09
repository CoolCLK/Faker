package coolclk.faker.injection.mixins;

import coolclk.faker.event.network.PacketSendEvent;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetworkManager.class)
public abstract class MixinNetworkManager {


    @Inject(at = @At(value = "HEAD"), method = "sendPacket(Lnet/minecraft/network/Packet;)V", cancellable = true)
    public void sendPacket(Packet<?> packetIn, CallbackInfo ci) {
        if (MinecraftForge.EVENT_BUS.post(new PacketSendEvent(packetIn))) ci.cancel();
    }

    @Inject(at = @At(value = "HEAD"), method = "sendPacket(Lnet/minecraft/network/Packet;Lio/netty/util/concurrent/GenericFutureListener;[Lio/netty/util/concurrent/GenericFutureListener;)V", cancellable = true)
    public void sendPacketEvent(Packet<?> packetIn, GenericFutureListener<? extends Future<? super Void>> listener, GenericFutureListener<? extends Future<? super Void>>[] listeners, CallbackInfo ci) {
        if (MinecraftForge.EVENT_BUS.post(new PacketSendEvent(packetIn))) ci.cancel();
    }
}
