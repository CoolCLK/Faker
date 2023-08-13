package coolclk.faker.injection.mixins;

import coolclk.faker.feature.ModuleHandler;
import coolclk.faker.feature.modules.movement.Fly;
import coolclk.faker.feature.modules.player.DisableDamage;
import coolclk.faker.feature.modules.player.NoAntiBuild;
import coolclk.faker.feature.modules.render.FreeCam;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.world.WorldSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldSettings.GameType.class)
public class MixinWorldSettingsGameType {
    @Inject(method = "configurePlayerCapabilities", at = @At(value = "RETURN"))
    public void configurePlayerCapabilities(PlayerCapabilities capabilities, CallbackInfo ci) {
        if (ModuleHandler.findModule(DisableDamage.class).getEnable()) {
            capabilities.disableDamage = true;
        }
        if (ModuleHandler.findModule(Fly.class).getEnable() || ModuleHandler.findModule(FreeCam.class).getEnable()) {
            capabilities.allowFlying = true;
        }
        if (ModuleHandler.findModule(NoAntiBuild.class).getEnable()) {
            capabilities.allowEdit = true;
        }
    }
}
