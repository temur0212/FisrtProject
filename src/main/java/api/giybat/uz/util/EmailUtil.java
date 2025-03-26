package api.giybat.uz.util;

import java.util.regex.Pattern;

public class EmailUtil {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");

    public static boolean isEmail(String value) {
        if (value == null) {
            return false;
        }
        return EMAIL_PATTERN.matcher(value).matches();
    }
}
