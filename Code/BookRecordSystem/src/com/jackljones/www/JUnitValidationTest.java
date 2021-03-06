package com.jackljones.www;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Validation Test

class JUnitValidationTest {

    private Validation validator;

    @BeforeEach
    public void setUp(){
        validator = new Validation();
    }

    @Test
    @DisplayName("String is Integer - Should return true as -128 is a negative integer")
    public void teststringIsIntegerNegativeInteger(){
        assertTrue(validator.stringIsInteger("-128"), "Should be True as negative integer");
    }

    @Test
    @DisplayName("String is Integer - Should return true as 0")
    public void teststringIsIntegerZero(){
        assertTrue(validator.stringIsInteger("0"), "Should be True as 0");
    }

    @Test
    @DisplayName("String is Integer - Should return true as 53835435453 is positive integer")
    public void teststringIsIntegerPositiveInteger(){
        assertTrue(validator.stringIsInteger("53835435453"), "Should be True as 53835435453 is positive integer");
    }

    @Test
    @DisplayName("String is Integer - Should return false as -128.56 is negative decimal")
    public void teststringIsIntegerNegativeDecimal(){
        assertFalse(validator.stringIsInteger("-128.56"), "Should be False as -128.56 is negative decimal");
    }

    @Test
    @DisplayName("String is Integer - Should return false as 53835435453.34 is positive decimal")
    public void teststringIsIntegerPositiveDecimal(){
        assertFalse(validator.stringIsInteger("53835435453.34"), "Should be False as 53835435453.34 is positive decimal");
    }

    @Test
    @DisplayName("String is Integer - Should return false as hello is just letter characters")
    public void teststringIsIntegerJustLetters(){
        assertFalse(validator.stringIsInteger("hello"), "Should be False as hello is just letter characters");
    }

    @Test
    @DisplayName("String is Integer - Should return false as 123abc456 is mixture of letter characters and numbers")
    public void teststringIsIntegerMixtureLettersAndNumbers(){
        assertFalse(validator.stringIsInteger("123abc456"), "Should be False as 123abc456 is mixture of letter characters and numbers");
    }

    @Test
    @DisplayName("Valid ISBN - Should return true as 0747532699 is a valid 10 digit ISBN")
    public void testvalidIsbnValid10Digit(){
        assertTrue(validator.validIsbn("0747532699"), "Should be True as 0747532699 is a valid 10 digit ISBN");
    }

    @Test
    @DisplayName("Valid ISBN - Should return true as 9780747532699 is a valid 13 digit ISBN")
    public void testvalidIsbnValid13Digit(){
        assertTrue(validator.validIsbn("9780747532699"), "Should be True as 9780747532699 is a valid 13 digit ISBN");
    }

    @Test
    @DisplayName("Valid ISBN - Should return false as 0747532697 is an invalid 10 digit ISBN")
    public void testvalidIsbnTenDigitInvalid10Digit(){
        assertFalse(validator.validIsbn("0747532697"), "Should be False as 0747532697 is an invalid 10 digit ISBN");
    }

    @Test
    @DisplayName("Valid ISBN - Should return false as 9780747532697 is an invalid 13 digit ISBN")
    public void testvalidIsbnInvalid13Digit(){
        assertFalse(validator.validIsbn("9780747532697"), "Should be False as 9780747532697 is an invalid 13 digit ISBN");
    }

    @Test
    @DisplayName("Valid ISBN - Should return false as 012345 is too short")
    public void testvalidIsbnInvalidTooShort(){
        assertFalse(validator.validIsbn("012345"), "Should be False as 012345 is too short");
    }

    @Test
    @DisplayName("Valid ISBN - Should return false as 9780747532697765 is too long")
    public void testvalidIsbnInvalidTooLong(){
        assertFalse(validator.validIsbn("9780747532697765"), "Should be False as 9780747532697765 is too long");
    }

    @Test
    @DisplayName("Valid ISBN - Should return false as HelloHello is not all numbers")
    public void testvalidIsbnInvalidWrongCharacters(){
        assertFalse(validator.validIsbn("HelloHello"), "Should be False as HelloHello is not all numbers");
    }

    @Test
    @DisplayName("Valid String Length - Should return true as Hello is between 1 and 10 characters")
    public void testvalidStringLengthValidLength(){
        assertTrue(validator.validStringLength("Hello", 1, 10), "Should return true as Hello is between 1 and 10 characters");
    }

    @Test
    @DisplayName("Valid String Length - Should return false as Hello is not between 6 and 10 characters")
    public void testvalidStringLengthInvalidLengthShort(){
        assertFalse(validator.validStringLength("Hello", 6, 10), "Should return false as Hello is not between 6 and 10 characters");
    }

    @Test
    @DisplayName("Valid String Length - Should return false as Hello is not between 1 and 4 characters")
    public void testvalidStringLengthInvalidLengthLong(){
        assertFalse(validator.validStringLength("Hello", 1, 4), "Should return false as Hello is not between 1 and 4 characters");
    }

    @Test
    @DisplayName("Valid Author - Should return false as A is too short to be an authors name")
    public void testvalidAuthorTooShort(){
        assertFalse(validator.validAuthor("A"), "Should return false as A is too short to be an authors name");
    }

    @Test
    @DisplayName("Valid Author - Should return false as too long")
    public void testvalidAuthorTooLong(){
        String tooLong = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

        assertFalse(validator.validAuthor(tooLong), "Should return false as too long");
    }

    @Test
    @DisplayName("Valid Author - Should return false as contains invalid characters")
    public void testvalidAuthorDisallowedCharacter(){
        assertFalse(validator.validAuthor("Henry 7th"), "Should return false as contains invalid characters");
    }

    @Test
    @DisplayName("Valid Author - Should return true")
    public void testvalidAuthorValidAuthor(){
        assertTrue(validator.validAuthor("J.K. Rowling"), "Should return true");
    }

    @Test
    @DisplayName("Valid Pages - Should return false as not an integer value")
    public void testvalidPagesNotInteger(){
        assertFalse(validator.validPages("six-hundred"), "Should return false as not an integer value");
    }

    @Test
    @DisplayName("Valid Pages - Should return false as too high a value")
    public void testvalidPagesTooHigh(){
        assertFalse(validator.validPages("35768"), "Should return false as too high a value");
    }

    @Test
    @DisplayName("Valid Pages - Should return true")
    public void testvalidPagesValidPages(){
        assertTrue(validator.validPages("872"), "Should return true");
    }

}