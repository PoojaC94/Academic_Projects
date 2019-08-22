package edu.neu.ccs.cs5004.supermarket;

/**
 * An exception that's thrown if the customer tries to buy more items than are available.
 */
class ExceedsQuantityException extends Exception {

  ExceedsQuantityException() {
    System.err.println();
  }
}
