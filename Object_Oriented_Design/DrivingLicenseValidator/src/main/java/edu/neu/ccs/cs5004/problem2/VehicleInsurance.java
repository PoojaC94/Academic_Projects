package edu.neu.ccs.cs5004.problem2;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * A class that represents information about the insurance policy of a Vehicle.
 */
class VehicleInsurance {

  private Name vehicleOwner;
  private List<Name> coveredPeople;
  private LocalDate insuranceExpiration;

  /**
   * A constructor for new Vehicle insurance.
   *
   * @param vehicleOwner        the owner of the vehicle to be insured
   * @param coveredPeople       a list of people who are covered under the insurance policy
   * @param insuranceExpiration the expiration date of the insurance policy
   */
  VehicleInsurance(Name vehicleOwner,
      List<Name> coveredPeople, LocalDate insuranceExpiration) {
    this.vehicleOwner = vehicleOwner;
    this.coveredPeople = coveredPeople;
    this.insuranceExpiration = insuranceExpiration;
  }

  /**
   * A method that takes in a name and adds it to the list of people covered under the insurance
   * policy.
   *
   * @param name the name to add to the policy.
   */
  void addToPolicy(Name name) {
    this.coveredPeople.add(name);
  }

  Name getVehicleOwner() {
    return vehicleOwner;
  }

  List<Name> getCoveredPeople() {
    return coveredPeople;
  }

  LocalDate getInsuranceExpiration() {
    return insuranceExpiration;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }
    VehicleInsurance that = (VehicleInsurance) object;
    return Objects.equals(vehicleOwner, that.vehicleOwner)
        && Objects.equals(coveredPeople, that.coveredPeople)
        && Objects.equals(insuranceExpiration, that.insuranceExpiration);
  }

  @Override
  public int hashCode() {
    return Objects.hash(vehicleOwner, coveredPeople, insuranceExpiration);
  }

  @Override
  public String toString() {
    return "VehicleInsurance{"
        + "vehicleOwner=" + vehicleOwner
        + ", coveredPeople=" + coveredPeople
        + ", insuranceExpiration=" + insuranceExpiration
        + '}';
  }
}
