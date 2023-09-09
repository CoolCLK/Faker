package coolclk.faker.event.events;

import coolclk.faker.event.api.Event;
import net.minecraft.client.resources.IResourcePack;

import java.util.List;

public class ResourceReloadedEvent extends Event {
    protected List<IResourcePack> resourcesPacksList;

    public ResourceReloadedEvent(List<IResourcePack> resourcesPacksList) {
        this.resourcesPacksList = resourcesPacksList;
    }

    public List<IResourcePack> getResourcesPacksList() {
        return this.resourcesPacksList;
    }
}
