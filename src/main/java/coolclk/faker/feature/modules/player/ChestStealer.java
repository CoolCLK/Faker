package coolclk.faker.feature.modules.player;

import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.modules.ModuleCategory;
import coolclk.faker.util.ModuleUtil;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraftforge.event.entity.player.PlayerOpenContainerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

@ModuleInfo(name = "ChestStealer", category = ModuleCategory.Player)
public class ChestStealer extends Module {
    long opened = 0;

    @Override
    public void onEnable() {
        opened = 0;
    }

    @SubscribeEvent
    public void onPlayerOpenContainer(PlayerOpenContainerEvent event) {
        if (this.getEnable()) {
            if (event.canInteractWith && event.entityPlayer.openContainer instanceof ContainerChest) {
                if (opened <= 0) {
                    ContainerChest chestContainer = (ContainerChest) event.entityPlayer.openContainer;
                    List<Packet<?>> packets = new ArrayList<Packet<?>>();
                    for (int i = 0; i < chestContainer.getLowerChestInventory().getSizeInventory(); i++) {
                        if (chestContainer.getLowerChestInventory().getStackInSlot(i) != null) {
                            packets.add(new C0EPacketClickWindow(chestContainer.windowId, i, 0, 1, chestContainer.getLowerChestInventory().getStackInSlot(i), chestContainer.getNextTransactionID(event.entityPlayer.inventory)));
                        }
                    }
                    for (Packet<?> packet : packets) ModuleUtil.gNM().sendPacket(packet);
                } else if (opened > 8) {
                    event.entityPlayer.closeScreen();
                    event.entityPlayer.inventoryContainer.onContainerClosed(event.entityPlayer);
                    opened = 0;
                }
                opened++;
            }
        }
    }
}
