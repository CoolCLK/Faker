package coolclk.faker.event.events;

import coolclk.faker.event.api.Event;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class PlayerAttackEntityEvent extends Event {
    protected final EntityPlayer player;
    protected final Entity targetEntity;

    public PlayerAttackEntityEvent(EntityPlayer playerIn, Entity targetEntity) {
        this.player = playerIn;
        this.targetEntity = targetEntity;
    }

    public EntityPlayer getEntityPlayer() {
        return this.player;
    }

    public Entity getTargetEntity() {
        return this.targetEntity;
    }
}
