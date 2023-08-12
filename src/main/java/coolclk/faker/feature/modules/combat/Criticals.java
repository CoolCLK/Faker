package coolclk.faker.feature.modules.combat;

import coolclk.faker.feature.api.*;
import coolclk.faker.feature.modules.ModuleCategory;
import coolclk.faker.util.ModuleUtil;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@ModuleInfo(name = "Criticals", category = ModuleCategory.Combat)
public class Criticals extends Module {
    public SettingsModeString mode = new SettingsModeString(this, "jump", "jump", "packet", "custom");
    public SettingsDouble jumpHeight = new SettingsDouble(this, "jumpHeight", 0.42, 0.01, 0.5);

    @Override
    public void onClickGuiUpdate() {
        jumpHeight.setDisplayVisible(mode.getValue().equals("custom"));
    }

    @SubscribeEvent
    public void onAttackEntity(AttackEntityEvent event) {
        if (this.getEnable()) {
            if (event.entityPlayer == ModuleUtil.gEP()) {
                if (event.entityPlayer.onGround) {
                    event.entityPlayer.onGround = false;
                    if (mode.getValue().equals("jump")) {
                        event.entityPlayer.jump();
                    } else if (mode.getValue().equals("custom")) {
                        event.entityPlayer.motionY = jumpHeight.getValue();
                    } else if (mode.getValue().equals("packet")) {
                        event.entityPlayer.posY += 0.01D;
                        ModuleUtil.gNM().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(event.entityPlayer.posX, event.entityPlayer.posY + 0.1, event.entityPlayer.posZ, false));
                    }
                }
            }
        }
    }
}
