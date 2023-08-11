package coolclk.faker.feature.modules.player;

import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.modules.ModuleCategory;
import coolclk.faker.util.ModuleUtil;
import net.minecraft.network.play.client.C03PacketPlayer;

@ModuleInfo(name = "Derp", category = ModuleCategory.Player)
public class Derp extends Module {
    @Override
    public void onEnabling() {
        float yaw = ModuleUtil.gEP().rotationYawHead, motionYaw;
        motionYaw = (float) (Math.random() * 360 - yaw);
        yaw += motionYaw;

        ModuleUtil.gEP().rotationYawHead = yaw;
        ModuleUtil.gEP().renderYawOffset = motionYaw;
        ModuleUtil.gNM().sendPacket(new C03PacketPlayer.C05PacketPlayerLook(yaw, ModuleUtil.gEP().rotationPitch, ModuleUtil.gEP().onGround)); // Forced to send packet
    }

    @Override
    public void onDisable() {
        ModuleUtil.gEP().renderYawOffset = 0;
    }
}
