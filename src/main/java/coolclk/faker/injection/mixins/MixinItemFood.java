package coolclk.faker.injection.mixins;

import coolclk.faker.feature.ModuleHandler;
import coolclk.faker.feature.modules.player.FastEat;
import net.minecraft.item.ItemFood;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemFood.class)
public class MixinItemFood {
    @Inject(method = "getMaxItemUseDuration", at = @At(value = "RETURN"), cancellable = true)
    public void getMaxItemUseDuration(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(ModuleHandler.findModule(FastEat.class).getEnable() ? ModuleHandler.findModule(FastEat.class).duration.getValue() : cir.getReturnValue());
    }
}
