package edu.neu.ccs.cs5004.problem2;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A class that stores all the different types of Non-moving Violations.
 */
public class NonmovingViolation {

  private HashMap<String, LocalDate> listOfViolations;

  /**
   * A constructor for a new Non-moving Violation.
   */
  NonmovingViolation() {
    this.listOfViolations = new HashMap<>();
    this.listOfViolations.put("parking violation", null);
    this.listOfViolations.put("paperwork issues", null);
    this.listOfViolations.put("problems with the vehicle", null);
  }

  /**
   * A method that adds a new Non-moving Violation to the list of possible Non-moving Violations.
   *
   * @param violation       the Non-moving Violation to add
   * @param dateOfViolation the date the Non-moving Violation occurred, or null if it never
   *                        occurred
   */
  void addNonmovingViolation(String violation, LocalDate dateOfViolation) {
    this.listOfViolations.put(violation.toLowerCase(), dateOfViolation);
  }

  /**
   * A method that removes a new Non-moving Violation to the list of possible Non-moving
   * Violations.
   *
   * @param violation the Non-moving Violation to remove
   */
  void removeNonmovingViolation(String violation) {
    this.listOfViolations.remove(violation.toLowerCase());
  }

  HashMap<String, LocalDate> getListOfViolations() {
    return listOfViolations;
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
    NonmovingViolation that = (NonmovingViolation) object;
    return Objects.equals(listOfViolations, that.listOfViolations);
  }

  @Override
  public int hashCode() {
    return Objects.hash(listOfViolations);
  }
}
