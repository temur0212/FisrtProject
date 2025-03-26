package api.giybat.uz.util;

import java.util.regex.Pattern;

public class PhoneUtil {
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?998\\d{9}$");

    public static boolean isPhone(String value) {
        if (value == null) {
            return false;
        }
        return PHONE_PATTERN.matcher(value).matches();
    }
}
