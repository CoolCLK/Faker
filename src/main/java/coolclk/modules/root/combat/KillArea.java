package coolclk.modules.root.combat;

import com.google.common.base.Predicate;
import coolclk.modules.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import org.lwjgl.input.Keyboard;
import scala.actors.threadpool.Arrays;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class KillArea extends Module {
    private Timer timer;
    private TimerTask attackTask;
    private TimerTask targetTask;
    private List<Entity> targets = new ArrayList<Entity>();

    boolean allowPlayer, allowMob;
    double attackDelay;
    double targetDelay;
    double range;

    public KillArea() {
        super("KillArea", Arrays.asList(new ModuleArgument[] { new ModuleArgument("allowPlayer", true), new ModuleArgument("allowMob", false), new ModuleArgument("range", 3, 3, 6), new ModuleArgument("attackDelay", 5, 0.1, 20), new ModuleArgument("updateTargetDelay", 10, 0.1, 40) }));
    }

    public void onEnable() {
        this.onEnabling();
        timer = new Timer();
        timer.schedule((attackTask = new TimerTask() {
            @Override
            public void run() {
                if (getPlayerController() != null) {
                    for (Entity target : targets) {
                        if (target != null) {
                            getEntityPlayer().swingItem();
                            getPlayerController().attackEntity(getEntityPlayer(), target);
                        }
                    }
                }
            }
        }), 0, Math.round(attackDelay * 50));
        timer.schedule((targetTask = new TimerTask() {
            @Override
            public void run() {
                targets.clear();
                if (getEntityPlayer() != null) {
                    List<? extends Entity> entities = findTargets(getEntityPlayer(), range);
                    for (Entity entity : entities) {
                        if (entity instanceof EntityPlayer) {
                            if (allowPlayer) {
                                targets.add(entity);
                            }
                        } else if (allowMob) {
                            targets.add(entity);
                        }
                    }
                }
            }
        }), 0, Math.round(targetDelay * 50));
    }

    public void onEnabling() {
        allowPlayer = this.getArgument("allowPlayer").getBooleanValue();
        allowMob = this.getArgument("allowMob").getBooleanValue();
        attackDelay = this.getArgument("attackDelay").getNumberValue();
        targetDelay = this.getArgument("updateTargetDelay").getNumberValue();
        range = this.getArgument("range").getNumberValue();
    }

    public void onDisable() {
        attackTask.cancel();
        targetTask.cancel();
        timer.cancel();
        targets.clear();
    }

    public int getDefaultKeyCode() {
        return Keyboard.KEY_R;
    }

    public List<Entity> findTargets(EntityPlayer player, double range) {
        List<Entity> targets = new ArrayList<Entity>();
        List<Entity> checkTargets = new ArrayList<Entity>(player.getEntityWorld().getEntities(EntityLivingBase.class, new Predicate<EntityLivingBase>() {
            @Override
            public boolean apply(@Nullable EntityLivingBase input) {
                return input != null;
            }
        }));

        for (Entity entity : checkTargets) {
            double distance = Math.sqrt((Math.pow(entity.getPosition().getX() - player.getPosition().getX(), 2) + Math.pow(entity.getPosition().getZ() - player.getPosition().getZ(), 2)) + Math.pow(entity.getPosition().getY() - player.getPosition().getY(), 2));
            if (entity != player && distance < range) {
                targets.add(entity);
            }
        }

        return targets;
    }
}
