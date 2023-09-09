package coolclk.faker.injection.mixins;

import coolclk.faker.event.EventHandler;
import coolclk.faker.event.events.PlayerUpdateEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerSP.class)
public class MixinEntityPlayerSP {
    @Shadow protected Minecraft mc;

    @Inject(method = "onUpdate", at = @At(value = "INVOKE", shift = At.Shift.BEFORE, target = "Lnet/minecraft/client/entity/AbstractClientPlayer;onUpdate()V"))
    public void beforeUpdate(CallbackInfo ci) {
        EventHandler.post(new PlayerUpdateEvent(PlayerUpdateEvent.Shift.BEFORE, this.mc.thePlayer));
    }

    @Inject(method = "onUpdate", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/client/entity/AbstractClientPlayer;onUpdate()V"))
    public void afterUpdate(CallbackInfo ci) {
        EventHandler.post(new PlayerUpdateEvent(PlayerUpdateEvent.Shift.AFTER, this.mc.thePlayer));
    }
}
