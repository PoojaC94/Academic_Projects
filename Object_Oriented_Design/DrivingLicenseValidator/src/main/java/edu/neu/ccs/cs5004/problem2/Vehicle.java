package edu.neu.ccs.cs5004.problem2;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

/**
 * A class that represents a vehicle driven by a Driver.
 */
public class Vehicle {

  private String vehicleMake;
  private String vehicleModel;
  private int vehicleYear;
  private Name vehicleOwner;

  /**
   * A constructor for a new vehicle.
   *
   * @param vehicleMake  the make of the vehicle
   * @param vehicleModel the model of the vehicle
   * @param vehicleYear  the year of the vehicle
   * @param vehicleOwner the owner of the vehicle
   */
  Vehicle(String vehicleMake, String vehicleModel, int vehicleYear,
      Name vehicleOwner) {
    this.vehicleMake = vehicleMake;
    this.vehicleModel = vehicleModel;
    this.vehicleYear = vehicleYear;
    this.vehicleOwner = vehicleOwner;
  }

  String getVehicleMake() {
    return vehicleMake;
  }

  String getVehicleModel() {
    return vehicleModel;
  }

  int getVehicleYear() {
    return vehicleYear;
  }

  Name getVehicleOwner() {
    return vehicleOwner;
  }

  private LocalDate intToYear() {
    int defaultMonth = 1;
    int defaultDay = 1;
    return LocalDate.of(vehicleYear, defaultDay, defaultMonth);
  }

  int calculateVehicleAge(LocalDate currentDate) {
    return Period.between(intToYear(), currentDate).getYears();
  }

  @Override
  public String toString() {
    return String.format("The vehicle is a %d %s %s, and is owned by %s.", vehicleYear, vehicleMake,
        vehicleModel,
        vehicleOwner);
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }
    Vehicle vehicle = (Vehicle) object;
    return vehicleYear == vehicle.vehicleYear
        && Objects.equals(vehicleMake, vehicle.vehicleMake)
        && Objects.equals(vehicleModel, vehicle.vehicleModel)
        && Objects.equals(vehicleOwner, vehicle.vehicleOwner);
  }

  @Override
  public int hashCode() {
    return Objects.hash(vehicleMake, vehicleModel, vehicleYear, vehicleOwner);
  }
}
