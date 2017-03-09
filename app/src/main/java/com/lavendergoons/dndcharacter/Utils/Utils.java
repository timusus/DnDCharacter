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

    /**
     * If string is empty returns true
     * @param array
     * @return
     */
    public static boolean isStringArrayEmpty(String[] array) {
        for (String s : array) {
            if (s.isEmpty() || s.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Computes mod value
     * @param mod
     * @return
     */
    public static int modValue(int mod) {
        return (mod%2==0)?(mod - 10)/2:(mod - 11) / 2;
    }
}
