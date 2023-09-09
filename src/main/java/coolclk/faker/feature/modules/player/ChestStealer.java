package coolclk.faker.feature.modules.player;

import coolclk.faker.event.api.SubscribeEvent;
import coolclk.faker.event.events.BlockActivatedEvent;
import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.api.SettingsLong;
import coolclk.faker.feature.modules.ModuleCategory;
import coolclk.faker.util.ModuleUtil;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0EPacketClickWindow;

import java.util.ArrayList;
import java.util.List;

@ModuleInfo(name = "ChestStealer", category = ModuleCategory.Player)
public class ChestStealer extends Module {
    public SettingsLong maxOpenTime = new SettingsLong(this, "maxOpenTime", 8L, 0L, 20L);
    long opened = 0;

    @Override
    public void onEnable() {
        opened = 0;
    }

    @SubscribeEvent
    public void onPlayerOpenContainer(BlockActivatedEvent event) {
        if (this.getEnable() && (event.getBlockState().getBlock() == Blocks.chest || event.getBlockState().getBlock() == Blocks.trapped_chest)) {
            if (opened <= 0 && event.getEntityPlayer().openContainer instanceof ContainerPlayer) {
                ContainerPlayer container = (ContainerPlayer) event.getEntityPlayer().openContainer;
                List<Packet<?>> packets = new ArrayList<Packet<?>>();
                if (container.getInventory().size() > 36) {
                    for (int i = 0; i < container.getInventory().size(); i++) {
                        if (container.getSlot(i).getStack() != null) {
                            packets.add(new C0EPacketClickWindow(container.windowId, container.getSlot(i).getSlotIndex(), 0, 1, container.getSlot(i).getStack(), container.getNextTransactionID(event.getEntityPlayer().inventory)));
                        }
                    }
                }
                for (Packet<?> packet : packets) ModuleUtil.gNM().sendPacket(packet);
            } else if (opened > maxOpenTime.getValue()) {
                event.getEntityPlayer().closeScreen();
                event.getEntityPlayer().inventoryContainer.onContainerClosed(event.getEntityPlayer());
                opened = 0;
            }
            opened++;
        }
    }
}
