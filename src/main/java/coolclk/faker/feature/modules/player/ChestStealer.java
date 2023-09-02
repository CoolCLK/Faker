package coolclk.faker.feature.modules.player;

import coolclk.faker.event.events.BlockChestActivatedEvent;
import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.api.SettingsLong;
import coolclk.faker.feature.modules.ModuleCategory;
import coolclk.faker.util.ModuleUtil;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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

    @SideOnly(value = Side.CLIENT)
    @SubscribeEvent
    public void onPlayerOpenContainer(BlockChestActivatedEvent event) {
        if (this.getEnable()) {
            if (opened <= 0 && ModuleUtil.gEP().openContainer instanceof ContainerPlayer) {
                ContainerPlayer container = (ContainerPlayer) ModuleUtil.gEP().openContainer;
                List<Packet<?>> packets = new ArrayList<Packet<?>>();
                if (container.getInventory().size() > 36) {
                    for (int i = 0; i < container.getInventory().size(); i++) {
                        if (container.getSlot(i).getStack() != null) {
                            packets.add(new C0EPacketClickWindow(container.windowId, container.getSlot(i).getSlotIndex(), 0, 1, container.getSlot(i).getStack(), container.getNextTransactionID(ModuleUtil.gEP().inventory)));
                        }
                    }
                }
                for (Packet<?> packet : packets) ModuleUtil.gNM().sendPacket(packet);
            } else if (opened > maxOpenTime.getValue()) {
                ModuleUtil.gEP().closeScreen();
                ModuleUtil.gEP().inventoryContainer.onContainerClosed(ModuleUtil.gEP());
                opened = 0;
            }
            opened++;
        }
    }
}
