package coolclk.faker.feature.modules.combat;

import com.google.common.base.Predicate;
import coolclk.faker.event.events.PlayerUpdateEvent;
import coolclk.faker.feature.ModuleHandler;
import coolclk.faker.feature.api.*;
import coolclk.faker.feature.modules.ModuleCategory;
import coolclk.faker.util.ModuleUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.util.vector.Vector2f;

import javax.annotation.Nullable;

@ModuleInfo(name = "AimAssist", category = ModuleCategory.Combat)
public class AimAssist extends Module {
    public SettingsBoolean allowPlayer = new SettingsBoolean(this, "allowAimPlayer", true);
    public SettingsBoolean allowMob = new SettingsBoolean(this, "allowAimMob", false);
    public SettingsDouble range = new SettingsDouble(this, "range", 3.5D, 0D, 6D);
    public SettingsFloat speed = new SettingsFloat(this, "speed", 3F, 0F, 20F);

    @Override
    public void onUpdate(PlayerUpdateEvent event) {
        Entity target = ModuleUtil.findClosestEntityWithDistance(event.getEntityPlayer(), range.getValue(), new Predicate<EntityLivingBase>() {
            @Override
            public boolean apply(@Nullable EntityLivingBase input) {
                return (allowPlayer.getValue() && input instanceof EntityPlayer && ModuleHandler.findModule(AntiBot.class).isBot(input)) || allowMob.getValue();
            }
        });
        if (target != null) {
            Vector2f rotation = ModuleUtil.entityToEntityYawAndPitch(event.getEntityPlayer(), target);
            event.getEntityPlayer().rotationYaw += (rotation.x - event.getEntityPlayer().rotationYaw) * (speed.getValue() / speed.getMaxValue());
            event.getEntityPlayer().rotationPitch += (rotation.y - event.getEntityPlayer().rotationPitch) * (speed.getValue() / speed.getMaxValue());
        }
    }
}
