package coolclk.faker.feature.modules.combat;

import coolclk.faker.feature.ModuleHandler;
import coolclk.faker.feature.api.*;
import coolclk.faker.feature.modules.ModuleCategory;
import coolclk.faker.util.ModuleUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

@ModuleInfo(name = "AimAssist", category = ModuleCategory.Combat)
public class AimAssist extends Module {
    public SettingsBoolean allowPlayer = new SettingsBoolean(this, "allowAimPlayer", true);
    public SettingsBoolean allowMob = new SettingsBoolean(this, "allowAimMob", true);
    public SettingsDouble range = new SettingsDouble(this, "range", 3.5D, 0D, 6D);
    public SettingsFloat speed = new SettingsFloat(this, "speed", 3F, 0F, 20F);

    @Override
    public void onEnabling() {
        Entity target = null;
        List<? extends Entity> entities = ModuleUtil.findEntitiesWithDistance(ModuleUtil.gEP(), range.getValue());
        if (!entities.isEmpty()) {
            for (Entity entity : entities) {
                boolean targeting = false;
                if (entity instanceof EntityPlayer) {
                    if (allowPlayer.getValue()) {
                        if (!ModuleHandler.findModule(AntiBot.class).isBot(entity)) {
                            targeting = true;
                        }
                    }
                } else if (allowMob.getValue()) {
                    targeting = true;
                }
                if (targeting) {
                    if (target == null || ModuleUtil.eTED(ModuleUtil.gEP(), entity) < ModuleUtil.entityToEntityDistance(ModuleUtil.gEP(), target)) {
                        target = entity;
                    }
                }
            }
        }
        if (target != null) {
            float yaw = ModuleUtil.gEP().rotationYaw, pitch = ModuleUtil.gEP().rotationPitch, targetYaw, targetPitch;
            targetYaw = (float) ModuleUtil.getPositionToPositionAngle(ModuleUtil.gEP().posX, ModuleUtil.gEP().posZ, target.posX, target.posZ);
            targetPitch = (float) ModuleUtil.getPositionToPositionAngle(Math.sqrt(Math.pow(ModuleUtil.gEP().posX, 2) + Math.pow(ModuleUtil.gEP().posZ, 2)), ModuleUtil.gEP().posY, Math.sqrt(Math.pow(target.posX, 2) + Math.pow(target.posZ, 2)), target.posY);
            yaw += (targetYaw - yaw) * (speed.getValue() / 20);
            pitch += (targetPitch - pitch) * (speed.getValue() / 20);
            ModuleUtil.gEP().setAngles(yaw, pitch);
        }
    }
}
