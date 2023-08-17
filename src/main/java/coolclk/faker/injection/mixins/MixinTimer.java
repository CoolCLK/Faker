package coolclk.faker.injection.mixins;

import coolclk.faker.event.UpdateTimerEvent;
import coolclk.faker.feature.ModuleHandler;
import net.minecraft.util.Timer;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Timer.class)
public class MixinTimer {
    @Shadow public float timerSpeed;

    @Inject(method = "updateTimer", at = @At(value = "HEAD"))
    public void updateTimer(CallbackInfo ci) {
        UpdateTimerEvent event = new UpdateTimerEvent();
        MinecraftForge.EVENT_BUS.post(event);
        float multiplier = event.getMultiplier();
        this.timerSpeed = (ModuleHandler.findModule(coolclk.faker.feature.modules.player.Timer.class).getEnable() ? ModuleHandler.findModule(coolclk.faker.feature.modules.player.Timer.class).speed.getValue() : 1) * multiplier;
        coolclk.faker.feature.modules.player.Timer.currentTimerSpeed = this.timerSpeed;
    }
}
