package coolclk.faker.feature.modules.combat;

import coolclk.faker.event.api.SubscribeEvent;
import coolclk.faker.event.events.PacketEvent;
import coolclk.faker.event.events.PlayerUpdateEvent;
import coolclk.faker.event.events.UpdateTimerEvent;
import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.api.SettingsFloat;
import coolclk.faker.feature.api.SettingsModeString;
import coolclk.faker.feature.modules.ModuleCategory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

@ModuleInfo(name = "Velocity", category = ModuleCategory.Combat)
public class Velocity extends Module {
    public SettingsModeString mode = new SettingsModeString(this, "custom", "cancelPacket", "custom");
    public SettingsFloat counteractPercent = new SettingsFloat(this, "counteractPercent", 0.1F, 0F, 1F) {
        @Override
        public String getDisplayValue() {
            return (super.getValuePercent() * 100) + "%";
        }
    };
    private EntityPlayer entityPlayer;

    @Override
    public void onClickGuiUpdate() {
        counteractPercent.setDisplayVisible(mode.getValue().equals("custom"));
    }

    @Override
    public void onUpdate(PlayerUpdateEvent event) {
        entityPlayer = event.getEntityPlayer();
        if (event.getEntityPlayer().hurtTime > 0) {
            if (mode.getValue().equals("custom")) {
                float per = 1 - counteractPercent.getValue();
                event.getEntityPlayer().motionX *= per;
                event.getEntityPlayer().motionY *= per;
                event.getEntityPlayer().motionZ *= per;
            }
        }
    }

    @SubscribeEvent
    public void onReceivePacket(PacketEvent event) {
        if (this.getEnable() && entityPlayer != null && event.getType() == PacketEvent.Type.RECEIVE) {
            if (mode.getValue().equals("cancelPacket") && event.isTypeOf(S08PacketPlayerPosLook.class)) {
                event.setPacket(new S08PacketPlayerPosLook(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ, ((S08PacketPlayerPosLook) event.getPacket()).getYaw(), ((S08PacketPlayerPosLook) event.getPacket()).getPitch(), ((S08PacketPlayerPosLook) event.getPacket()).func_179834_f()));
            }
        }
    }
}
