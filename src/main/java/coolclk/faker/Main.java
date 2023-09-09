package coolclk.faker;

import coolclk.faker.event.EventHandler;
import coolclk.faker.event.events.ResourceReloadedEvent;
import coolclk.faker.feature.ModuleHandler;
import coolclk.faker.util.LoggerUtil;
import net.minecraft.client.resources.IResourcePack;
import org.apache.logging.log4j.Logger;

import java.util.Collections;

public class Main {
    public static boolean isForge = false;
    public static Logger logger = LoggerUtil.getLogger();

    public static void setup() {
        logger.info("Start setup");
        ModuleHandler.register();
        EventHandler.post(new ResourceReloadedEvent(Collections.<IResourcePack>emptyList()));
    }
}
