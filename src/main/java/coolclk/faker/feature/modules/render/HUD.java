package coolclk.faker.feature.modules.render;

import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.modules.ModuleCategory;
import coolclk.faker.gui.GuiHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@ModuleInfo(name = "HUD", category = ModuleCategory.Render)
public class HUD extends Module {
    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent event) {
        if (this.getEnable() && event.type == RenderGameOverlayEvent.ElementType.ALL) {
            FontRenderer fontRendererObj = Minecraft.getMinecraft().fontRendererObj;
            int margin = 10;
            fontRendererObj.drawStringWithShadow("Faker", margin, margin, GuiHandler.getRainbowColor(1));
            int yPosition = margin;
            int colorMoved = 0;
            for (Module module : ModuleCategory.getAllModules()) {
                if (module.getEnable()) {
                    fontRendererObj.drawStringWithShadow(module.getDisplayName(), new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth() - fontRendererObj.getStringWidth(module.getDisplayName()) - margin, yPosition, GuiHandler.getRainbowColor(1, colorMoved));
                    yPosition += fontRendererObj.FONT_HEIGHT;
                }
                colorMoved += 5;
            }
        }
    }
}
