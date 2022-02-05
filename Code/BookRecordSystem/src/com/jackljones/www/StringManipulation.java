// Package declaration
package com.jackljones.www;

// This class is used to group methods which manipulate strings
public class StringManipulation {

    // Blank constructor
    public StringManipulation() {

    }

    public boolean csvColumnEquals(String line, int index, String value) {
        String[] values = line.split(",");

        // if the index is too large default to false
        if (values.length - 1 < index) {
            return false;
        }

        return values[index].equals(value);

    }

    public String reverseString(String toReverse) {

        String toReturn;

        // if empty string
        if (toReverse.length() == 0) {
            // return empty string
            return "";
        // if the length is one
        } else if(toReverse.length() == 1){
            // return toReverse as it will be the same either way
            return toReverse;
        } else{


            char[] toReverseAsArray = toReverse.toCharArray();

            StringBuilder sb = new StringBuilder();

            // loop backwards through array
            for (int i = toReverseAsArray.length - 1; i >= 0; i--) {
                sb.append(toReverseAsArray[i]);
            }

            toReturn = sb.toString();

        }

        return toReturn;

    }


}
