package coolclk.faker.event.events;

import coolclk.faker.event.api.Event;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.NetworkManager;

public class PlayerUpdateEvent extends Event {
    public enum Shift {
        BEFORE,
        AFTER
    }

    protected final Shift shift;
    protected final EntityPlayer entityPlayer;

    public PlayerUpdateEvent(Shift shift, EntityPlayer entityPlayer) {
        this.shift = shift;
        this.entityPlayer = entityPlayer;
    }

    public Shift getShift() {
        return this.shift;
    }

    public EntityPlayerSP getEntityPlayer() {
        return (EntityPlayerSP) this.entityPlayer;
    }

    public PlayerControllerMP getPlayerController() {
        return Minecraft.getMinecraft().playerController;
    }

    public WorldClient getWorldClient() {
        return Minecraft.getMinecraft().theWorld;
    }

    public NetworkManager getNetworkManager() {
        return Minecraft.getMinecraft().getNetHandler().getNetworkManager();
    }
}
