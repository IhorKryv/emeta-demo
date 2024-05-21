package com.emetaplus.admin.utils;

public class ParamProcessorUtils {

    private ParamProcessorUtils() {}

    public static String buildLikeParam(String param) {
        return "%" + param + "%";
    }
}
