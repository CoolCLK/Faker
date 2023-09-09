package coolclk.faker.feature.modules.combat;

import coolclk.faker.event.api.SubscribeEvent;
import coolclk.faker.event.events.PlayerAttackEntityEvent;
import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.api.SettingsDouble;
import coolclk.faker.feature.api.SettingsModeString;
import coolclk.faker.feature.modules.ModuleCategory;
import coolclk.faker.util.ModuleUtil;
import net.minecraft.network.play.client.C03PacketPlayer;

@ModuleInfo(name = "Criticals", category = ModuleCategory.Combat)
public class Criticals extends Module {
    public SettingsModeString mode = new SettingsModeString(this, "jump", "jump", "packet", "custom");
    public SettingsDouble jumpHeight = new SettingsDouble(this, "jumpHeight", 0.42, 0.01, 0.5);

    @Override
    public void onClickGuiUpdate() {
        jumpHeight.setDisplayVisible(mode.getValue().equals("custom"));
    }

    @SubscribeEvent
    public void onAttackEntity(PlayerAttackEntityEvent event) {
        if (this.getEnable()) {
            if (event.getEntityPlayer().onGround) {
                event.getEntityPlayer().onGround = false;
                if (mode.getValue().equals("jump")) {
                    event.getEntityPlayer().jump();
                } else if (mode.getValue().equals("custom")) {
                    event.getEntityPlayer().motionY = jumpHeight.getValue();
                } else if (mode.getValue().equals("packet")) {
                    for (double height : new double[] { 0.01, 0.0625, 0.0375, 0 }) {
                        ModuleUtil.gNM().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(event.getEntityPlayer().posX, event.getEntityPlayer().posY + height, event.getEntityPlayer().posZ, false));
                    }
                }
            }
        }
    }
}
