// Package declaration
package com.jackljones.www;

// Imports
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

// This class contains methods for performing operations on files
public class FileHandling {

    // Instance Variables
    private static String filename;
    private static File file;
    private static final StringManipulation stringManipulator = new StringManipulation();
    private static final Validation validator = new Validation();

    // Constructor
    public FileHandling(String filename) {

        // Sets File name
        FileHandling.filename = filename;

        file = new File(filename);

        // Check if File Exists
        if (!file.exists()) {
            try {
                // Try to create file
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Unable to create file.");
                e.printStackTrace();
            }
        }

    }

    // Getter and Setter
    public static String getFilename() {
        return filename;
    }

    public static void setFilename(String filename) {
        FileHandling.filename = filename;
    }

    // Appends a line to a file
    public void writeLineToFile(String content) {

        // Creates StringBuilder with all file content
        StringBuilder toWrite = new StringBuilder(readAllFile());

        // Add the additional line to StringBuilder object
        toWrite.append(content);

        try {
            FileWriter fileWriter = new FileWriter(file);
            // Writes content to file
            fileWriter.write(toWrite.toString());
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Unable To Write To File = " + filename);
            e.printStackTrace();
        }

    }

    // Writes content to a file - discarding the original content
    public void overwriteFile(String content) {


        try {
            // Writes Content to file
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Unable to write to " + filename);
            e.printStackTrace();
        }


    }

    // Reads all the lines of the file
    public String readAllFile() {

        // default value
        String content = "";

        try {

            // Scanner for file contents
            Scanner scanner = new Scanner(file);

            StringBuilder sb = new StringBuilder();

            while (scanner.hasNextLine()) {

                // add line and new line character to sb object
                sb.append(scanner.nextLine());
                sb.append("\n");

            }

            scanner.close();
            content = sb.toString();

        } catch (FileNotFoundException e) {
            System.out.println("Unable to find file: " + filename);
            e.printStackTrace();
        }


        return content;
    }

    // Deletes a line from the file - based on the id
    public void deleteRecord(String id) {
        String fileContents = readAllFile();
        String[] fileContentsArray = fileContents.split("\n");

        // An array list for the new file content
        ArrayList<String> editedFileContents = new ArrayList<>();

        for (String line : fileContentsArray) {

            // if the ID of the line isn't the ID of the item to be deleted
            if (!stringManipulator.csvColumnEquals(line, 0, id)) {
                // Add the line to the array list
                editedFileContents.add(line);
            }
        }

        Object[] objectArray = editedFileContents.toArray();

        String[] editedFileContentsArray = new String[objectArray.length];

        int counter = 0;

        for (Object obj : objectArray) {
            editedFileContentsArray[counter] = obj.toString();
            counter++;
        }


        String editedFileContentsString = String.join("\n", editedFileContentsArray);
        // Overwrite file contents
        overwriteFile(editedFileContentsString);


    }


    // Edits a line of the file - based on the ID
    public void editRecord(String id, String newContent) {
        String fileContents = readAllFile();
        String[] fileContentsArray = fileContents.split("\n");


        // An array list for the new file content
        ArrayList<String> editedFileContents = new ArrayList<>();

        for (String line : fileContentsArray) {
            // if the ID of the line is the ID of the item to be deleted
            if (stringManipulator.csvColumnEquals(line, 0, id)) {
                // Add the new content
                editedFileContents.add(newContent);
            } else {
                // Add the line
                editedFileContents.add(line);
            }
        }

        Object[] objectArray = editedFileContents.toArray();

        String[] editedFileContentsArray = new String[objectArray.length];

        int counter = 0;

        for (Object obj : objectArray) {
            editedFileContentsArray[counter] = obj.toString();
            counter++;
        }


        String editedFileContentsString = String.join("\n", editedFileContentsArray);
        // Overwrite file contents
        overwriteFile(editedFileContentsString);


    }


    // Checks if a potential entry would be a duplicate
    public boolean isDuplicate(String potentialEntry, boolean editing) {

        String fileContents = readAllFile();
        String[] fileContentsAsArray = fileContents.split("\n");

        String[] potentialEntryArray = potentialEntry.split(",");

        boolean matchingId, matchingISBN, matchingTitle;

        // When adding a record
        if (!editing) {
            for (String line : fileContentsAsArray) {

                String[] lineArray = line.split(",");

                // Check if ID, ISBN or Title is equal
                matchingId = lineArray[0].equals(potentialEntryArray[0]);
                matchingISBN = lineArray[1].equals(potentialEntryArray[1]);
                matchingTitle = lineArray[2].equals(potentialEntryArray[2]);

                if (matchingId || matchingISBN || matchingTitle) {
                    return true;
                }


            }

        // When editing a record (needs to be greater than 1 otherwise would include the original entry)
        } else {

            Map<String, Integer> toCheck = new HashMap<>();
            toCheck.put(potentialEntryArray[0], 0);
            toCheck.put(potentialEntryArray[1], 0);
            toCheck.put(potentialEntryArray[2], 0);

            for (String line : fileContentsAsArray) {

                String[] lineArray = line.split(",");

                if (Arrays.asList(lineArray).contains(potentialEntryArray[0])) {
                    toCheck.put(potentialEntryArray[0], toCheck.get(potentialEntryArray[0]) + 1);
                }

                if (Arrays.asList(lineArray).contains(potentialEntryArray[1])) {
                    toCheck.put(potentialEntryArray[1], toCheck.get(potentialEntryArray[1]) + 1);
                }

                if (Arrays.asList(lineArray).contains(potentialEntryArray[2])) {
                    toCheck.put(potentialEntryArray[2], toCheck.get(potentialEntryArray[2]) + 1);
                }

            }

            return toCheck.get(potentialEntryArray[0]) > 1 || toCheck.get(potentialEntryArray[1]) > 1 ||
                    toCheck.get(potentialEntryArray[2]) > 1;

        }
        return false;

    }


    // Gets the next ID
    public String getNextId() {

        String fileContents = readAllFile();
        String[] fileContentsArray = fileContents.split("\n");

        // Gets last line in file
        String lastLine = fileContentsArray[fileContentsArray.length - 1];
        // Gets index one (the ID)
        String idSection = lastLine.split(",")[0];

        String nextId;

        // If it is an integer
        if (validator.stringIsInteger(idSection)) {
            // Add one to the value and have that as the next ID
            nextId = String.valueOf(Integer.parseInt(idSection) + 1);
        } else {
            // Have the next ID be 1
            nextId = "1";
        }


        return nextId;

    }

    // Searches the file for specific details
    public ArrayList<String> searchFile(String[] details) {

        // number of fields which need to be matched to be a valid search result
        int needed = 0;

        for(String item: details){
            // if the item is not an empty string (i.e. nothing has been searched)
            if(item.length() > 0){
                // increment needed
                needed ++;
            }
        }


        String[] toSearch = readAllFile().split("\n");

        ArrayList<String> toReturn = new ArrayList<>();

        // if no fields are specified
        if(needed == 0){
            return toReturn;
        }

        int matched;

        for (String line : toSearch) {
            matched = 0;
            String[] lineArray = line.split(",");

            // if the fields match
            if((lineArray[0].toLowerCase().contains(details[0].toLowerCase())) && details[0].length() > 0){
                // increment matched
                matched++;
            }  // id

            if((lineArray[1].toLowerCase().contains(details[1].toLowerCase())) && details[1].length() > 0){
                matched++;
            }  // isbn

            if((lineArray[2].toLowerCase().contains(details[2].toLowerCase())) && details[2].length() > 0){
                matched++;
            }  // title

            if((lineArray[3].toLowerCase().contains(details[3].toLowerCase())) && details[3].length() > 0){
                matched++;
            }  // author

            if((lineArray[4].toLowerCase().contains(details[4].toLowerCase())) && details[4].length() > 0){
                matched++;
            }  // pages

            if((lineArray[5].toLowerCase().contains(details[5].toLowerCase())) && details[5].length() > 0){
                matched++;
            }  // genre

            if((lineArray[6].toLowerCase().contains(details[6].toLowerCase())) && details[6].length() > 0){
                matched++;
            }  // review

            // if the number of matches is equal to whats needed
            if(matched == needed){
                // add to ArrayList
                toReturn.add(line);
            }



        }

        return toReturn;
    }


    // Counts the occurrences of a specific field (e.g. how many of each genre)
    public Map<String, Integer> countOccurrences(int field) {

        // Creates a hashmap
        Map<String, Integer> counter = new HashMap<>();
        String[] toSearch = readAllFile().split("\n");

        for (String line : toSearch) {
            String[] lineArray = line.split(",");
            String item = lineArray[field];

            // If the item isn't in the map
            if (!counter.containsKey(item)) {
                counter.put(item, 1);
            } else {
                counter.put(item, counter.get(item) + 1);
            }

        }

        return counter;

    }

    // Gets two integer fields
    public Map<Integer, Integer> getTwoIntFields(int field1, int field2) {

        Map<Integer, Integer> counter = new HashMap<>();
        String[] toSearch = readAllFile().split("\n");

        for (String line : toSearch) {
            String[] lineArray = line.split(",");
            Integer item = Integer.valueOf(lineArray[field1]);

            // If the item isn't in the map
            if (!counter.containsKey(item)) {
                counter.put(item, Integer.valueOf(lineArray[field2]));
            }

        }

        return counter;

    }

    // Get the Mode of type String
    public String getStringMode(int field){

        Map<String, Integer> counter = new HashMap<>();
        String[] toSearch = readAllFile().split("\n");

        for(String line: toSearch){

            String[] lineArray = line.split(",");
            String item = lineArray[field];

            // If the item isn't in the map
            if (!counter.containsKey(item)) {
                counter.put(item, 1);
            }else{
                counter.put(item, counter.get(item) + 1);
            }

        }

        int maximum = -100;

        StringBuilder mode = new StringBuilder();
        int value;

        for(String key: counter.keySet()){
            value = counter.get(key);

            // If the value is greater than the current mode
            if(value > maximum){
                // Replace the mode e.g. Horror:2
                mode = new StringBuilder(key + ":" + value);
                maximum = value;
            }else if(value == maximum){
                // Append it
                mode.append("\n");
                mode.append(key);
                mode.append(":");
                mode.append(value);
            }

        }

        return mode.toString();
    }


    // Get the mean as an double
    public double getDoubleMean(int field){

        ArrayList<Integer> numbers = new ArrayList<>();

        String[] toSearch = readAllFile().split("\n");

        for (String line : toSearch) {
            String[] lineArray = line.split(",");
            // Number form of item at specific index
            numbers.add(Integer.valueOf(lineArray[field]));
        }

        int sum = 0;
        int count = 0;

        for(int num: numbers){
            sum += num;
            count++;
        }

        if(count == 0){
            return 0;
        }else{
            double mean = (double) sum / (double) count;

            // Round to 2 decimal places
            mean = Math.round(mean * 100.0) / 100.0;
            return mean;
        }
    }

    // Get the median as a double
    public double getDoubleMedian(int field){

        ArrayList<Integer> numbers = new ArrayList<>();

        String[] toSearch = readAllFile().split("\n");

        for(String line: toSearch){
            String[] lineArray = line.split(",");
            numbers.add(Integer.valueOf(lineArray[field]));
        }

        // If the length of the list is odd
        if(numbers.size() % 2 == 1){

            // Get the middle index e.g. 7 --> 3
            int medianIndex = (numbers.size() - 1) / 2;
            return numbers.get(medianIndex);

        // For even
        }else{
            // Get the two middle
            int medianMaxIndex = (numbers.size() - 1) / 2;
            int medianMinIndex = medianMaxIndex - 1;

            // Calculate the average
            int medianMax = numbers.get(medianMaxIndex);
            int medianMin = numbers.get(medianMinIndex);
            double medianSum = medianMax + medianMin;

            return medianSum / 2.0;

        }

    }

    // Get the range as an int
    public int getIntRange(int field){

        ArrayList<Integer> numbers = new ArrayList<>();

        String[] toSearch = readAllFile().split("\n");

        for(String line: toSearch){
            String[] lineArray = line.split(",");
            numbers.add(Integer.valueOf(lineArray[field]));
        }

        // If there's only one item return the first item
        if(numbers.size() == 1){
            return numbers.get(0);
        }else{
            int[] numberArray = new int[numbers.size()];
            int index = 0;

            for(Object obj: numbers.toArray()){
                numberArray[index] = (int) obj;
                index++;
            }

            // Sort array
            Arrays.sort(numberArray);

            int maxVal = numberArray[numberArray.length - 1];
            int minVal = numberArray[0];

            return  maxVal - minVal;

        }

    }


}
