package coolclk.faker.injection.mixins;

import coolclk.faker.event.EventHandler;
import coolclk.faker.event.events.ResourceReloadedEvent;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(SimpleReloadableResourceManager.class)
public class MixinSimpleReloadableResourceManager {
    @Inject(method = "reloadResources", at = @At(value = "RETURN"))
    public void reloadedResources(List<IResourcePack> resourcesPacksList, CallbackInfo ci) {
        EventHandler.post(new ResourceReloadedEvent(resourcesPacksList));
    }
}
