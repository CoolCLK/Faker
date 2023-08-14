package coolclk.faker.injection.mixins;

import coolclk.faker.feature.ModuleHandler;
import coolclk.faker.feature.modules.movement.NoSlow;
import coolclk.faker.util.ModuleUtil;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public abstract class MixinWorld {
    @Inject(method = "handleMaterialAcceleration", at = @At(value = "RETURN"), cancellable = true)
    public void handleMaterialAcceleration(AxisAlignedBB bb, Material materialIn, Entity entityIn, CallbackInfoReturnable<Boolean> cir) {
        if (ModuleHandler.findModule(NoSlow.class).getEnable() && entityIn == ModuleUtil.gEP()) {
            cir.setReturnValue(false);
        }
    }
}
