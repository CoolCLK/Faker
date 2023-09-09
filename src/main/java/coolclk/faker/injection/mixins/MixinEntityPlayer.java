package coolclk.faker.injection.mixins;

import coolclk.faker.event.EventHandler;
import coolclk.faker.event.events.PlayerBreakSpeedEvent;
import coolclk.faker.event.events.PlayerJumpEvent;
import coolclk.faker.feature.ModuleHandler;
import coolclk.faker.feature.modules.movement.NoClip;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityPlayer.class)
public abstract class MixinEntityPlayer {
    @Shadow public abstract boolean isUser();

    public boolean noClip;

    public double motionY;

    @Inject(method = "onUpdate", at = @At(value = "RETURN"))
    public void onUpdate(CallbackInfo ci) {
        if (ModuleHandler.findModule(NoClip.class).getEnable()) {
            this.noClip = true;
        }
    }

    @Inject(method = "jump", at = @At(value = "RETURN"))
    public void jump(CallbackInfo ci) {
        if (isUser()) {
            PlayerJumpEvent event = new PlayerJumpEvent(this.motionY);
            EventHandler.post(event);
            this.motionY = event.getUpwardsMotion();
        }
    }

    @Inject(method = "getToolDigEfficiency", at = @At(value = "RETURN"), cancellable = true)
    public void getToolDigEfficiency(Block block, CallbackInfoReturnable<Float> cir) {
        if (isUser()) {
            PlayerBreakSpeedEvent event = new PlayerBreakSpeedEvent(block, cir.getReturnValue());
            EventHandler.post(event);
            cir.setReturnValue(event.getBreakSpeed());
        }
    }
}
