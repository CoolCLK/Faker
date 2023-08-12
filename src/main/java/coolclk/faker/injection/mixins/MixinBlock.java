package coolclk.faker.injection.mixins;

import coolclk.faker.feature.ModuleHandler;
import coolclk.faker.feature.modules.render.ChestESP;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public class MixinBlock {
    @Inject(method = "shouldSideBeRendered", at = @At(value = "RETURN"), cancellable = true)
    public void shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue((ModuleHandler.findModule(ChestESP.class).getEnable() && (worldIn.getBlockState(pos).getBlock() == Blocks.chest || worldIn.getBlockState(pos).getBlock() == Blocks.ender_chest || worldIn.getBlockState(pos).getBlock() == Blocks.trapped_chest)) || cir.getReturnValue());
    }
}
