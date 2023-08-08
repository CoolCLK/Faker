package coolclk.faker.injection.mixins;

import coolclk.faker.modules.root.player.Regen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.FoodStats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FoodStats.class)
public class MixinFoodStats {
    @Unique
    private int faker$foodTimer;

    @Inject(at = @At(value = "RETURN"), method = "onUpdate")
    public void onUpdate(EntityPlayer player, CallbackInfo ci) {
        if (Regen.INSTANCE.getEnable()) {
            this.faker$foodTimer++;

            if (this.faker$foodTimer >= Regen.INSTANCE.getArgument("speed").getNumberValueF()) {
                player.heal(Regen.INSTANCE.getArgument("heal").getNumberValueF() - 1);
                this.faker$foodTimer = 0;
            }
        }
    }
}
