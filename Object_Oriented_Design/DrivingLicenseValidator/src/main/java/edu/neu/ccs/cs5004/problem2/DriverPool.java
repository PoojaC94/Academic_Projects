package edu.neu.ccs.cs5004.problem2;

import java.util.HashSet;

/**
 * A class that represents a pool of drivers for a ride sharing service.
 */
class DriverPool {

  private HashSet<Driver> acceptedDrivers;
  private Validator validator;

  /**
   * A constructor for a pool of Drivers.
   *
   * @param validator the Validator to use to check
   */
  DriverPool(Validator validator) {
    this.acceptedDrivers = new HashSet<>();
    this.validator = validator;
  }

  /**
   * Add a driver to the pool of accepted Drivers.
   *
   * @param driver the Driver to add
   */
  void addDriver(Driver driver) {
    if (this.validator.validateAll()) {
      this.acceptedDrivers.add(driver);
    } else {
      System.out.println("Cannot add driver at this time.");
    }
  }

  HashSet<Driver> getAcceptedDrivers() {
    return acceptedDrivers;
  }
}
