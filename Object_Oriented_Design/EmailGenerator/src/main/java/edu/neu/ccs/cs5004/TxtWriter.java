package edu.neu.ccs.cs5004;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * A class that reads stored text and writes it to a plaintext file.
 */
class TxtWriter {

  /**
   * Writes text to a file.
   *
   * @param textToWrite the text to write to the file
   * @param filePath    the path of the file to write to
   */
  static void writeTxt(String textToWrite, Path filePath) {
    try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
      writer.write(textToWrite);
    } catch (IOException e) {
      System.err.format("Can't open file for writing: %s\n", filePath);
    }
  }
}
