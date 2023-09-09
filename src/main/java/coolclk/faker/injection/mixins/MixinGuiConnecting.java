package coolclk.faker.injection.mixins;

import coolclk.faker.event.EventHandler;
import coolclk.faker.event.events.ClientConnectToServerEvent;
import net.minecraft.client.multiplayer.GuiConnecting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiConnecting.class)
public class MixinGuiConnecting {
    @Inject(method = "connect", at = @At(value = "HEAD"))
    private void connect(final String ip, final int port, CallbackInfo ci) {
        EventHandler.post(new ClientConnectToServerEvent(ip, port));
    }
}
