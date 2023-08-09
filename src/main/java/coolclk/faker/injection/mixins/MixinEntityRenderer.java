package coolclk.faker.injection.mixins;

import coolclk.faker.modules.root.render.HurtCam;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(EntityRenderer.class)
public abstract class MixinEntityRenderer {
    /**
     * @author CoolCLK
     * @reason Modify hurt camera rotation.
     */
    @Overwrite
    private void hurtCameraEffect(float partialTicks) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.getRenderViewEntity() instanceof EntityLivingBase) {
            float percent = HurtCam.INSTANCE.getEnable() ? HurtCam.INSTANCE.getArgument("per").getNumberValueF() : 1;

            EntityLivingBase entitylivingbase = (EntityLivingBase) mc.getRenderViewEntity();
            float f = (float) entitylivingbase.hurtTime - partialTicks;

            if (entitylivingbase.getHealth() <= 0.0F) {
                float f1 = (float) entitylivingbase.deathTime + partialTicks;
                GlStateManager.rotate(40.0F - 8000.0F / (f1 + 200.0F), 0.0F, 0.0F, 1.0F);
            }

            if (f < 0.0F) {
                return;
            }


            f = f / (float) entitylivingbase.maxHurtTime;
            f = MathHelper.sin(f * f * f * f * (float)Math.PI);
            f *= percent;
            float f2 = entitylivingbase.attackedAtYaw;
            f2 *= percent;
            GlStateManager.rotate(-f2, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(-f * 14.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(f2, 0.0F, 1.0F, 0.0F);
        }
    }
}
