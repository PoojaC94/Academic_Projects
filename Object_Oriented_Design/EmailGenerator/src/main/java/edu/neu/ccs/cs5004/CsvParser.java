package edu.neu.ccs.cs5004;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * A class that's responsible for extracting contents from a CSV file.
 */
class CsvParser {

  private static final String DELIMITER = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";

  /**
   * A method that takes in the complete path to a file, opens the file for reading, then parses it.
   * It creates a HashMap of each customer's information, then stores this is a single list.
   *
   * @param filePath the path to the file to open
   */
  static List<HashMap<String, String>> readCsv(String filePath) {

    List<HashMap<String, String>> customerList = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
      String line;
      HashMap<String, String> customerRoster;

      String firstLine = br.readLine();  // Store the column headers of the CSV file.
      HashMap<Integer, String> fieldMap = mapHeader(firstLine);  // Create a map of column headers.

      while ((line = br.readLine()) != null) {
        // Make a HashMap of each customer's information (e.g. first_name, last_name, etc.).
        customerRoster = createRoster(fieldMap, parseLine(line));

        // Make a single list of each customer's HashMap.
        customerList.add(customerRoster);
      }
    } catch (IOException e) {
      System.err.format("Can't open file: %s", filePath);
    }
    return customerList;
  }

  /**
   * Splits the CSV column headers on commas, then maps each column header to its position (index).
   *
   * @param firstLine the line to split and map
   * @return a HashMap of the fields and their corresponding index numbers
   */
  private static HashMap<Integer, String> mapHeader(String firstLine) {
    HashMap<Integer, String> headerMap;

    /*
     * Remove the quotation marks from the column headers of the CSV file, split on commas,
     * then add each column header to a list.
     */
    List<String> headerList = new ArrayList<>(Arrays.asList(
        firstLine.replace("\"", "").split(",")));

    // Create a HashMap where <Key, Value> = <index, column header>.
    HashMap<Integer, String> map = new HashMap<>();
    for (String columnHeader : headerList) {
      map.put(headerList.indexOf(columnHeader), columnHeader);
    }
    headerMap = map;
    return headerMap;
  }

  /**
   * Splits a line of a CSV file into distinct fields, then adds each field into a list.
   *
   * @param line the line to split into substrings
   * @return a list of information about people
   */
  private static List<String> parseLine(String line) {
    List<String> peopleData;
    String[] fieldArray = line.split(DELIMITER);

    // Remove the quotation marks from the fields of the CSV file.
    List<String> list = new ArrayList<>();
    for (String field : fieldArray) {
      String fieldNoQuotes = field.replace("\"", "");
      list.add(fieldNoQuotes);
    }
    peopleData = list;

    return peopleData;
  }

  /**
   * Uses a HashMap to lookup indices and match the substrings from a list of customers to their
   * appropriate fields.
   *
   * For example, if index 0 maps to "first_name" in the HashMap, then index 0 for a given
   * customer's list of information will be associated with the "first_name" field.
   *
   * @param fieldList    the HashMap to use as a lookup table
   * @param customerList the list of customers
   * @return a new HashMap where the key is the field and the value is the customer information
   */
  private static HashMap<String, String> createRoster(HashMap<Integer, String> fieldList,
      List<String> customerList) {
    HashMap<String, String> roster = new HashMap<>();

    for (int i = 0; i < customerList.size(); i++) {
      roster.put(fieldList.get(i), customerList.get(i));
    }
    return roster;
  }
}
