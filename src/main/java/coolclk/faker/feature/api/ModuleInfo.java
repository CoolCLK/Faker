package coolclk.faker.feature.api;

import coolclk.faker.feature.modules.ModuleCategory;
import org.lwjgl.input.Keyboard;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ModuleInfo {
    /**
     * @author CoolCLK
     * @return The module's name
     */
    String name();

    /**
     * @author CoolCLK
     * @return The module's category
     */
    ModuleCategory category() default ModuleCategory.None;

    /**
     * @author CoolCLK
     * @return The module's binding keycode
     */
    int defaultKeycode() default Keyboard.KEY_NONE;
}
