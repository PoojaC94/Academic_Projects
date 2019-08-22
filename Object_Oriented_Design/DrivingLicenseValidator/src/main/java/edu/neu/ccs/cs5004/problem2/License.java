package edu.neu.ccs.cs5004.problem2;

import java.time.LocalDate;
import java.time.Period;
import java.util.Locale;
import java.util.Objects;

/**
 * A class that represents the license of a Driver.
 */
class License {

  private static final LocalDate CURRENT_DATE = LocalDate.now();
  private Name driverName;
  private Address driverAddress;
  private int licenseNumber;
  private LocalDate birthdate;
  private Locale country;
  private LocalDate issueDate;
  private LocalDate expirationDate;


  /**
   * A constructor for a new driver's license object.
   *
   * @param driverName     the name of the driver
   * @param driverAddress  the address of the driver
   * @param licenseNumber  the number of the license
   * @param birthdate      the birthdate (day, month, year) of the driver
   * @param country        the country the license was issued
   * @param issueDate      the date the license was issued
   * @param expirationDate the date the license will expire
   * @throws InvalidDateException if the expiration date comes before the issue date
   */
  License(Name driverName, Address driverAddress, int licenseNumber, LocalDate birthdate,
      Locale country, LocalDate issueDate, LocalDate expirationDate) throws InvalidDateException {
    this.driverName = driverName;
    this.driverAddress = driverAddress;
    this.licenseNumber = licenseNumber;
    this.birthdate = birthdate;
    this.country = country;
    this.issueDate = issueDate;
    this.expirationDate = expirationDate;

    verifyDate();
  }

  /**
   * A method that verifies the expiration date doesn't come before the issue date for a new
   * driver's license.
   *
   * @throws InvalidDateException if the license expires before it's issued
   */
  private void verifyDate() throws InvalidDateException {
    if (expirationDate.isBefore(issueDate)) {
      throw new InvalidDateException("The issue date can't come before the expiration date!");
    }
  }

  int calculateAge() {
    return Period.between(this.birthdate, CURRENT_DATE).getYears();
  }

  Name getDriverName() {
    return driverName;
  }

  Address getDriverAddress() {
    return driverAddress;
  }

  int getLicenseNumber() {
    return licenseNumber;
  }

  LocalDate getBirthdate() {
    return birthdate;
  }

  Locale getCountry() {
    return country;
  }

  LocalDate getIssueDate() {
    return issueDate;
  }

  LocalDate getExpirationDate() {
    return expirationDate;
  }

  int calculateLicenseAge(LocalDate localDate) {
    return Period.between(issueDate, CURRENT_DATE).getMonths();
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }
    License license = (License) object;
    return licenseNumber == license.licenseNumber
        && Objects.equals(driverName, license.driverName)
        && Objects.equals(driverAddress, license.driverAddress)
        && Objects.equals(birthdate, license.birthdate)
        && Objects.equals(country, license.country)
        && Objects.equals(issueDate, license.issueDate)
        && Objects.equals(expirationDate, license.expirationDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(driverName, driverAddress, licenseNumber, birthdate, country, issueDate,
        expirationDate);
  }

  @Override
  public String toString() {
    return "License{"
        + "driverName=" + driverName
        + ", driverAddress=" + driverAddress
        + ", licenseNumber=" + licenseNumber
        + ", birthdate=" + birthdate
        + ", country=" + country
        + ", issueDate=" + issueDate
        + ", expirationDate=" + expirationDate
        + '}';
  }
}
