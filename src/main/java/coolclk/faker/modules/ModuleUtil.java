package coolclk.faker.modules;

import com.google.common.base.Predicate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ModuleUtil {
    public static EntityPlayerSP getEntityPlayer() {
        return Minecraft.getMinecraft().thePlayer;
    }

    public static PlayerControllerMP getPlayerController() {
        return Minecraft.getMinecraft().playerController;
    }

    public static List<EntityLivingBase> findEntitiesWithDistance(EntityPlayer player, double range) {
        List<EntityLivingBase> targets = new ArrayList<EntityLivingBase>();
        List<EntityLivingBase> checkTargets = new ArrayList<EntityLivingBase>(player.getEntityWorld().getEntities(EntityLivingBase.class, new Predicate<EntityLivingBase>() {
            @Override
            public boolean apply(@Nullable EntityLivingBase input) {
                return input != null;
            }
        }));

        for (EntityLivingBase entity : checkTargets) {
            double distance = entityToEntityDistance(player, entity);
            if (entity != player && distance < range) {
                targets.add(entity);
            }
        }
        return targets;
    }

    public static EntityLivingBase findClosestEntity(Entity entity, int range) {
        EntityLivingBase target = null;
        List<EntityLivingBase> entities = new ArrayList<EntityLivingBase>(entity.getEntityWorld().getEntities(EntityLivingBase.class, new Predicate<EntityLivingBase>() {
            @Override
            public boolean apply(@Nullable EntityLivingBase input) {
                return input != null;
            }
        }));
        for (EntityLivingBase checkEntity : entities) {
            if ((range < 0 || entityToEntityDistance(entity, checkEntity) <= range) && (target == null || entityToEntityDistance(entity, checkEntity) < entityToEntityDistance(entity, target))) {
                target = checkEntity;
            }
        }
        return target;
    }

    public static EntityLivingBase findClosestEntity(Entity entity) {
        return findClosestEntity(entity, -1);
    }

    public static double entityToEntityDistance(Entity a, Entity b) {
        return positionToPositionDistance(a.getPosition().getX(), a.getPosition().getY(), a.getPosition().getZ(), b.getPosition().getX(), b.getPosition().getY(), b.getPosition().getZ());
    }

    public static double positionToPositionDistance2d(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    public static double positionToPositionDistance(double x1, double y1, double z1, double x2, double y2, double z2) {
        return Math.sqrt(Math.pow(positionToPositionDistance2d(x1, y1, x2, y2), 2) + Math.pow(z1 - z2, 2));
    }

    public static double blockPosToBlockPosDistance(BlockPos a, BlockPos b) {
        return positionToPositionDistance(a.getX(), a.getY(), a.getZ(), b.getX(), b.getY(), b.getZ());
    }

    // Simplified Function

    public static EntityPlayerSP gEP() {
        return getEntityPlayer();
    }

    public static PlayerControllerMP gPC() {
        return getPlayerController();
    }

    public static double eTED(Entity a, Entity b) {
        return entityToEntityDistance(a, b);
    }

    public static double pTPD2d(double x1, double y1, double x2, double y2) {
        return positionToPositionDistance2d(x1, y1, x2, y2);
    }

    public static double pTPD(double x1, double y1, double z1, double x2, double y2, double z2) {
        return positionToPositionDistance(x1, y1, z1, x2, y2, z2);
    }

    public static double bPTBPD(BlockPos a, BlockPos b) {
        return blockPosToBlockPosDistance(a, b);
    }
}
