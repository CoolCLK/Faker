package coolclk.faker.injection.mixins;

import coolclk.faker.modules.root.render.ESP;
import coolclk.faker.modules.root.render.NameTag;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Render.class)
public class MixinRender<T extends Entity> {
    @Inject(at = @At(value = "RETURN"), method = "canRenderName", cancellable = true)
    protected void canRenderName(T entity, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue((NameTag.INSTANCE.getEnable() && entity instanceof EntityPlayer) || cir.getReturnValue());
    }

    @Inject(at = @At(value = "RETURN"), method = "shouldRender", cancellable = true)
    public void shouldRender(T livingEntity, ICamera camera, double camX, double camY, double camZ, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue((ESP.INSTANCE.getEnable() && (ESP.INSTANCE.getArgument("player").getBooleanValue() && livingEntity instanceof EntityPlayer) || (ESP.INSTANCE.getArgument("collection").getBooleanValue() && livingEntity instanceof EntityItem)) || cir.getReturnValue());
    }
}
