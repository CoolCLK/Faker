package coolclk.faker.modules.root.combat;

import coolclk.faker.modules.Module;
import coolclk.faker.util.ModuleUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;

public class Criticals extends Module {
    public static Criticals INSTANCE = new Criticals();

    public Criticals() {
        super("Criticals", Arrays.asList(new ModuleArgument("onlyPacket", true), new ModuleArgument("jumpHeight", 0.42, 0, 0.5)));
    }

    @SubscribeEvent
    public void onAttackEntity(AttackEntityEvent event) {
        if (this.getEnable()) {
            if (event.entityPlayer == ModuleUtil.gEP()) {
                if (event.entityPlayer.onGround) {
                    event.entityPlayer.onGround = false;
                    event.entityPlayer.fallDistance = 0.01F;
                    if (this.getArgument("onlyPacket").getBooleanValue()) {
                        for (double d : new double[] { 0.0625, 0.01 - Math.random() / 10000 }) {
                            Minecraft.getMinecraft().getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(event.entityPlayer.posX, event.entityPlayer.posY + d, event.entityPlayer.posZ, false));
                        }
                    } else {
                        double offset = 0.01;
                        event.entityPlayer.posY += offset;
                        event.entityPlayer.motionY = this.getArgument("jumpHeight").getNumberValueD() - offset;
                    }
                }
            }
        }
    }
}
