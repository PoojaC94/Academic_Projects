package edu.neu.ccs.cs5004.problem2;

import java.util.Objects;

/**
 * A class that represents the address of a Driver.
 */
class Address {

  private int buildingNumber;
  private String streetName;
  private String cityName;
  private String stateName;
  private int postalCode;

  /**
   * A constructor for a driver's address.
   *
   * @param buildingNumber the building number, and apartment number, if applicable
   * @param streetName     the street name
   * @param cityName       the city name
   * @param stateName      the state name
   * @param postalCode     the postal (or ZIP) code
   */
  Address(int buildingNumber, String streetName, String cityName, String stateName,
      int postalCode) {
    this.buildingNumber = buildingNumber;
    this.streetName = streetName;
    this.cityName = cityName;
    this.stateName = stateName;
    this.postalCode = postalCode;
  }

  int getBuildingNumber() {
    return buildingNumber;
  }

  String getStreetName() {
    return streetName;
  }

  String getCityName() {
    return cityName;
  }

  String getStateName() {
    return stateName;
  }

  int getPostalCode() {
    return postalCode;
  }

  @Override
  public String toString() {
    return String.format("%d %s, %s, %s, %d", buildingNumber,
        streetName, cityName, stateName, postalCode);
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }
    Address address = (Address) object;
    return buildingNumber == address.buildingNumber
        && postalCode == address.postalCode
        && Objects.equals(streetName, address.streetName)
        && Objects.equals(cityName, address.cityName)
        && Objects.equals(stateName, address.stateName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(buildingNumber, streetName, cityName, stateName, postalCode);
  }
}
