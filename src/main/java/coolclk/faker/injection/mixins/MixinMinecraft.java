package coolclk.faker.injection.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Util;
import org.apache.commons.io.IOUtils;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {
    @Inject(at = @At(value = "RETURN"), method = "createDisplay")
    private void setWindowTitle(CallbackInfo ci) {
        Display.setTitle(Display.getTitle() + " | " + "Faker made by CoolCLK");
    }

    @Inject(at = @At(value = "RETURN"), method = "setWindowIcon")
    private void setWindowIcon(CallbackInfo ci) {
        if (Util.getOSType() != Util.EnumOS.OSX) {
            InputStream icon16x = null;
            InputStream icon32x = null;
            InputStream icon64x = null;
            InputStream icon128x = null;
            try {
                icon16x = this.getClass().getResourceAsStream("/assets/faker/icons/icon_16x16.png");
                icon32x = this.getClass().getResourceAsStream("/assets/faker/icons/icon_32x32.png");
                icon64x = this.getClass().getResourceAsStream("/assets/faker/icons/icon_64x64.png");
                icon128x = this.getClass().getResourceAsStream("/assets/faker/icons/icon_128x128.png");
                if (icon16x != null && icon32x != null && icon64x != null && icon128x != null) {
                    Display.setIcon(new ByteBuffer[] { readImageToBuffer(icon16x), readImageToBuffer(icon32x), readImageToBuffer(icon64x), readImageToBuffer(icon128x) });
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                IOUtils.closeQuietly(icon16x);
                IOUtils.closeQuietly(icon32x);
                IOUtils.closeQuietly(icon64x);
                IOUtils.closeQuietly(icon128x);
            }
        }
    }

    @Shadow protected abstract ByteBuffer readImageToBuffer(InputStream imageStream) throws IOException;
}
