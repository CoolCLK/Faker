package coolclk.faker.modules.root.render;

import coolclk.faker.event.network.PacketSendEvent;
import coolclk.faker.modules.Module;
import coolclk.faker.util.ModuleUtil;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Collections;

public class FreeCam extends Module {
    public static FreeCam INSTANCE = new FreeCam();

    private EntityOtherPlayerMP fakePlayer;
    private float oldRotationPitch, oldRotationYaw;
    private double oldPosX, oldPosY, oldPosZ;

    public FreeCam() {
        super("FreeCam", Collections.singletonList(new ModuleArgument("flySpeed", 0.5, 0.1, 10)));
        if (this.getEnable()) {
            this.toggleModule();
        }
    }

    @Override
    public void onEnable() {
        fakePlayer = new EntityOtherPlayerMP(ModuleUtil.gW(), ModuleUtil.gEP().getGameProfile());
        fakePlayer.setPositionAndRotation(ModuleUtil.gEP().posX, ModuleUtil.gEP().posY, ModuleUtil.gEP().posZ, ModuleUtil.gEP().rotationYaw, ModuleUtil.gEP().rotationPitch);
        fakePlayer.rotationYawHead = ModuleUtil.gEP().rotationYawHead;

        ModuleUtil.gW().addEntityToWorld(fakePlayer.getEntityId(), fakePlayer);

        oldRotationPitch = ModuleUtil.gEP().rotationPitch;
        oldRotationYaw = ModuleUtil.gEP().rotationYaw;
        oldPosX = ModuleUtil.gEP().posX;
        oldPosY = ModuleUtil.gEP().posY;
        oldPosZ = ModuleUtil.gEP().posZ;
    }

    @Override
    public void onEnabling() {
        ModuleUtil.gEP().noClip = true;
        ModuleUtil.gEP().motionY = (ModuleUtil.gM().gameSettings.keyBindJump.isKeyDown() ? this.getArgument("flySpeed").getNumberValueD() : 0) + (ModuleUtil.gM().gameSettings.keyBindSneak.isKeyDown() ? -this.getArgument("flySpeed").getNumberValueD() : 0);
    }

    @Override
    public void onDisable() {
        if (fakePlayer != null) {
            ModuleUtil.gW().removeEntityFromWorld(fakePlayer.getEntityId());
            ModuleUtil.gEP().setPositionAndRotation(oldPosX, oldPosY, oldPosZ, oldRotationYaw, oldRotationPitch);
        }
        ModuleUtil.gEP().noClip = false;
        ModuleUtil.gEP().motionY = 0;
    }

    @SubscribeEvent
    public void onPacketSend(PacketSendEvent event) {
        if (this.getEnable()) {
            if (event.packet instanceof CPacketPlayer || event.packet instanceof CPacketPlayerDigging || event.packet instanceof CPacketUseEntity || event.packet instanceof C00Handshake) {
                event.setCanceled(true);
            }
        }
    }
}
