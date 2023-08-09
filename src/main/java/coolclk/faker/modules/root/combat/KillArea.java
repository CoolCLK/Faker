package coolclk.faker.modules.root.combat;

import coolclk.faker.modules.Module;
import coolclk.faker.util.ModuleUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KillArea extends Module {
    public static KillArea INSTANCE = new KillArea();

    private final List<Entity> targets = new ArrayList<Entity>();
    private Entity closestTarget = null;
    private long lastAttackTime = 0;

    boolean single, allowPlayer, allowMob;
    double attackDelay, range;

    public KillArea() {
        super("KillArea", Arrays.asList(new ModuleArgument("single", true), new ModuleArgument("aim", true), new ModuleArgument("allowPlayer", true), new ModuleArgument("allowMob", false), new ModuleArgument("range", 3, 3, 6), new ModuleArgument("attackDelay", 5, 0.1, 20)));
    }

    public void onEnable() {
        this.onEnabling();
    }

    public void onEnabling() {
        single = this.getArgument("single").getBooleanValue();
        allowPlayer = this.getArgument("allowPlayer").getBooleanValue();
        allowMob = this.getArgument("allowMob").getBooleanValue();
        attackDelay = this.getArgument("attackDelay").getNumberValueD();
        range = this.getArgument("range").getNumberValueD();

        if (System.currentTimeMillis() >= lastAttackTime + (attackDelay * 50)){
            targets.clear();
            if (ModuleUtil.gEP() != null) {
                List<? extends Entity> entities = ModuleUtil.findEntitiesWithDistance(ModuleUtil.gEP(), range);
                if (!entities.isEmpty()) {
                    for (Entity entity : entities) {
                        boolean targeting = false;
                        if (entity instanceof EntityPlayer) {
                            if (allowPlayer) {
                                if (!AntiBot.INSTANCE.isBot(entity)) {
                                    targeting = true;
                                }
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

            if (ModuleUtil.gPC() != null) {
                for (Entity target : targets) {
                    if (target != null && (!this.getArgument("aim").getBooleanValue() || (ModuleUtil.gM().objectMouseOver != null && ModuleUtil.gM().objectMouseOver.entityHit == target))) {
                        ModuleUtil.gEP().swingItem();
                        ModuleUtil.gPC().attackEntity(ModuleUtil.gEP(), target);
                    }
                }
            }

            lastAttackTime = System.currentTimeMillis();
        }
    }

    public void onDisable() {
        targets.clear();
    }

    public int getDefaultKeyCode() {
        return Keyboard.KEY_R;
    }
}
