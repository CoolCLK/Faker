package coolclk.faker.injection.mixins;

import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.network.handshake.FMLHandshakeMessage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(FMLHandshakeMessage.ModList.class)
public abstract class MixinModList {
    // @Shadow(remap = false) private Map<String, String> modTags;

    @Inject(method = "<init>(Ljava/util/List;)V", at = @At(value = "RETURN"), remap = false)
    public void init(List<ModContainer> modList, CallbackInfo ci) {
        // this.modTags.remove(FakerForgeMod.MODID); // Remove Faker on mod list.
    }
}
