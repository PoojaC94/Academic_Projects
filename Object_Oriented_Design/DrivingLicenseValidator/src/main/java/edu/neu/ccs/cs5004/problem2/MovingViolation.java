package edu.neu.ccs.cs5004.problem2;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A class that stores all the different types of moving violations.
 */
public class MovingViolation {

  private static final LocalDate CURRENT_DATE = LocalDate.now();
  private HashMap<String, LocalDate> listOfViolations;

  /**
   * A constructor for a new Moving Violation.
   */
  MovingViolation() {
    this.listOfViolations = new HashMap<>();
    this.listOfViolations.put("distracted driving", null);
    this.listOfViolations.put("reckless driving", null);
    this.listOfViolations.put("speeding", null);
    this.listOfViolations.put("DUI", null);
    this.listOfViolations.put("failure to respect traffic signs", null);
    this.listOfViolations.put("driving with invalid license", null);
    this.listOfViolations.put("driving with invalid insurance", null);
  }

  /**
   * A method that adds a new Moving Violation to the list of possible Moving Violations.
   *
   * @param violation       the Moving Violation to add
   * @param dateOfViolation the date the Moving Violation occurred, or null if it never occurred
   */
  void addMovingViolation(String violation, LocalDate dateOfViolation) {
    this.listOfViolations.put(violation.toLowerCase(), dateOfViolation);
  }

  /**
   * A method that removes a new Moving Violation to the list of possible Moving Violations.
   *
   * @param violation the Moving Violation to remove
   */
  void removeMovingViolation(String violation) {
    this.listOfViolations.remove(violation.toLowerCase());
  }

  HashMap<String, LocalDate> getListOfViolations() {
    return listOfViolations;
  }


  /**
   * A static method which gives the local date value of the most recent key in hash map.
   *
   * @return a local date
   */
  LocalDate getLocalDate() {
    LocalDate newDate = LocalDate.of(1950, 1, 1);
    if (this.listOfViolations.entrySet().iterator().next().getValue() == null) {
      return newDate;
    }
    return this.listOfViolations.entrySet().iterator().next().getValue();
  }

  /**
   * A method which gives the local date of the most recent moving violation.
   *
   * @return local date
   */
  private LocalDate mostRecentViolation() {
    return getLocalDate();
  }

  /**
   * A method that calculates the number of months since the most recent moving violation.
   *
   * @param localDate of the key moving violation
   * @return an integer of the number of months
   */
  int calculateRecentMovingViolation(LocalDate localDate) {
    return Period.between(mostRecentViolation(), CURRENT_DATE).getMonths();
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();

    for (Map.Entry violation : this.listOfViolations.entrySet()) {
      result.append(violation).append("\n");
    }

    return result.toString();
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }
    MovingViolation that = (MovingViolation) object;
    return Objects.equals(listOfViolations, that.listOfViolations);
  }

  @Override
  public int hashCode() {
    return Objects.hash(listOfViolations);
  }
}
