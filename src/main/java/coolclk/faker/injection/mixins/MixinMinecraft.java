package coolclk.faker.injection.mixins;

import coolclk.faker.event.EventHandler;
import coolclk.faker.event.events.KeyInputEvent;
import coolclk.faker.feature.ModuleHandler;
import coolclk.faker.feature.modules.render.ClickGui;
import coolclk.faker.gui.clickgui.ClickGuiScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(Minecraft.class)
public class MixinMinecraft {
    @Shadow public GuiScreen currentScreen;

    @Unique List<Integer> faker$downKeys = new ArrayList<Integer>();

    @Inject(method = "runTick", at = @At(value = "HEAD"))
    public void beforeTick(CallbackInfo ci) {
        if (ModuleHandler.findModule(ClickGui.class).getEnable() && this.currentScreen == null) {
            this.currentScreen = ClickGuiScreen.INSTANCE;
        } else if (!ModuleHandler.findModule(ClickGui.class).getEnable() && this.currentScreen == ClickGuiScreen.INSTANCE) {
            this.currentScreen = null;
        }
    }

    @Inject(method = "runTick", at = @At(value = "INVOKE", shift = At.Shift.AFTER, target = "Lnet/minecraft/client/settings/KeyBinding;setKeyBindState(IZ)V"))
    public void onKeyboard(CallbackInfo ci) {
        if (Keyboard.getEventKey() > 0 && Keyboard.getEventKeyState()) {
            EventHandler.post(new KeyInputEvent(Keyboard.getEventKey()));
        }
    }

    @Inject(method = "runTick", at = @At(value = "RETURN"))
    public void afterTick(CallbackInfo ci) {
        KeyInputEvent.clearHoldingKeys();
    }
}
