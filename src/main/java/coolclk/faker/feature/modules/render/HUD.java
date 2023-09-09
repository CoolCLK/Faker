package coolclk.faker.feature.modules.render;

import coolclk.faker.event.api.SubscribeEvent;
import coolclk.faker.event.events.RenderGameOverlayEvent;
import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.api.SettingsBoolean;
import coolclk.faker.feature.modules.ModuleCategory;
import coolclk.faker.gui.GuiHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;

import java.util.ArrayList;
import java.util.List;

@ModuleInfo(name = "HUD", category = ModuleCategory.Render)
public class HUD extends Module {
    public SettingsBoolean moduleList = new SettingsBoolean(this, "moduleList", true);
    public SettingsBoolean notification = new SettingsBoolean(this, "notification", true);

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent event) {
        if (this.getEnable()) {
            FontRenderer fontRendererObj = Minecraft.getMinecraft().fontRendererObj;
            int margin = 10, rainbowSpeed = 5;
            if (moduleList.getValue()) {
                int yPosition = margin;
                int colorMoved = 0;
                List<Module> unsortedList = new ArrayList<Module>(ModuleCategory.getAllModules());
                List<Module> list = new ArrayList<Module>();
                for (Module module : unsortedList) {
                    int index = 0;
                    if (!list.isEmpty()) {
                        int width, lastWidth;
                        index = list.size() - 1;
                        do {
                            width = fontRendererObj.getStringWidth(module.getDisplayName());
                            lastWidth = index < list.size() ? fontRendererObj.getStringWidth(list.get(index).getDisplayName()) : 0;
                            if (width < lastWidth) index--;
                        } while (width < lastWidth || index <= 0);
                    }
                    list.add(index, module);
                }
                for (Module module : ModuleCategory.getAllModules()) {
                    if (module.getEnable()) {
                        fontRendererObj.drawStringWithShadow(module.getDisplayName(), new ScaledResolution(Minecraft.getMinecraft()).getScaledWidth() - margin, yPosition, GuiHandler.getRainbowColor(rainbowSpeed, colorMoved));
                        yPosition += fontRendererObj.FONT_HEIGHT;
                        colorMoved += 1;
                    }
                }
            }
            if (notification.getValue()) {

            }
        }
    }
}
