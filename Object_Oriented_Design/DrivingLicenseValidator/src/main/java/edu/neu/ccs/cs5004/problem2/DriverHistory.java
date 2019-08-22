package edu.neu.ccs.cs5004.problem2;

import java.util.Objects;

/**
 * A class that represents the history of a Driver.
 */
public class DriverHistory {

  private MovingViolation movingViolations;
  private NonmovingViolation nonmovingViolations;

  /**
   * A constructor for a new Driver History.
   *
   * @param movingViolations    all the Moving Violations the Driver has on his record
   * @param nonmovingViolations all the Non-moving Violations the Driver has on his record
   */
  DriverHistory(MovingViolation movingViolations, NonmovingViolation nonmovingViolations) {
    this.movingViolations = movingViolations;
    this.nonmovingViolations = nonmovingViolations;
  }

  MovingViolation getMovingViolations() {
    return movingViolations;
  }

  NonmovingViolation getNonmovingViolations() {
    return nonmovingViolations;
  }

  @Override
  public String toString() {
    return "DriverHistory{"
        + "movingViolations=" + movingViolations
        + ", nonmovingViolations=" + nonmovingViolations
        + '}';
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }
    DriverHistory that = (DriverHistory) object;
    return Objects.equals(movingViolations, that.movingViolations)
        && Objects.equals(nonmovingViolations, that.nonmovingViolations);
  }

  @Override
  public int hashCode() {
    return Objects.hash(movingViolations, nonmovingViolations);
  }
}
