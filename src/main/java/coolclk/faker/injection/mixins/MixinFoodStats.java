package coolclk.faker.injection.mixins;

import coolclk.faker.feature.ModuleHandler;
import coolclk.faker.feature.modules.player.Regen;
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

    @Inject(method = "onUpdate", at = @At(value = "RETURN"))
    public void onUpdate(EntityPlayer player, CallbackInfo ci) {
        if (ModuleHandler.findModule(Regen.class).getEnable()) {
            this.faker$foodTimer++;

            if (this.faker$foodTimer >= ModuleHandler.findModule(Regen.class).healSpeed.getValue()) {
                player.heal(ModuleHandler.findModule(Regen.class).healHealth.getValue());
                this.faker$foodTimer = 0;
            }
        }
    }
}
