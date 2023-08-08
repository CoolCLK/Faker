package coolclk.faker.injection.mixins;

import coolclk.faker.gui.GuiHandler;
import coolclk.faker.modules.Module;
import coolclk.faker.modules.ModuleHandler;
import coolclk.faker.modules.ModuleType;
import coolclk.faker.modules.root.render.HUD;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.GuiIngameForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngameForge.class)
public abstract class MixinGuiIngameForge {
    @Shadow
    public ScaledResolution getResolution() {
        return null;
    }

    @Inject(at = @At(value = "RETURN"), method = "renderGameOverlay")
    private void renderGameOverlay(float partialTicks, CallbackInfo ci) {
        FontRenderer fontRendererObj = Minecraft.getMinecraft().fontRendererObj;
        if (HUD.INSTANCE.getEnable()) {
            int margin = 15;
            fontRendererObj.drawStringWithShadow("Faker", margin, margin, GuiHandler.getRainbowColor(0.000125));
            int yPosition = margin;
            for (ModuleType group : ModuleHandler.getAllModules()) {
                for (Module module : group.getModules()) {
                    if (module.getEnable()) {
                        fontRendererObj.drawStringWithShadow(module.getDisplayName(), this.getResolution().getScaledWidth() - fontRendererObj.getStringWidth(module.getDisplayName()) - margin, yPosition, GuiHandler.getRainbowColor(0.000125));
                        yPosition += 10;
                    }
                }
            }
        }
    }
}
