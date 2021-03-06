package com.jackljones.www;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

// Tests Book Class

class JUnitBookTest {

    private Book book;

    @BeforeEach
    public void setUp(){
        book = new Book("1", "1234567890", "The Book",
                "John Smith", "123", "Generic", "It's a book");
    }

    
    // Test for Getters
    
    @Test
    @DisplayName("Getter for Id should be equal")
    public void testGetEqualId(){
        assertEquals("1", book.getId(), "Getter for id should be equal");
    }

    @Test
    @DisplayName("Getter for Id should not be equal")
    public void testGetUnequalId(){
        assertNotEquals("2", book.getId(), "Getter for id should not be equal");
    }

    @Test
    @DisplayName("Getter for ISBN should be equal")
    public void testGetEqualISBN(){
        assertEquals("1234567890", book.getIsbn(), "Getter for isbn should be equal");
    }

    @Test
    @DisplayName("Getter for ISBN should not be equal")
    public void testGetUnequalISBN(){
        assertNotEquals("9234567890", book.getIsbn(), "Getter for isbn should not be equal");
    }

    @Test
    @DisplayName("Getter for Title should be equal")
    public void testGetEqualTitle(){
        assertEquals("The Book", book.getTitle(), "Getter for Title should be equal");
    }

    @Test
    @DisplayName("Getter for Title should not be equal")
    public void testGetUnequalTitle(){
        assertNotEquals("Not The Book", book.getTitle(), "Getter for Title should not be equal");
    }

    @Test
    @DisplayName("Getter for Author should be equal")
    public void testGetEqualAuthor(){
        assertEquals("John Smith", book.getAuthor(), "Getter for Author should be equal");
    }

    @Test
    @DisplayName("Getter for Author should not be equal")
    public void testGetUnequalAuthor(){
        assertNotEquals("John Doe", book.getAuthor(), "Getter for Author should not be equal");
    }
    
    @Test
    @DisplayName("Getter for Pages should be equal")
    public void testGetEqualPages(){
        assertEquals("123", book.getPages(), "Getter for Pages should be equal");
    }

    @Test
    @DisplayName("Getter for Pages should not be equal")
    public void testGetUnequalPages(){
        assertNotEquals("456", book.getPages(), "Getter for Pages should not be equal");
    }

    @Test
    @DisplayName("Getter for Genre should be equal")
    public void testGetEqualGenre(){
        assertEquals("Generic", book.getGenre(), "Getter for Genre should be equal");
    }

    @Test
    @DisplayName("Getter for Genre should not be equal")
    public void testGetUnequalGenre(){
        assertNotEquals("Specific", book.getGenre(), "Getter for Genre should not be equal");
    }

    @Test
    @DisplayName("Getter for Review should be equal")
    public void testGetEqualReview(){
        assertEquals("It's a book", book.getReview(), "Getter for Review should be equal");
    }

    @Test
    @DisplayName("Getter for Review should not be equal")
    public void testGetUnequalReview(){
        assertNotEquals("it is a good book!", book.getReview(), "Getter for Review should not " +
                "be equal");
    }
    
    // Tests for Setters

    @Test
    @DisplayName("Setter for ID should be equal")
    public void testSetID(){
        book.setId("5");
        assertEquals("5", book.getId(), "Id should have been set correctly");
    }

    @Test
    @DisplayName("Setter for ISBN should be equal")
    public void testSetIsbn(){
        book.setIsbn("2345678901");
        assertEquals("2345678901", book.getIsbn(), "ISBN should have been set correctly");
    }

    @Test
    @DisplayName("Setter for Title should be equal")
    public void testSetTitle(){
        book.setTitle("Another Book");
        assertEquals("Another Book", book.getTitle(), "Title should have been set correctly");
    }

    @Test
    @DisplayName("Setter for Author should be equal")
    public void testSetAuthor(){
        book.setAuthor("Jane Doe");
        assertEquals("Jane Doe", book.getAuthor(), "Author should have been set correctly");
    }

    @Test
    @DisplayName("Setter for Pages should be equal")
    public void testSetPages(){
        book.setPages("1000");
        assertEquals("1000", book.getPages(), "Pages should have been set correctly");
    }

    @Test
    @DisplayName("Setter for Genre should be equal")
    public void testSetGenre(){
        book.setGenre("Horror");
        assertEquals("Horror", book.getGenre(), "Genre should have been set correctly");
    }

    @Test
    @DisplayName("Setter for Review should be equal")
    public void testSetReview(){
        book.setReview("It is fun!");
        assertEquals("It is fun!", book.getReview(), "Review should have been set correctly");
    }




}