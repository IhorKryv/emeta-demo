package com.emetaplus.admin.utils;

public class ValidationRules {

    private ValidationRules() {}

    public static final int USER_NAME_LENGTH = 6;
    public static final int PASSWORD_LENGTH = 8;
    public static final String USER_NAME_REGEXP = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
    public static final String PASSWORD_REGEXP = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";
}
