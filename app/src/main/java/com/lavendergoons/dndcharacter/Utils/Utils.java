package com.lavendergoons.dndcharacter.Utils;

/**
 * General Utils
 */

public class Utils {
    /**
     * If string is empty returns true
     * @param string
     * @return boolean
     */
    public static boolean isStringEmpty(String string) {
        return (string.isEmpty() || string.trim().isEmpty());
    }

    public static boolean isStringArrayEmpty(String[] array) {
        for (String s : array) {
            if (s.isEmpty() || s.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public static int modValue(int mod) {
        return (mod%2==0)?(mod - 10)/2:(mod - 11) / 2;
    }
}
