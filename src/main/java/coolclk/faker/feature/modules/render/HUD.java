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
    public static String MESSAGE = "";

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent event) {
        if (event.type == RenderGameOverlayEvent.ElementType.TEXT) {
            if (this.getEnable()) {
                FontRenderer fontRendererObj = Minecraft.getMinecraft().fontRendererObj;
                int margin = 10, rainbowSpeed = 5;
                int yPosition = margin;
                int colorMoved = 0;
                for (Module module : ModuleCategory.getAllModules()) {
                    String moduleInfo = module.getHUDInfo();
                    if (moduleInfo.trim().isEmpty()) moduleInfo = "";
                    if (module.getEnable()) {
                        fontRendererObj.drawStringWithShadow(module.getDisplayName(), new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth() - fontRendererObj.getStringWidth(module.getDisplayName() + moduleInfo) - (moduleInfo.isEmpty() ? 0 : 2) - margin, yPosition, GuiHandler.getRainbowColor(rainbowSpeed, colorMoved));
                        fontRendererObj.drawStringWithShadow(moduleInfo, new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth() - fontRendererObj.getStringWidth(moduleInfo) - margin, yPosition, GuiHandler.Theme.MODULE_BUTTON_HUD_INFO_COLOR);
                        yPosition += fontRendererObj.FONT_HEIGHT;
                        colorMoved += 1;
                    }
                }

                yPosition = margin;
                colorMoved = 0;
                for (String line : MESSAGE.contains("\n") ? MESSAGE.split("\n") : new String[]{MESSAGE}) {
                    fontRendererObj.drawStringWithShadow(line, margin, yPosition, GuiHandler.getRainbowColor(rainbowSpeed, 0));
                    yPosition += fontRendererObj.FONT_HEIGHT;
                    colorMoved += 1;
                }
            }
        }
    }
}
