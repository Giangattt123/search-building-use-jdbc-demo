package com.example.search_building.Utils;

public class StringUtil {
    public static boolean checkString(String data) {
        if (data != null && !data.equals("")) {
            return true;
        }
        return false;
    }
}
