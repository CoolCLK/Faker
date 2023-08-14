package coolclk.faker.injection.mixins;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import coolclk.faker.feature.ModuleHandler;
import coolclk.faker.feature.modules.player.Reach;
import coolclk.faker.feature.modules.render.HurtCam;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(EntityRenderer.class)
public abstract class MixinEntityRenderer {
    @Shadow private Entity pointedEntity;

    @Shadow private Minecraft mc;

    @Inject(method = "hurtCameraEffect", at = @At(value = "HEAD"), cancellable = true)
    private void hurtCameraEffect(float partialTicks, CallbackInfo ci) {
        if (ModuleHandler.findModule(HurtCam.class).getEnable()) {
            Minecraft mc = Minecraft.getMinecraft();
            if (mc.getRenderViewEntity() instanceof EntityLivingBase) {
                float percent = ModuleHandler.findModule(HurtCam.class).shakePercent.getValue();

                EntityLivingBase entitylivingbase = (EntityLivingBase) mc.getRenderViewEntity();
                float f = (float) entitylivingbase.hurtTime - partialTicks;
                f *= percent;

                if (entitylivingbase.getHealth() <= 0.0F) {
                    float f1 = (float) entitylivingbase.deathTime + partialTicks;
                    GlStateManager.rotate(40.0F - 8000.0F / (f1 + 200.0F), 0.0F, 0.0F, 1.0F);
                }

                if (f < 0.0F) {
                    return;
                }

                f = f / (float) entitylivingbase.maxHurtTime;
                f = MathHelper.sin(f * f * f * f * (float) Math.PI);
                float f2 = entitylivingbase.attackedAtYaw;
                f2 *= percent;
                GlStateManager.rotate(-f2, 0.0F, 1.0F, 0.0F);
                GlStateManager.rotate(-f * 14.0F, 0.0F, 0.0F, 1.0F);
                GlStateManager.rotate(f2, 0.0F, 1.0F, 0.0F);
            }
            ci.cancel();
        }
    }

    /**
     * @author CoolCLK
     * @reason Change view
     */
    @Overwrite
    public void getMouseOver(float partialTicks) {
        Minecraft mc = Minecraft.getMinecraft();
        Entity entity = mc.getRenderViewEntity();

        if (entity != null) {
            if (mc.theWorld != null) {
                mc.mcProfiler.startSection("pick");
                mc.pointedEntity = null;
                double blockReachDistance = this.mc.playerController.getBlockReachDistance();
                mc.objectMouseOver = entity.rayTrace(blockReachDistance, partialTicks);
                double entityReachDistance = blockReachDistance;
                Vec3 eyesPosition = entity.getPositionEyes(partialTicks);
                boolean flag = false;

                if (ModuleHandler.findModule(Reach.class).getEnable()) {
                    blockReachDistance = ModuleHandler.findModule(Reach.class).distance.getValue();
                    entityReachDistance = blockReachDistance;
                }
                else if (this.mc.playerController.extendedReach()) {
                    blockReachDistance = 6.0D;
                    entityReachDistance = 6.0D;
                }
                else {
                    if (blockReachDistance > 3.0D) {
                        flag = true;
                    }
                }

                if (mc.objectMouseOver != null) {
                    entityReachDistance = mc.objectMouseOver.hitVec.distanceTo(eyesPosition);
                }

                Vec3 targetEyesPosition = entity.getLook(partialTicks);
                this.pointedEntity = null;
                Vec3 reachPosition = null;
                float boxSize = 1.0F;
                List<Entity> list = mc.theWorld.getEntitiesInAABBexcluding(entity, entity.getEntityBoundingBox().addCoord(targetEyesPosition.xCoord * blockReachDistance, targetEyesPosition.yCoord * blockReachDistance, targetEyesPosition.zCoord * blockReachDistance).expand(boxSize, boxSize, boxSize), Predicates.and(EntitySelectors.NOT_SPECTATING, new Predicate<Entity>() {
                    public boolean apply(Entity p_apply_1_) {
                        return p_apply_1_.canBeCollidedWith();
                    }
                }));
                double d2 = entityReachDistance;

                for (Entity value : list) {
                    float f1 = value.getCollisionBorderSize();
                    AxisAlignedBB axisalignedbb = value.getEntityBoundingBox().expand(f1, f1, f1);
                    MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(eyesPosition, eyesPosition.addVector(targetEyesPosition.xCoord * blockReachDistance, targetEyesPosition.yCoord * blockReachDistance, targetEyesPosition.zCoord * blockReachDistance));

                    if (axisalignedbb.isVecInside(eyesPosition)) {
                        if (d2 >= 0.0D) {
                            this.pointedEntity = value;
                            reachPosition = movingobjectposition == null ? eyesPosition : movingobjectposition.hitVec;
                            d2 = 0.0D;
                        }
                    } else if (movingobjectposition != null) {
                        double d3 = eyesPosition.distanceTo(movingobjectposition.hitVec);

                        if (d3 < d2 || d2 == 0.0D) {
                            if (value == entity.ridingEntity && !entity.canRiderInteract()) {
                                if (d2 == 0.0D) {
                                    this.pointedEntity = value;
                                    reachPosition = movingobjectposition.hitVec;
                                }
                            } else {
                                this.pointedEntity = value;
                                reachPosition = movingobjectposition.hitVec;
                                d2 = d3;
                            }
                        }
                    }
                }

                if (this.pointedEntity != null && flag && eyesPosition.distanceTo(reachPosition) > entityReachDistance) {
                    this.pointedEntity = null;
                    if (reachPosition != null) {
                        mc.objectMouseOver = new MovingObjectPosition(MovingObjectPosition.MovingObjectType.MISS, reachPosition, null, new BlockPos(reachPosition));
                    }
                }

                if (this.pointedEntity != null && (d2 < entityReachDistance || mc.objectMouseOver == null)) {
                    mc.objectMouseOver = new MovingObjectPosition(this.pointedEntity, reachPosition);

                    if (this.pointedEntity instanceof EntityLivingBase || this.pointedEntity instanceof EntityItemFrame) {
                        mc.pointedEntity = this.pointedEntity;
                    }
                }

                mc.mcProfiler.endSection();
            }
        }
    }
}
