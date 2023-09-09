package coolclk.faker.injection.mixins;

import coolclk.faker.event.EventHandler;
import coolclk.faker.event.events.PlayerAttackEntityEvent;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerControllerMP.class)
public class MixinPlayerControllerMP {
    @Inject(method = "attackEntity", at = @At(value = "INVOKE", shift = At.Shift.BEFORE, target = "Lnet/minecraft/entity/player/EntityPlayer;attackTargetEntityWithCurrentItem(Lnet/minecraft/entity/Entity;)V"), cancellable = true)
    public void attackEntity(EntityPlayer playerIn, Entity targetEntity, CallbackInfo ci) {
        PlayerAttackEntityEvent event = new PlayerAttackEntityEvent(playerIn, targetEntity);
        EventHandler.post(event);
        if (event.isCanceled()) ci.cancel();
    }
}
