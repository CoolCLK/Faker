package coolclk.faker.feature.modules.render;

import coolclk.faker.feature.api.Module;
import coolclk.faker.feature.api.ModuleInfo;
import coolclk.faker.feature.modules.ModuleCategory;
import coolclk.faker.util.ModuleUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraftforge.client.IRenderHandler;

@ModuleInfo(name = "NoWeather", category = ModuleCategory.Render)
public class NoWeather extends Module {
    private IRenderHandler oldWeatherRender;
    private final IRenderHandler clearWeatherRender = new IRenderHandler() {
        @Override
        public void render(float partialTicks, WorldClient world, Minecraft mc) {

        }
    };

    @Override
    public void onEnable() {
        oldWeatherRender = ModuleUtil.gW().provider.getWeatherRenderer();
    }

    @Override
    public void onEnabling() {
        if (ModuleUtil.gW().provider.getWeatherRenderer() != clearWeatherRender) {
            ModuleUtil.gW().provider.setWeatherRenderer(clearWeatherRender);
        }
    }

    @Override
    public void onDisable() {
        ModuleUtil.gW().provider.setWeatherRenderer(oldWeatherRender);
    }
}
