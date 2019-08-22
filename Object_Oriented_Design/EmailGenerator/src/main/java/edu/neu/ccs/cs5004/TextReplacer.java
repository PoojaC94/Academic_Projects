package edu.neu.ccs.cs5004;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class that replaces text provided by a template (e.g. email or letter) with information
 * provided in a HashMap of customers' personal information.
 */
class TextReplacer {

  private static final String REPLACE_FIELDS = "\\[\\[(\\w+)]]";
  private static final Pattern PATTERN = Pattern.compile(REPLACE_FIELDS);

  /**
   * Replaces text in a line of a CSV file using regex patterns and a HashMap as a lookup table for
   * each corresponding field (e.g. [[first_name]] gets replaced with an actual first name).
   *
   * Methodology adopted from: https://javamex.com/tutorials/regular_expressions/search_replace_loop.shtml.
   *
   * @param oldText         the old line to find and replace text
   * @param customerHashMap the HashMap used to lookup what new text will replace the old text
   * @return a String containing the same text as the input, but with the fields replaced
   */
  static String replaceText(StringBuilder oldText, HashMap<String, String> customerHashMap) {
    StringBuffer newText = new StringBuffer(oldText.length());  // Allows multithreading
    Matcher field = PATTERN.matcher(oldText);

    while (field.find()) {
      String replacement = customerHashMap.get(field.group(1));  // Lookup value in the HashMap
      field.appendReplacement(newText, Matcher.quoteReplacement(replacement));
    }
    field.appendTail(newText);

    return newText.toString();
  }
}
