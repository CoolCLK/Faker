package coolclk.faker.injection.mixins;

import coolclk.faker.event.EventHandler;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngameForge.class)
public class MixinGuiIngameForge {
    @Inject(method = "post", at = @At(value = "RETURN"), remap = false)
    private void post(RenderGameOverlayEvent.ElementType type, CallbackInfo ci) {
        EventHandler.post(new coolclk.faker.event.events.RenderGameOverlayEvent());
    }
}
