package coolclk.faker.injection.mixins;

import coolclk.faker.feature.ModuleHandler;
import coolclk.faker.feature.modules.movement.Jesus;
import net.minecraft.block.material.MaterialLiquid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MaterialLiquid.class)
public class MixinMaterialLiquid {
    @Inject(method = "blocksMovement", at = @At(value = "RETURN"), cancellable = true)
    public void blocksMovement(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(ModuleHandler.findModule(Jesus.class).getEnable());
    }
}
