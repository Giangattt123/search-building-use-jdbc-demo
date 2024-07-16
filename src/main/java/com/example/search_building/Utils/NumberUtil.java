package com.example.search_building.Utils;

public class NumberUtil {
    public static boolean isNumber(String value) {
        try{
            Long number = Long.parseLong(value);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

}