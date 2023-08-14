package coolclk.faker.util;

import com.google.common.base.Predicate;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.NetworkManager;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import org.lwjgl.util.vector.Vector2f;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ModuleUtil {
    public static boolean inRange(Double number, Double min, Double max) {
        double minNum = Math.min(min, max), maxNum = Math.max(min, max);
        return number >= minNum && number <= maxNum;
    }

    public static boolean inRange(Integer number, Integer min, Integer max) {
        return inRange(number.doubleValue(), min.doubleValue(), max.doubleValue());
    }

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

    public static NetHandlerPlayClient getNetworkHandler() {
        return getMinecraft().getNetHandler();
    }

    public static NetworkManager getNetworkManager() {
        return getNetworkHandler().getNetworkManager();
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
            if (entity != player && (distance < 0 || distance < range)) {
                targets.add(entity);
            }
        }
        return targets;
    }

    public static EntityLivingBase findClosestEntityWithDistance(EntityPlayer player, double range, Predicate<EntityLivingBase> filter) {
        EntityLivingBase target = null;
        for (EntityLivingBase entityLivingBase : findEntitiesWithDistance(player, range)) {
            if (target == null || (filter.apply(entityLivingBase) && entityToEntityDistance(entityLivingBase, player) < entityToEntityDistance(target, player))) {
                target = entityLivingBase;
            }
        }
        return target;
    }

    public static List<BlockPos> findBlockPosWithDistance(World world, BlockPos position, Block block, double distance) {
        List<BlockPos> target = new ArrayList<BlockPos>();
        for (double nowX = -distance; nowX < distance; nowX++) {
            for (double nowZ = -distance; nowZ < distance; nowZ++) {
                for (double nowY = -distance; nowY < distance; nowY++) {
                    BlockPos blockPos = position.add(nowX, nowY, nowZ);
                    double nowDistance = ModuleUtil.blockPosToBlockPosDistance(position, blockPos);
                    if (nowDistance <= distance && world.getBlockState(blockPos).getBlock() == block) {
                        target.add((target.isEmpty() || ModuleUtil.blockPosToBlockPosDistance(position, target.get(0)) > nowDistance) ? 0 : target.size() - 1, blockPos);
                    }
                }
            }
        }
        return target;
    }

    public static double getPositionToPositionAngle(double x1, double y1, double x2, double y2) {
        return -Math.toDegrees(Math.atan((x2 - x1) / (y2 - y1)));
    }

    public static double entityToEntityDistance(Entity a, Entity b) {
        return positionToPositionDistance(a.getPosition().getX(), a.getPosition().getY(), a.getPosition().getZ(), b.getPosition().getX(), b.getPosition().getY(), b.getPosition().getZ());
    }

    public static double entityToEntityXZDistance(Entity a, Entity b) {
        return positionToPositionDistance2d(a.getPosition().getX(), a.getPosition().getY(), b.getPosition().getX(), b.getPosition().getY());
    }

    public static double positionToPositionDistance2d(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    public static double blockPosToBlockPosDistance(BlockPos a, BlockPos b) {
        return positionToPositionDistance(a.getX(), a.getY(), a.getZ(), b.getX(), b.getY(), b.getZ());
    }

    public static double positionToPositionDistance(double x1, double y1, double z1, double x2, double y2, double z2) {
        return Math.sqrt(Math.pow(positionToPositionDistance2d(x1, y1, x2, y2), 2) + Math.pow(z1 - z2, 2));
    }

    public static Vector2f positionToPositionYawAndPitch(double fromX, double fromY, double fromZ, double toX, double toY, double toZ) {
        float pitch = (float) ModuleUtil.getPositionToPositionAngle(0, fromY, ModuleUtil.positionToPositionDistance(fromX, fromY, fromZ, toX, toY, toZ), toY), yaw = (float) ModuleUtil.getPositionToPositionAngle(fromX, fromZ, toX, toZ);
        if (toZ < ModuleUtil.gEP().posZ) {
            if (toX >= ModuleUtil.gEP().posX) yaw -= 180;
            if (toX < ModuleUtil.gEP().posX) yaw += 180;
            yaw %= 180;
        }
        pitch %= 90;
        return new Vector2f(pitch, yaw);
    }

    public static Vector2f entityToEntityYawAndPitch(Entity from, Entity to) {
        return positionToPositionYawAndPitch(from.posX, from.posY, from.posZ, to.posX, to.posY, to.posZ);
    }

    public static Object getInaccessibleVariable(Class<?> clazz, String variableName) throws NoSuchFieldException, IllegalAccessException {
        Field field = clazz.getDeclaredField(variableName);
        field.setAccessible(true);
        return field.get(clazz);
    }

    public static Object getInaccessibleVariable(Object object, String variableName) throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(variableName);
        field.setAccessible(true);
        return field.get(object);
    }

    public static float getInaccessibleVariableF(Class<?> clazz, String variableName) throws NoSuchFieldException, IllegalAccessException {
        Field field = clazz.getDeclaredField(variableName);
        field.setAccessible(true);
        return field.getFloat(clazz);
    }

    public static float getInaccessibleVariableF(Object object, String variableName) throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(variableName);
        field.setAccessible(true);
        return field.getFloat(object);
    }

    public static void setInaccessibleVariable(Class<?> clazz, String variableName, Object variableNewValue) throws NoSuchFieldException, IllegalAccessException {
        Field field = clazz.getDeclaredField(variableName);
        field.setAccessible(true);
        field.set(clazz, variableNewValue);
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

    public static NetHandlerPlayClient gNH() {
        return getNetworkHandler();
    }

    public static NetworkManager gNM() {
        return getNetworkManager();
    }

    public static double eTED(Entity a, Entity b) {
        return entityToEntityDistance(a, b);
    }
}
