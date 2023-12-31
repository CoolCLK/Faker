package coolclk.faker.injection.mixins;

import coolclk.faker.feature.ModuleHandler;
import coolclk.faker.feature.modules.movement.Jesus;
import coolclk.faker.feature.modules.movement.NoSlow;
import coolclk.faker.feature.modules.render.NoInvisible;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class MixinEntity {
    @Shadow protected boolean isInWeb;

    @Inject(method = "isInvisible", at = @At(value = "RETURN"), cancellable = true)
    public void isInvisible(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(!ModuleHandler.findModule(NoInvisible.class).getEnable() && cir.getReturnValue());
    }

    @Inject(method = "setInWeb", at = @At(value = "RETURN"))
    public void setInWeb(CallbackInfo ci) {
        if (ModuleHandler.findModule(NoSlow.class).getEnable()) {
            this.isInWeb = false;
        }
    }

    @Inject(method = "isInsideOfMaterial", at = @At(value = "HEAD"), cancellable = true)
    public void isInsideOfMaterial(Material materialIn, CallbackInfoReturnable<Boolean> cir)  {
        if (ModuleHandler.findModule(Jesus.class).getEnable() && materialIn.isLiquid()) {
            cir.setReturnValue(false);
        }
    }
}
