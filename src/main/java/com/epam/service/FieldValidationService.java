package com.epam.service;

/**
 * Validation class contains common methods to validate data
 *
 * @author Volodymyr Tytukh
 */

public class FieldValidationService {

    private static final String EMAIL_PATTERN = "^(?=.{3,45}$)[^\\s]+@[^\\s]+\\.[^\\s]+$";
    private static final String PASSWORD_PATTERN = "^[\\w-]{6,20}$";
    private static final String NAME_PATTERN = "^[^\\s$/()]+$";

    public static boolean isMailValid(String mail) {
        return mail != null && mail.matches(EMAIL_PATTERN);
    }

    public static boolean isPasswordValid(String password) {
        return password != null && password.matches(PASSWORD_PATTERN);
    }

    public static boolean isNameValid(String name) {
        return name != null && name.matches(NAME_PATTERN);
    }

}
