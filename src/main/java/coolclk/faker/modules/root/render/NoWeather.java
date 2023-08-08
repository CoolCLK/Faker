package coolclk.faker.modules.root.render;

import coolclk.faker.modules.Module;
import coolclk.faker.util.ModuleUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraftforge.client.IRenderHandler;

public class NoWeather extends Module {
    public static NoWeather INSTANCE = new NoWeather();

    private IRenderHandler oldWeatherRender;
    private final IRenderHandler clearWeatherRender = new IRenderHandler() {
        @Override
        public void render(float partialTicks, WorldClient world, Minecraft mc) {

        }
    };

    public NoWeather() {
        super("NoWeather");
    }

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
