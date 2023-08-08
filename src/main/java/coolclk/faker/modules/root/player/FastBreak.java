package coolclk.faker.modules.root.player;

import coolclk.faker.modules.Module;
import coolclk.faker.util.ModuleUtil;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Collections;

import static coolclk.faker.launch.FakerForgeMod.LOGGER;

public class FastBreak extends Module {
    public static FastBreak INSTANCE = new FastBreak();

    public FastBreak() {
        super("FastBreak", Collections.singletonList(new ModuleArgument("damage", 5, 0, 20)));
    }

    @SubscribeEvent
    public void onBreak(BlockEvent.BreakEvent event) {
        if (event.getPlayer() == ModuleUtil.gEP()) {
            try {
                float blockDamage = ModuleUtil.getInaccessibleVariableF(ModuleUtil.gPC(), "curBlockDamageMP");
                blockDamage += this.getArgument("damage").getNumberValueF();
                ModuleUtil.setInaccessibleVariable(ModuleUtil.gPC(), "curBlockDamageMP", blockDamage);
            } catch (Exception e) {
                LOGGER.error(I18n.format("modules.message.ModuleThrowsError", this.getDisplayName()));
                throw new RuntimeException(e);
            }
        }
    }
}
