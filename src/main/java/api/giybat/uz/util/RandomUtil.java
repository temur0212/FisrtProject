package api.giybat.uz.util;

import java.util.Random;

public class RandomUtil {
    public static final Random random = new Random();

    public static String getRandomValue()
    {
        return String.valueOf(random.nextInt(10000,99999));
    }
}
