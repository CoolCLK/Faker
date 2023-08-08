package coolclk.faker.injection.mixins;

import coolclk.faker.modules.root.player.Reach;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerControllerMP.class)
public class MixinPlayerControllerMP {
    @Inject(at = @At(value = "RETURN"), method = "getBlockReachDistance", cancellable = true)
    public void getBlockReachDistance(CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(Reach.INSTANCE.getEnable() ? Reach.INSTANCE.getArgument("blockDistance").getNumberValueF() : cir.getReturnValue());
    }
}
