package com.example._4_man_fashion.utils;

public class StringCommon {
    private StringCommon() {

    }

    public static boolean isNullOrBlank(String str) {
        return str == null || str.trim().equals("");
    }

    public static String getLikeCondition(String str) {
        if (str == null || str.trim().isEmpty()) {
            str = "%";

            return str;
        }
        String newStr =
                str.trim()
                        .replace("\\", "\\\\")
                        .replace("\\t", "\\\\t")
                        .replace("\\n", "\\\\n")
                        .replace("\\r", "\\\\r")
                        .replace("\\z", "\\\\z")
                        .replace("\\b", "\\\\b")
                        .replace("_", "\\_")
                        .replace("%", "\\%");
        str = "%".concat(newStr.trim().concat("%"));
        return str;
    }
}
