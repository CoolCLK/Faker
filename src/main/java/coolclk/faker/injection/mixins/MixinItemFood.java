package coolclk.faker.injection.mixins;

import coolclk.faker.modules.root.movement.Jesus;
import coolclk.faker.modules.root.player.FastEat;
import coolclk.faker.modules.root.render.NoInvisible;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemFood;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemFood.class)
public class MixinItemFood {
    @Inject(at = @At(value = "RETURN"), method = "getMaxItemUseDuration", cancellable = true)
    public void getMaxItemUseDuration(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(cir.getReturnValue() - (FastEat.INSTANCE.getEnable() ? FastEat.INSTANCE.getArgument("speed").getNumberValueI() : 0));
    }
}
