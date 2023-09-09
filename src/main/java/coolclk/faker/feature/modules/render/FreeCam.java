package coolclk.faker.feature.modules.render;

import coolclk.faker.event.api.SubscribeEvent;
import coolclk.faker.event.events.PacketEvent;
import coolclk.faker.event.events.PlayerUpdateEvent;
import coolclk.faker.feature.ModuleHandler;
import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.api.SettingsDouble;
import coolclk.faker.feature.api.SettingsFloat;
import coolclk.faker.feature.modules.ModuleCategory;
import coolclk.faker.feature.modules.movement.Fly;
import coolclk.faker.util.ModuleUtil;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.*;

@ModuleInfo(name = "FreeCam", category = ModuleCategory.Render)
public class FreeCam extends Module {
    private EntityOtherPlayerMP fakePlayer = null;
    private float oldRotationPitch, oldRotationYaw;
    private double oldPosX, oldPosY, oldPosZ;

    public SettingsFloat flyHorizontalSpeed = new SettingsFloat(this, "flyHorizontalSpeed", 0.1F, 0F, 5F);
    public SettingsDouble flyVerticalSpeed = new SettingsDouble(this, "flyVerticalSpeed", 0.1D, 0D, 5D);
    
    private EntityPlayerSP entityPlayer;
    
    @Override
    public boolean getCanKeepEnable() {
        return false;
    }

    @Override
    public void onRegister() {
        if (this.getEnable()) {
            this.toggleModule();
        }
    }

    @Override
    public void onEnable() {
        if (entityPlayer != null) {
            fakePlayer = new EntityOtherPlayerMP(ModuleUtil.gW(), entityPlayer.getGameProfile());
            fakePlayer.setPositionAndRotation(entityPlayer.posX, entityPlayer.posY, entityPlayer.posZ, entityPlayer.rotationYaw, entityPlayer.rotationPitch);
            fakePlayer.rotationYawHead = entityPlayer.rotationYawHead;

            ModuleUtil.gW().addEntityToWorld(fakePlayer.getEntityId(), fakePlayer);

            oldRotationPitch = entityPlayer.rotationPitch;
            oldRotationYaw = entityPlayer.rotationYaw;
            oldPosX = entityPlayer.posX;
            oldPosY = entityPlayer.posY;
            oldPosZ = entityPlayer.posZ;

            if (ModuleHandler.findModule(Fly.class).getEnable()) {
                ModuleHandler.findModule(Fly.class).toggleModule();
            }
        }
    }

    @Override
    public void onUpdate(PlayerUpdateEvent event) {
        entityPlayer = event.getEntityPlayer();
        event.getEntityPlayer().capabilities.isFlying = true;
        event.getEntityPlayer().noClip = true;
        event.getEntityPlayer().moveEntityWithHeading(event.getEntityPlayer().movementInput.moveStrafe * flyHorizontalSpeed.getValue(), event.getEntityPlayer().movementInput.moveForward * flyHorizontalSpeed.getValue());
        if (ModuleUtil.gM().gameSettings.keyBindJump.isKeyDown() || ModuleUtil.gM().gameSettings.keyBindSneak.isKeyDown()) {
            event.getEntityPlayer().motionY = (ModuleUtil.gM().gameSettings.keyBindJump.isKeyDown() ? flyVerticalSpeed.getValue() : 0) + (ModuleUtil.gM().gameSettings.keyBindSneak.isKeyDown() ? -flyVerticalSpeed.getValue() : 0);
        } else {
            event.getEntityPlayer().motionY = 0;
        }
    }

    @Override
    public void onDisable() {
        if (entityPlayer != null) {
            if (fakePlayer != null) {
                ModuleUtil.gW().removeEntityFromWorld(fakePlayer.getEntityId());
                entityPlayer.setPositionAndRotation(oldPosX, oldPosY, oldPosZ, oldRotationYaw, oldRotationPitch);
            }
            entityPlayer.noClip = false;
            entityPlayer.onGround = true;
            entityPlayer.fallDistance = 0;
            entityPlayer.motionY = 0;
            entityPlayer.capabilities.isFlying = ModuleHandler.findModule(Fly.class).getEnable();
        }
    }

    @SubscribeEvent
    public void onPacketSend(PacketEvent event) {
        if (this.getEnable() && event.getType() == PacketEvent.Type.SEND) {
            if (event.isTypeOf(C0APacketAnimation.class, C0BPacketEntityAction.class, C0CPacketInput.class, C02PacketUseEntity.class, C03PacketPlayer.class, C07PacketPlayerDigging.class, C08PacketPlayerBlockPlacement.class, C09PacketHeldItemChange.class, C10PacketCreativeInventoryAction.class, C11PacketEnchantItem.class, C12PacketUpdateSign.class, C13PacketPlayerAbilities.class)) {
                event.setCanceled(true);
            }
        }
    }

    public EntityPlayer getFreeCamObject() {
        return fakePlayer;
    }
}
