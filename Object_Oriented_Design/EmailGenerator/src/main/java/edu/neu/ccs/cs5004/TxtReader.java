package edu.neu.ccs.cs5004;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * A class that reads a plaintext template file (e.g. for an email or letter) and stores its
 * contents.
 */
class TxtReader {

  /**
   * Reads in a .txt file and stores its contents in a buffer.
   *
   * @param filePath the path to the file to read
   * @return a new StringBuilder object containing the original, unmodified text
   */

  static StringBuilder readTxt(Path filePath) {
    String oldLine;
    StringBuilder originalText = new StringBuilder();

    try (BufferedReader reader = Files.newBufferedReader(filePath)) {

      while ((oldLine = reader.readLine()) != null) {
        originalText.append(String.format("%s\n", oldLine));  // Build up the original text
      }
    } catch (IOException e) {
      System.err.format("Can't open file for reading: %s\n", filePath);
    }
    return originalText;
  }
}
