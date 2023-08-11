package coolclk.faker.injection.mixins;

import coolclk.faker.feature.ModuleHandler;
import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.modules.ModuleCategory;
import coolclk.faker.feature.modules.render.HUD;
import coolclk.faker.gui.GuiHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.GuiIngameForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngameForge.class)
public abstract class MixinGuiIngameForge {
    @Inject(method = "renderGameOverlay", at = @At(value = "RETURN"))
    private void renderGameOverlay(float partialTicks, CallbackInfo ci) {
        if (ModuleHandler.findModule(HUD.class).getEnable()) {
            FontRenderer fontRendererObj = Minecraft.getMinecraft().fontRendererObj;
            int margin = 10;
            fontRendererObj.drawStringWithShadow("Faker", margin, margin, GuiHandler.getRainbowColor(1));
            int yPosition = margin;
            for (Module module : ModuleCategory.getAllModules()) {
                if (module.getEnable()) {
                    fontRendererObj.drawStringWithShadow(module.getDisplayName(), new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth() - fontRendererObj.getStringWidth(module.getDisplayName()) - margin, yPosition, GuiHandler.getRainbowColor(1));
                    yPosition += fontRendererObj.FONT_HEIGHT;
                }
            }
        }
    }
}
