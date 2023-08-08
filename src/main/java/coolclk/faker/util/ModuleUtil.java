package coolclk.faker.util;

import com.google.common.base.Predicate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ModuleUtil {
    public static Minecraft getMinecraft() {
        return Minecraft.getMinecraft();
    }

    public static EntityPlayerSP getEntityPlayer() {
        return getMinecraft().thePlayer;
    }

    public static PlayerControllerMP getPlayerController() {
        return getMinecraft().playerController;
    }

    public static WorldClient getWorld() {
        return getMinecraft().theWorld;
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

    public static Object getInaccessibleVariable(Class<?> anyClass, String variableName) throws NoSuchFieldException, IllegalAccessException {
        Field field = anyClass.getDeclaredField(variableName);
        field.setAccessible(true);
        return field.get(anyClass);
    }

    public static Object getInaccessibleVariable(Object object, String variableName) throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(variableName);
        field.setAccessible(true);
        return field.get(object);
    }

    public static float getInaccessibleVariableF(Class<?> anyClass, String variableName) throws NoSuchFieldException, IllegalAccessException {
        Field field = anyClass.getDeclaredField(variableName);
        field.setAccessible(true);
        return field.getFloat(anyClass);
    }

    public static float getInaccessibleVariableF(Object object, String variableName) throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(variableName);
        field.setAccessible(true);
        return field.getFloat(object);
    }

    public static void setInaccessibleVariable(Class<?> anyClass, String variableName, Object variableNewValue) throws NoSuchFieldException, IllegalAccessException {
        Field field = anyClass.getDeclaredField(variableName);
        field.setAccessible(true);
        field.set(anyClass, variableNewValue);
    }

    public static void setInaccessibleVariable(Object object, String variableName, Object variableNewValue) throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(variableName);
        field.setAccessible(true);
        field.set(object, variableNewValue);
    }

    // Simplified Function

    public static Minecraft gM() {
        return getMinecraft();
    }

    public static EntityPlayerSP gEP() {
        return getEntityPlayer();
    }

    public static PlayerControllerMP gPC() {
        return getPlayerController();
    }

    public static WorldClient gW() {
        return getWorld();
    }

    public static double eTED(Entity a, Entity b) {
        return entityToEntityDistance(a, b);
    }
}