package coolclk.faker.modules.root.player;

import coolclk.faker.modules.Module;
import coolclk.faker.util.ModuleUtil;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Collections;

public class FastBreak extends Module {
    public static FastBreak INSTANCE = new FastBreak();

    public FastBreak() {
        super("FastBreak", Collections.singletonList(new ModuleArgument("damage", 5, 0, 20)));
    }

    @SubscribeEvent
    public void onBreak(PlayerEvent.BreakSpeed event) {
        if (event.getEntityPlayer() == ModuleUtil.gEP()) {
            event.setNewSpeed(event.getOriginalSpeed() + this.getArgument("damage").getNumberValueF());
        }
    }
}
