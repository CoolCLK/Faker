package coolclk.faker.injection.mixins;

import coolclk.faker.modules.root.render.ESP;
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
    @Inject(at = @At(value = "RETURN"), method = "shouldSideBeRendered", cancellable = true)
    public void shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue((ESP.INSTANCE.getEnable() && ((ESP.INSTANCE.getArgument("chest").getBooleanValue() && (worldIn.getBlockState(pos).getBlock() == Blocks.chest || worldIn.getBlockState(pos).getBlock() == Blocks.ender_chest || worldIn.getBlockState(pos).getBlock() == Blocks.trapped_chest)) || (ESP.INSTANCE.getArgument("bed").getBooleanValue() && worldIn.getBlockState(pos).getBlock() == Blocks.bed))) || cir.getReturnValue());
    }
}
