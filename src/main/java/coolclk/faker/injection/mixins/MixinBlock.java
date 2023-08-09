package coolclk.faker.injection.mixins;

import coolclk.faker.modules.root.render.ESP;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public class MixinBlock {
    @Inject(at = @At(value = "RETURN"), method = "shouldSideBeRendered", cancellable = true)
    public void shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue((ESP.INSTANCE.getEnable() && ((ESP.INSTANCE.getArgument("chest").getBooleanValue() && (blockState.getBlock() == Blocks.CHEST || blockState.getBlock() == Blocks.ENDER_CHEST || blockState.getBlock() == Blocks.TRAPPED_CHEST)) || (ESP.INSTANCE.getArgument("bed").getBooleanValue() && blockState.getBlock() == Blocks.BED))) || cir.getReturnValue());
    }
}
