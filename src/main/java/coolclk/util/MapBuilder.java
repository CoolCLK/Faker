package coolclk.util;

import java.util.HashMap;
import java.util.Map;

public class MapBuilder {
    public static Map<?, ?> of(Object... keyAndValue) {
        if (keyAndValue.length % 2 == 0) {
            Map<Object, Object> map = new HashMap<Object, Object>();
            boolean isKey = true;
            Object key = null;
            for (Object keyOrValue : keyAndValue) {
                if (isKey) {
                    key = keyOrValue;
                } else {
                    map.put(key, keyOrValue);
                }
                isKey = !isKey;
            }
            return map;
        }
        return new HashMap<Object, Object>();
    }
}
