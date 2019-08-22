package edu.neu.ccs.cs5004.problem2;

import java.util.Objects;

/**
 * A class that represents a Vehicle's history.
 */
public class VehicleHistory {

  private MovingViolation movingViolation;
  private Crash crash;

  /**
   * A constructor for a new Vehicle History.
   *
   * @param movingViolation any Moving Violations associated with the vehicle
   * @param crash           any Crashes associated with the vehicle
   */
  VehicleHistory(MovingViolation movingViolation, Crash crash) {
    this.movingViolation = movingViolation;
    this.crash = crash;
  }

  MovingViolation getMovingViolation() {
    return movingViolation;
  }

  Crash getCrash() {
    return crash;
  }


  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }
    VehicleHistory that = (VehicleHistory) object;
    return Objects.equals(movingViolation, that.movingViolation)
        && Objects.equals(crash, that.crash);
  }

  @Override
  public int hashCode() {
    return Objects.hash(movingViolation, crash);
  }

  @Override
  public String toString() {
    return "VehicleHistory{"
        + "movingViolation=" + movingViolation
        + ", crash=" + crash
        + '}';
  }
}
