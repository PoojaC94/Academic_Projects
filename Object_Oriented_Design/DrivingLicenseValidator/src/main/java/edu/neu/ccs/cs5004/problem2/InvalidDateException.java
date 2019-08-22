package edu.neu.ccs.cs5004.problem2;

/**
 * An exception that's thrown if the provided date is invalid.
 */
class InvalidDateException extends Exception {

  InvalidDateException(String message) {
    super(message);
  }
}
