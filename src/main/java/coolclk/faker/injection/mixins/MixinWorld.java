package coolclk.faker.injection.mixins;

import coolclk.faker.feature.ModuleHandler;
import coolclk.faker.feature.modules.movement.Jesus;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public abstract class MixinWorld {
    @Shadow public abstract IBlockState getBlockState(BlockPos pos);

    @Inject(method = "handleMaterialAcceleration", at = @At(value = "RETURN"), cancellable = true)
    public void handleMaterialAcceleration(AxisAlignedBB bb, Material materialIn, Entity entityIn, CallbackInfoReturnable<Boolean> cir) {
        if (ModuleHandler.findModule(Jesus.class).getEnable()) {
            for (double x = -0.5; x <= 0.5; x += 0.1) {
                for (double z = -0.5; z <= 0.5; z += 0.1) {
                    Material material = this.getBlockState(entityIn.getPosition().add(x, -0.5, z)).getBlock().getMaterial();
                    if (material == Material.water || material == Material.lava) {
                        entityIn.onGround = true;
                        cir.setReturnValue(false);
                    }
                }
            }
        }
    }
}
