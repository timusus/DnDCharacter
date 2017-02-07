package com.lavendergoons.dndcharacter.Utils;

/**
 * General Utils
 */

public class Utils {
    public static boolean isStringEmpty(String string) {
        return (!string.isEmpty() && !string.trim().isEmpty());
    }

    public static int modValue(int mod) {
        return (mod%2==0)?(mod - 10)/2:(mod - 11) / 2;
    }
}
