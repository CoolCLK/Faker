package coolclk.faker.launch.agent;

import org.spongepowered.asm.mixin.transformer.MixinTransformer;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class ClassTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        try {
            MixinTransformer.class.getDeclaredConstructor(new Class[0]).newInstance().transformClassBytes(className, className, classfileBuffer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new byte[0];
    }
}
