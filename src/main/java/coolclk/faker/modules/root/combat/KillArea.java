package coolclk.faker.modules.root.combat;

import coolclk.faker.modules.Module;
import coolclk.faker.modules.ModuleUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.input.Keyboard;

import java.util.*;

public class KillArea extends Module {
    public static KillArea INSTANCE = new KillArea();

    private Timer timer;
    private TimerTask attackTask;
    private final List<Entity> targets = new ArrayList<Entity>();
    private Entity closestTarget = null;

    boolean single, allowPlayer, allowMob;
    double attackDelay, range;

    public KillArea() {
        super("KillArea", Arrays.asList(new ModuleArgument("single", false), new ModuleArgument("allowPlayer", true), new ModuleArgument("allowMob", false), new ModuleArgument("range", 3, 3, 6), new ModuleArgument("attackDelay", 5, 0.1, 20)));
    }

    public void onEnable() {
        this.onEnabling();
        timer = new Timer();
        timer.schedule((attackTask = new TimerTask() {
            @Override
            public void run() {
                if (ModuleUtil.gPC() != null) {
                    for (Entity target : targets) {
                        if (target != null) {
                            ModuleUtil.gEP().swingItem();
                            ModuleUtil.gPC().attackEntity(ModuleUtil.gEP(), target);
                        }
                    }
                }
            }
        }), 0, Math.round(attackDelay * 50));
    }

    public void onEnabling() {
        single = this.getArgument("single").getBooleanValue();
        allowPlayer = this.getArgument("allowPlayer").getBooleanValue();
        allowMob = this.getArgument("allowMob").getBooleanValue();
        attackDelay = this.getArgument("attackDelay").getNumberValueD();
        range = this.getArgument("range").getNumberValueD();

        targets.clear();
        if (ModuleUtil.gEP() != null) {
            List<? extends Entity> entities = ModuleUtil.findEntitiesWithDistance(ModuleUtil.gEP(), range);
            if (!entities.isEmpty()) {
                for (Entity entity : entities) {
                    boolean targeting = false;
                    if (entity instanceof EntityPlayer) {
                        if (allowPlayer) {
                            targeting = true;
                        }
                    } else if (allowMob) {
                        targeting = true;
                    }
                    if (targeting) {
                        targets.add(entity);
                        if (closestTarget == null || ModuleUtil.eTED(ModuleUtil.gEP(), entity) < ModuleUtil.entityToEntityDistance(ModuleUtil.gEP(), closestTarget)) {
                            closestTarget = entity;
                        }
                    }
                }
                if (single && closestTarget != null) {
                    targets.clear();
                    targets.add(closestTarget);
                }
            }
        }
    }

    public void onDisable() {
        attackTask.cancel();
        timer.cancel();
        targets.clear();
    }

    public int getDefaultKeyCode() {
        return Keyboard.KEY_R;
    }
}
