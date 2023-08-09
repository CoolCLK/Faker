package coolclk.faker.modules.root.combat;

import coolclk.faker.modules.Module;
import coolclk.faker.util.MathHelper;
import coolclk.faker.util.ModuleUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import java.util.Arrays;

public class AimAssist extends Module {
    public static AimAssist INSTANCE = new AimAssist();

    public AimAssist() {
        super("AimAssist", Arrays.asList(new ModuleArgument("range", 3, 1, 4), new ModuleArgument("assistSpeed", 3, 0, 20)));
    }

    @Override
    public void onEnabling() {
        Entity target = ModuleUtil.findClosestEntity(ModuleUtil.gEP());
        if (target != null) {
            float yaw = ModuleUtil.gEP().rotationYaw, pitch = ModuleUtil.gEP().rotationPitch;
            float[] targetRotations = getRotations(ModuleUtil.gEP(), target);
            yaw += (targetRotations[0] - yaw) * (getArgument("assistSpeed").getNumberValueF() / 20);
            pitch += (targetRotations[1] - pitch) * (getArgument("assistSpeed").getNumberValueF() / 20);
            ModuleUtil.gEP().rotationYaw = yaw;
            ModuleUtil.gEP().rotationPitch = pitch;
        }
    }

    private float[] getRotations(EntityPlayer player, Entity target) {
        final double var4 = (target.posX - (target.lastTickPosX - target.posX)) + 0.01 - player.posX;
        final double var6 = (target.posZ - (target.lastTickPosZ - target.posZ)) - player.posZ;
        final double var8 = (target.posY - (target.lastTickPosY - target.posY)) + 0.4 + target.getEyeHeight() / 1.3 - (player.posY + player.getEyeHeight());

        final double var14 = MathHelper.sqrt_double(var4 * var4 + var6 * var6);

        float yaw = (float) (Math.atan2(var6, var4) * 180.0D / Math.PI) - 90.0F;
        float pitch = (float) -(Math.atan2(var8, var14) * 180.0D / Math.PI);

        yaw = player.rotationYaw + MathHelper.wrapAngleTo180_float(yaw - player.rotationYaw);
        pitch = player.rotationPitch + MathHelper.wrapAngleTo180_float(pitch - player.rotationPitch);

        pitch = MathHelper.clamp_float(pitch, -90.0F, 90.0F);

        return new float[] { yaw, pitch };
    }
}
