package coolclk.faker.feature.api;

import java.util.Arrays;
import java.util.List;

public class SettingsModeString extends SettingsMode<String> {
    public SettingsModeString(Module parent, String value, List<String> modes) {
        super(parent, value, modes);
    }

    public SettingsModeString(Module parent, String value, String... modes) {
        this(parent, value, Arrays.asList(modes));
    }
}
