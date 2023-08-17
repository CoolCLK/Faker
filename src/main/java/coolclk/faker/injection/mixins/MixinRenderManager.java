package coolclk.faker.injection.mixins;

import coolclk.faker.util.ModuleUtil;
import net.minecraft.client.renderer.entity.RenderManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderManager.class)
public class MixinRenderManager {
    @Shadow private double renderPosZ;

    @Shadow private double renderPosY;

    @Shadow private double renderPosX;

    @Inject(method = "setRenderPosition", at = @At(value = "RETURN"))
    public void setRenderPosition(double renderPosXIn, double renderPosYIn, double renderPosZIn, CallbackInfo ci) {
        ModuleUtil.renderPosX = renderPosX;
        ModuleUtil.renderPosY = renderPosY;
        ModuleUtil.renderPosZ = renderPosZ;
    }
}
