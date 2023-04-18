package com.epam.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FieldValidationServiceTest {

    @Test
    @DisplayName("isMailValid(): email validating")
    void testIsMailValidCorrectEmailTesting() {
        assertTrue(FieldValidationService.isMailValid("test.mail@mail.com"));
        assertTrue(FieldValidationService.isMailValid("test_mail@mail.com"));
        assertTrue(FieldValidationService.isMailValid("test12mail@mail.com"));
        assertFalse(FieldValidationService.isMailValid("test mail@mail.com"), "Email cannot contain spaces");
    }

    @Test
    @DisplayName("isMailValid(): email length validating")
    void testIsMailValidEmailLengthTesting() {
        assertFalse(FieldValidationService.isMailValid(""),
                "Email is too short – length must be from 3 to 45 symbols");
        assertFalse(FieldValidationService.isMailValid("testmail12345678912345678912345678912@mail.com"),
                "Email is too long – length must be from 3 to 45 symbols");
    }

    @Test
    @DisplayName("isMailValid(): email username and subdomain validating")
    void testIsMailValidIncorrectEmailUsernameOrSubdomainTesting() {
        assertFalse(FieldValidationService.isMailValid("@mail.com"),
                "Email is incorrect – missing username");
        assertFalse(FieldValidationService.isMailValid("test.mail@mail"),
                "Email is incorrect – missing subdomain");
    }

    @Test
    @DisplayName("isPasswordValid(): correct password inputs")
    void testIsPasswordValidCorrectPasswordTesting() {
        assertTrue(FieldValidationService.isPasswordValid("qwerty"));
        assertTrue(FieldValidationService.isPasswordValid("qwerty1234"));
        assertTrue(FieldValidationService.isPasswordValid("qwerty_1234"));
        assertTrue(FieldValidationService.isPasswordValid("qwerty-1234"));
    }

    @Test
    @DisplayName("isPasswordValid(): incorrect password inputs")
    void testIsPasswordValidIncorrectSymbolsTesting() {
        assertFalse(FieldValidationService.isPasswordValid("qwerty 1234"),
                "You can use latin symbols, figures, and hyphen");
        assertFalse(FieldValidationService.isPasswordValid("qwerty()34"),
                "You can use latin symbols, figures, and hyphen");
        assertFalse(FieldValidationService.isPasswordValid("qцerЮy()34"),
                "You can use latin symbols, figures, and hyphen");
    }

    @Test
    @DisplayName("isPasswordValid(): password length testing")
    void testIsPasswordValidPasswordLengthTesting() {
        assertFalse(FieldValidationService.isPasswordValid(""),
                "Password is too short – length must be from 6 to 20 symbols");
        assertFalse(FieldValidationService.isPasswordValid("1234"),
                "Password is too short – length must be from 6 to 20 symbols");
        assertTrue(FieldValidationService.isPasswordValid("123456"),
                "Length must be from 6 to 20 symbols");
        assertFalse(FieldValidationService.isPasswordValid("123456789123456789123"),
                "Password is too long – length must be from 6 to 20 symbols");
    }

    @Test
    @DisplayName("isNameValid(): name length testing")
    void testIsNameValidLengthTesting() {
        assertFalse(FieldValidationService.isNameValid(""),
                "Name is too short – length must be from 2 to 45 symbols");
        assertTrue(FieldValidationService.isPasswordValid("Volodymyr"),
                "Length must be from 2 to 45 symbols");
        assertFalse(FieldValidationService.isNameValid("MyNameIsTooLooooooooooooooooooooooooooooooooong"),
                "Name is too long – length must be from 2 to 45 symbols");
    }

    @Test
    @DisplayName("isMoneyValid(): money values testing")
    void isMoneyValid() {
        assertTrue(FieldValidationService.isMoneyValid("120"));
        assertTrue(FieldValidationService.isMoneyValid("120.00"));
        assertTrue(FieldValidationService.isMoneyValid("120,25"));
        assertFalse(FieldValidationService.isMoneyValid(""),
                "Money should have a whole part");
        assertFalse(FieldValidationService.isMoneyValid(".12"),
                "Money should have a whole part");
        assertFalse(FieldValidationService.isMoneyValid(",12"),
                "Money should have a whole part");
        assertFalse(FieldValidationService.isMoneyValid("-120,12"),
                "Money cannot have a negative value");
        assertFalse(FieldValidationService.isMoneyValid("ьщтун"),
                "You cannot use the Cyrillic alphabet");
        assertFalse(FieldValidationService.isMoneyValid("money"),
                "You cannot use the Latin alphabet");
        assertFalse(FieldValidationService.isMoneyValid("12*0 ?%"),
                "You cannot use special characters");
    }
}