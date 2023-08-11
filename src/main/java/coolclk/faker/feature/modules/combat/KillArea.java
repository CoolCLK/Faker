package coolclk.faker.feature.modules.combat;

import coolclk.faker.feature.ModuleHandler;
import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.api.SettingsBoolean;
import coolclk.faker.feature.api.SettingsDouble;
import coolclk.faker.feature.modules.ModuleCategory;
import coolclk.faker.util.ModuleUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

@ModuleInfo(name = "KillArea", category = ModuleCategory.Combat, defaultKeycode = Keyboard.KEY_R)
public class KillArea extends Module {
    private final List<Entity> targets = new ArrayList<Entity>();
    private Entity closestTarget = null;
    private long lastAttackTime = 0;

    public SettingsBoolean onlyAiming = new SettingsBoolean(this, "onlyAiming", true);
    public SettingsBoolean onlySingle = new SettingsBoolean(this, "onlySingle", true);
    public SettingsBoolean allowPlayer = new SettingsBoolean(this, "allowAttackPlayer", true);
    public SettingsBoolean allowMob = new SettingsBoolean(this, "allowAttackMob", true);
    public SettingsDouble attackDelay = new SettingsDouble(this, "attackDelay", 3D, 3D, 8D);
    public SettingsDouble range = new SettingsDouble(this, "range", 5D, 0D, 20D);

    @Override
    public void onEnabling() {
        if (System.currentTimeMillis() >= lastAttackTime + (attackDelay.getValue() * 50)){
            targets.clear();
            if (ModuleUtil.gEP() != null) {
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
                            targets.add(entity);
                            if (closestTarget == null || ModuleUtil.eTED(ModuleUtil.gEP(), entity) < ModuleUtil.entityToEntityDistance(ModuleUtil.gEP(), closestTarget)) {
                                closestTarget = entity;
                            }
                        }
                    }
                    if (onlySingle.getValue() && closestTarget != null) {
                        targets.clear();
                        targets.add(closestTarget);
                    }
                }
            }

            if (ModuleUtil.gPC() != null) {
                for (Entity target : targets) {
                    if (target != null && (!onlyAiming.getValue() || (ModuleUtil.gM().objectMouseOver != null && ModuleUtil.gM().objectMouseOver.entityHit == target))) {
                        ModuleUtil.gEP().swingItem();
                        ModuleUtil.gPC().attackEntity(ModuleUtil.gEP(), target);
                    }
                }
            }

            lastAttackTime = System.currentTimeMillis();
        }
    }
}
