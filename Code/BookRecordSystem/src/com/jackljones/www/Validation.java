// Package Declaration
package com.jackljones.www;

// This class is used to group all methods relating to validation
public class Validation {

    private final StringManipulation stringManipulator = new StringManipulation();

    public boolean stringIsInteger(String value) {

        boolean toReturn;


        // Regex matches positive and negative digits
        String pattern = "-?\\d+";
        toReturn = value.matches(pattern);

        return toReturn;
    }

    public boolean validIsbn(String isbn) {

        boolean toReturn = false;

        // if the length is either 10 or 13 digits and the string is an integer
        if ((isbn.length() == 10 || isbn.length() == 13) && stringIsInteger(isbn)) {

            if (isbn.length() == 10) {
                // Use 10 digit function
                toReturn = validIsbnTenDigit(isbn);
            }

            if (isbn.length() == 13) {
                // Use 13 digit function
                toReturn = validIsbnThirteenDigit(isbn);
            }

        }


        return toReturn;

    }

    public boolean validIsbnTenDigit(String isbn) {

        // https://www.instructables.com/How-to-verify-a-ISBN/

        int checkSum = 0;

        // Reverse ISBN
        String reversedIsbn = stringManipulator.reverseString(isbn);

        for (int i = 9; i >= 0; i--) {
            checkSum += Character.getNumericValue(reversedIsbn.charAt(i)) * (i + 1);
        }

        return checkSum % 11 == 0;

    }

    public boolean validIsbnThirteenDigit(String isbn) {

        // https://www.instructables.com/How-to-verify-a-ISBN/

        int checkSum = 0;
        boolean timesOne = true;

        for (char digit : isbn.toCharArray()) {

            if (timesOne) {
                checkSum += Character.getNumericValue(digit);
            } else {
                checkSum += Character.getNumericValue(digit) * 3;
            }

            timesOne = !timesOne;

        }

        return checkSum % 10 == 0;
    }

    public boolean validStringLength(String toCheck, int minLength, int maxLength) {
        // Check between min and max length

        return toCheck.length() >= minLength && toCheck.length() <= maxLength;
    }

    public boolean validTitle(String title) {

        return validStringLength(title, 0, 256);
    }

    public boolean validAuthor(String author) {

        boolean toReturn = validStringLength(author, 5, 256);

        String regEx = "^[.a-zA-Z\\- ]*$"; // Matches upper and lowercase, full stops, hyphens and spaces

        boolean matched = author.matches(regEx);

        toReturn = toReturn && matched;

        return toReturn;
    }

    public boolean validPages(String pages) {

        boolean toReturn = false;

        if (stringIsInteger(pages)) {

            int numberPages = Integer.parseInt(pages);

            if (numberPages > 0 && numberPages < 32_768) {
                toReturn = true;
            }

        }

        return toReturn;

    }

}
