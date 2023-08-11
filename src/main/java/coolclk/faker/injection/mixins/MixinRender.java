package coolclk.faker.injection.mixins;

import coolclk.faker.feature.ModuleHandler;
import coolclk.faker.feature.modules.render.ESP;
import coolclk.faker.feature.modules.render.NameTag;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Render.class)
public class MixinRender<T extends Entity> {
    @Inject(method = "canRenderName", at = @At(value = "RETURN"), cancellable = true)
    protected void canRenderName(T entity, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue((ModuleHandler.findModule(NameTag.class).getEnable() && entity instanceof EntityPlayer) || cir.getReturnValue());
    }

    @Inject(method = "shouldRender", at = @At(value = "RETURN"), cancellable = true)
    public void shouldRender(T livingEntity, ICamera camera, double camX, double camY, double camZ, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue((ModuleHandler.findModule(ESP.class).getEnable() && livingEntity instanceof EntityPlayer) || cir.getReturnValue());
    }
}
