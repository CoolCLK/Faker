package coolclk.faker.modules.root.combat;

import coolclk.faker.modules.Module;
import coolclk.faker.util.ModuleUtil;
import net.minecraft.network.play.client.CPacketPlayer;
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
            if (event.getEntityPlayer() == ModuleUtil.gEP()) {
                if (event.getEntityPlayer().onGround) {
                    event.getEntityPlayer().onGround = false;
                    event.getEntityPlayer().fallDistance = 0.01F;
                    if (this.getArgument("onlyPacket").getBooleanValue()) {
                        for (double d : new double[] { 0.0625, 0.01 - Math.random() / 10000 }) {
                            if (ModuleUtil.getNetworkManager() != null) {
                                ModuleUtil.gNM().sendPacket(new CPacketPlayer.Position(event.getEntityPlayer().posX, event.getEntityPlayer().posY + d, event.getEntityPlayer().posZ, false));
                            }
                        }
                    } else {
                        double offset = 0.01;
                        event.getEntityPlayer().posY += offset;
                        event.getEntityPlayer().motionY = this.getArgument("jumpHeight").getNumberValueD() - offset;
                    }
                }
            }
        }
    }
}
