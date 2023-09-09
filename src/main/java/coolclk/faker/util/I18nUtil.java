package coolclk.faker.util;

import coolclk.faker.Main;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class I18nUtil {
    protected static Map<String, String> translateMap = new HashMap<String, String>();
    protected static Locale locale = Locale.getDefault();

    static {
        refreshTranslateMap();
    }

    public static void refreshTranslateMap() {
        try {
            translateMap.clear();
            InputStream inputStream = Main.class.getResourceAsStream("/assets/faker/lang/" + getLanguage() + ".lang");
            if (inputStream != null) {
                byte[] contentBytes = new byte[inputStream.available()];
                int contentByte = -1;
                for (int byteIndex = 0; (contentByte = inputStream.read()) != -1; byteIndex++) {
                    contentBytes[byteIndex] = (byte) contentByte;
                }
                String content = new String(contentBytes);
                for (String contentLine : content.contains("\n") ? content.split("\n") : new String[] {content}) {
                    String[] contentLineData = contentLine.split("=", 1);
                    if (contentLineData.length == 2) {
                        translateMap.put(contentLineData[0], contentLineData[1]);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setLocale(String minecraftLanguage) {
        String[] data = minecraftLanguage.split("_", 1);
        if (data.length == 2) {
            setLocale(new Locale(data[0], data[1]));
        }
    }

    public static void setLocale(Locale locale) {
        I18nUtil.locale = locale;
    }

    public static String format(String translateKey, Object... parameters) {
        if (!translateMap.containsKey(translateKey)) {
            return translateKey;
        }
        return String.format(translateMap.get(translateKey), parameters);
    }

    public static String format(String translateKey) {
        return format(translateKey, (Object) null);
    }

    public static String getLanguage() {
        return locale.getLanguage() + "_" + locale.getCountry();
    }
}
