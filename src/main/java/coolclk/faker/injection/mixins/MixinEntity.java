package coolclk.faker.injection.mixins;

import coolclk.faker.feature.ModuleHandler;
import coolclk.faker.feature.modules.combat.Hitbox;
import coolclk.faker.feature.modules.movement.NoSlow;
import coolclk.faker.feature.modules.render.NoInvisible;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class MixinEntity {
    @Shadow protected boolean isInWeb;

    @Shadow public float width;

    @Shadow public float height;

    @Shadow public abstract void setEntityBoundingBox(AxisAlignedBB bb);

    @Shadow public abstract AxisAlignedBB getEntityBoundingBox();

    @Inject(method = "isInvisible", at = @At(value = "RETURN"), cancellable = true)
    public void isInvisible(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(!ModuleHandler.findModule(NoInvisible.class).getEnable() && cir.getReturnValue());
    }

    @Inject(method = "setInWeb", at = @At(value = "RETURN"))
    public void setInWeb(CallbackInfo ci) {
        if (ModuleHandler.findModule(NoSlow.class).getEnable()) {
            this.isInWeb = false;
        }
    }

    @Inject(method = "setSize", at = @At(value = "RETURN"))
    protected void setSize(float width, float height, CallbackInfo ci) {
        if (ModuleHandler.findModule(Hitbox.class).getEnable()) {
            float size = ModuleHandler.findModule(Hitbox.class).size.getValue();
            width *= size;
            height *= size;
            if (width != this.width || height != this.height) {
                this.width = width;
                this.height = height;
                this.setEntityBoundingBox(new AxisAlignedBB(this.getEntityBoundingBox().minX, this.getEntityBoundingBox().minY, this.getEntityBoundingBox().minZ, this.getEntityBoundingBox().minX + (double) this.width, this.getEntityBoundingBox().minY + (double) this.height, this.getEntityBoundingBox().minZ + (double) this.width));
            }
        }
    }
}
