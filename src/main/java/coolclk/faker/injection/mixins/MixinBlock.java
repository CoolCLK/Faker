package coolclk.faker.injection.mixins;

import coolclk.faker.event.EventHandler;
import coolclk.faker.event.events.BlockActivatedEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public class MixinBlock {
    @Inject(method = "onBlockActivated", at = @At(value = "RETURN"))
    public void onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ, CallbackInfoReturnable<Boolean> cir) {
        EventHandler.post(new BlockActivatedEvent(worldIn, pos, state, playerIn, side, hitX, hitY, hitZ));
    }
}
