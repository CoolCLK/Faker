package coolclk.faker.feature.modules.render;

import coolclk.faker.event.PacketSendEvent;
import coolclk.faker.feature.ModuleHandler;
import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.api.SettingsDouble;
import coolclk.faker.feature.api.SettingsFloat;
import coolclk.faker.feature.modules.ModuleCategory;
import coolclk.faker.feature.modules.movement.Fly;
import coolclk.faker.util.ModuleUtil;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C14PacketTabComplete;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@ModuleInfo(name = "FreeCam", category = ModuleCategory.Render)
public class FreeCam extends Module {
    private EntityOtherPlayerMP fakePlayer;
    private float oldRotationPitch, oldRotationYaw;
    private double oldPosX, oldPosY, oldPosZ;

    public SettingsFloat flyHorizontalSpeed = new SettingsFloat(this, "flyHorizontalSpeed", 0.1F, 0F, 5F);
    public SettingsDouble flyVerticalSpeed = new SettingsDouble(this, "flyVerticalSpeed", 0.1D, 0D, 5D);

    private boolean oldIsFlying = false;

    @Override
    public void onRegister() {
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

        if (ModuleHandler.findModule(Fly.class).getEnable()) {
            ModuleHandler.findModule(Fly.class).toggleModule();
        }

        oldIsFlying = ModuleUtil.gEP().capabilities.isFlying;
    }

    @Override
    public void onEnabling() {
        ModuleUtil.gEP().capabilities.isFlying = true;
        ModuleUtil.gEP().noClip = true;
        ModuleUtil.gEP().moveEntityWithHeading(ModuleUtil.gEP().movementInput.moveStrafe * flyHorizontalSpeed.getValue(), ModuleUtil.gEP().movementInput.moveForward * flyHorizontalSpeed.getValue());
        if (ModuleUtil.gM().gameSettings.keyBindJump.isKeyDown() || ModuleUtil.gM().gameSettings.keyBindSneak.isKeyDown()) {
            ModuleUtil.gEP().motionY = (ModuleUtil.gM().gameSettings.keyBindJump.isKeyDown() ? flyVerticalSpeed.getValue() : 0) + (ModuleUtil.gM().gameSettings.keyBindSneak.isKeyDown() ? -flyVerticalSpeed.getValue() : 0);
        } else {
            ModuleUtil.gEP().motionY = 0;
        }
    }

    @Override
    public void onDisable() {
        if (fakePlayer != null) {
            ModuleUtil.gW().removeEntityFromWorld(fakePlayer.getEntityId());
            ModuleUtil.gEP().setPositionAndRotation(oldPosX, oldPosY, oldPosZ, oldRotationYaw, oldRotationPitch);
        }
        ModuleUtil.gEP().noClip = false;
        ModuleUtil.gEP().onGround = true;
        ModuleUtil.gEP().fallDistance = 0;
        ModuleUtil.gEP().motionY = 0;
        ModuleUtil.gEP().capabilities.isFlying = oldIsFlying;
    }

    @SubscribeEvent
    public void onPacketSend(PacketSendEvent event) {
        if (this.getEnable()) {
            if (!(event.packet instanceof C14PacketTabComplete || event.packet instanceof C01PacketChatMessage)) {
                event.setCanceled(true);
            }
        }
    }
}
