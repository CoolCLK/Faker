package coolclk.faker.feature.modules.player;

import coolclk.faker.event.events.PlayerUpdateEvent;
import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.api.SettingsLong;
import coolclk.faker.feature.modules.ModuleCategory;
import coolclk.faker.util.ModuleUtil;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.C03PacketPlayer;

@ModuleInfo(name = "Derp", category = ModuleCategory.Player)
public class Derp extends Module {
    private long lastTime = 0;
    public SettingsLong delay = new SettingsLong(this, "delay", 50L, 0L, 100L) {
        @Override
        public String getDisplayValue() {
            return super.getDisplayValue() + "ms";
        }
    };
    private EntityPlayerSP entityPlayer;

    @Override
    public void onUpdate(PlayerUpdateEvent event) {
        entityPlayer = event.getEntityPlayer();
        if (System.currentTimeMillis() >= lastTime + delay.getValue()) {
            float yaw = event.getEntityPlayer().rotationYawHead, motionYaw;
            motionYaw = (float) (Math.random() * 360 - yaw);
            yaw += motionYaw;

            event.getEntityPlayer().rotationYawHead = yaw;
            event.getEntityPlayer().renderYawOffset = motionYaw;
            event.getNetworkManager().sendPacket(new C03PacketPlayer.C05PacketPlayerLook(yaw, event.getEntityPlayer().rotationPitch, event.getEntityPlayer().onGround));

            lastTime = System.currentTimeMillis();
        }
    }

    @Override
    public void onDisable() {
        entityPlayer.renderYawOffset = 0;
    }
}
