package coolclk.faker.injection.mixins;

import coolclk.faker.modules.root.movement.Jesus;
import coolclk.faker.modules.root.render.NoInvisible;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class MixinEntity {
    @Inject(at = @At(value = "RETURN"), method = "isInvisible", cancellable = true)
    public void isInvisible(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(!NoInvisible.INSTANCE.getEnable() && cir.getReturnValue());
    }

    @Inject(at = @At(value = "HEAD"), method = "handleWaterMovement", cancellable = true)
    public void handleWaterMovement(CallbackInfoReturnable<Boolean> cir) {
        if (Jesus.INSTANCE.getEnable()) {
            cir.setReturnValue(false);
        }
    }
}
