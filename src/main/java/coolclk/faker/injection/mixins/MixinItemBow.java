package coolclk.faker.injection.mixins;

import coolclk.faker.feature.ModuleHandler;
import coolclk.faker.feature.modules.combat.FastBow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemBow.class)
public abstract class MixinItemBow {
    @Shadow public abstract int getMaxItemUseDuration(ItemStack stack);

    @Inject(method = "getMaxItemUseDuration", at = @At(value = "RETURN"), cancellable = true)
    public void getMaxItemUseDuration(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(ModuleHandler.findModule(FastBow.class).bowDuration.getValue());
    }

    @Inject(method = "onItemRightClick", at = @At(value = "INVOKE", shift = At.Shift.BY, target = "Lnet/minecraft/entity/player/EntityPlayer;setItemInUse(Lnet/minecraft/item/ItemStack;I)V"))
    public void onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, CallbackInfoReturnable<ItemStack> cir) {
        playerIn.setItemInUse(itemStackIn, this.getMaxItemUseDuration(itemStackIn));
    }
}
