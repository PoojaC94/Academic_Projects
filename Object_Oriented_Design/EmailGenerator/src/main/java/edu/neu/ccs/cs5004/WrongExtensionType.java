package edu.neu.ccs.cs5004;

/**
 * An exception that's throw if the file is of the wrong extension (including if the file doesn't
 * have an extension).
 */
class WrongExtensionType extends Exception {

  WrongExtensionType(String message) {
    super(message);
  }
}
