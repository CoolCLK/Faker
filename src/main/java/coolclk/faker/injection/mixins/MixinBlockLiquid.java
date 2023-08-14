package coolclk.faker.injection.mixins;

import coolclk.faker.feature.ModuleHandler;
import coolclk.faker.feature.modules.movement.Jesus;
import net.minecraft.block.BlockLiquid;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockLiquid.class)
public class MixinBlockLiquid {
    @Inject(method = "isBlockSolid", at = @At(value = "RETURN"), cancellable = true)
    public void isBlockSolid(IBlockAccess worldIn, BlockPos pos, EnumFacing side, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(ModuleHandler.findModule(Jesus.class).getEnable() || cir.getReturnValue());
    }
}
