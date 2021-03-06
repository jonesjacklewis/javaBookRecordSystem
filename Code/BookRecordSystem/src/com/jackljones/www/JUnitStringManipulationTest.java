package com.jackljones.www;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class JUnitStringManipulationTest {

    private StringManipulation stringManipulator;
    
    // StringManipulation Test

    @BeforeEach
    public void setUp(){
        stringManipulator = new StringManipulation();
    }

    @Test
    @DisplayName("CSV Column Equals - Should return true")
    public void testcsvColumnEqualsTrue(){
        assertTrue(stringManipulator.csvColumnEquals("1,2,3,4", 0, "1"), "Should be True");
    }

    @Test
    @DisplayName("CSV Column Equals - Should return false as too high a position")
    public void testcsvColumnEqualsFalseTooHigh(){
        assertFalse(stringManipulator.csvColumnEquals("1,2,3,4", 4, "1"), "Should be False");
    }

    @Test
    @DisplayName("CSV Column Equals - Should return false as incorrect")
    public void testcsvColumnEqualsFalseIncorrect(){
        assertFalse(stringManipulator.csvColumnEquals("1,2,3,4", 0, "65"), "Should be False");
    }

    @Test
    @DisplayName("Reverse String - Should return '' as length is 0")
    public void testreverseStringEmptyString(){
        assertEquals("",
                stringManipulator.reverseString(""),
                "Should return empty string");
    }

    @Test
    @DisplayName("Reverse String - Should return the same as the input as length is 1")
    public void testreverseStringLengthOne(){
        assertEquals("a",
                stringManipulator.reverseString("a"),
                "Should return the string 'a'");
    }

    @Test
    @DisplayName("Reverse String - Should return the reverse of the String")
    public void testreverseString(){
        assertEquals("raC",
                stringManipulator.reverseString("Car"),
                "Should return the string 'raC'");
    }






}