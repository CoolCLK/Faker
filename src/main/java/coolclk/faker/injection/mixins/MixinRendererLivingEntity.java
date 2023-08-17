package coolclk.faker.injection.mixins;

import coolclk.faker.feature.ModuleHandler;
import coolclk.faker.feature.modules.combat.AntiBot;
import coolclk.faker.feature.modules.render.ESP;
import coolclk.faker.util.ModuleUtil;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RendererLivingEntity.class)
public abstract class MixinRendererLivingEntity<T extends EntityLivingBase> {
    @Shadow public abstract ModelBase getMainModel();

    @Inject(method = "renderModel", at = @At(value = "RETURN"))
    public void renderModel(T entitylivingbaseIn, float p_77036_2_, float p_77036_3_, float p_77036_4_, float p_77036_5_, float p_77036_6_, float scaleFactor, CallbackInfo ci) {
        if (entitylivingbaseIn instanceof EntityPlayer && ModuleHandler.findModule(ESP.class).getEnable()) {
            EntityPlayer player = (EntityPlayer) entitylivingbaseIn;
            if (player != ModuleUtil.gEP() && !ModuleHandler.findModule(AntiBot.class).isBot(player)) {
                if (ModuleHandler.findModule(ESP.class).getEnable()) {
                    if (ModuleHandler.findModule(ESP.class).modes.getValue().equals("outline")) {
                        GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);

                        GL11.glDisable(GL11.GL_ALPHA_TEST);
                        GL11.glDisable(GL11.GL_TEXTURE_2D);
                        GL11.glDisable(GL11.GL_LIGHTING);
                        GL11.glEnable(GL11.GL_BLEND);
                        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

                        GlStateManager.color(1, 1, 1, 1);
                        GL11.glLineWidth(ModuleHandler.findModule(ESP.class).outlineWidth.getValue());
                        GL11.glEnable(GL11.GL_LINE_SMOOTH);
                        GL11.glEnable(GL11.GL_STENCIL_TEST);
                        GL11.glClear(GL11.GL_STENCIL_BUFFER_BIT);
                        GL11.glClearStencil(0xF);
                        GL11.glStencilFunc(GL11.GL_NEVER, 1, 0xF);
                        GL11.glStencilOp(GL11.GL_REPLACE, GL11.GL_REPLACE, GL11.GL_REPLACE);
                        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);

                        this.getMainModel().render(entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);
                        GL11.glStencilFunc(GL11.GL_NEVER, 0, 0xF);
                        GL11.glStencilOp(GL11.GL_REPLACE, GL11.GL_REPLACE, GL11.GL_REPLACE);
                        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);

                        GlStateManager.color(1, 1, 1, ModuleHandler.findModule(ESP.class).outlineAlpha.getValue());
                        this.getMainModel().render(entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);

                        GL11.glStencilFunc(GL11.GL_EQUAL, 1, 0xF);
                        GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_KEEP);
                        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
                        this.getMainModel().render(entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);

                        GL11.glDepthMask(false);
                        GL11.glDisable(GL11.GL_DEPTH_TEST);
                        GL11.glEnable(GL11.GL_POLYGON_OFFSET_LINE);
                        GL11.glPolygonOffset(1.0F, -2000000F);
                        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
                        this.getMainModel().render(entitylivingbaseIn, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, scaleFactor);

                        GL11.glPolygonOffset(1.0F, 2000000F);
                        GL11.glDisable(GL11.GL_POLYGON_OFFSET_LINE);
                        GL11.glEnable(GL11.GL_DEPTH_TEST);
                        GL11.glDepthMask(true);
                        GL11.glDisable(GL11.GL_STENCIL_TEST);
                        GL11.glDisable(GL11.GL_LINE_SMOOTH);
                        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_DONT_CARE);
                        GL11.glEnable(GL11.GL_BLEND);
                        GL11.glEnable(GL11.GL_LIGHTING);
                        GL11.glEnable(GL11.GL_TEXTURE_2D);
                        GL11.glEnable(GL11.GL_ALPHA_TEST);

                        GL11.glPopAttrib();
                    }
                }
            }
        }
    }
}
