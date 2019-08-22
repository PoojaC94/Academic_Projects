package edu.neu.ccs.cs5004.problem2;

import java.time.LocalDate;
import java.util.Objects;

/**
 * A class that represents information about a person for a ride sharing system.
 */
abstract class AbstractPerson {

  private Name personName;
  private LocalDate personBirthdate;
  private License personLicense;
  private Vehicle personVehicle;
  private DriverHistory personHistory;
  private VehicleHistory personVehicleHistory;
  private VehicleInsurance personVehicleInsurance;

  /**
   * A constructor for a Driver.
   *
   * @param personName             the Name of the Person
   * @param personBirthdate        the birthdate of the Person
   * @param personLicense          the License of the Person
   * @param personVehicle          the Vehicle of the Person
   * @param personHistory          the History of the Person
   * @param personVehicleHistory   the VehicleHistory of the Person
   * @param personVehicleInsurance the VehicleInsurance of the Person
   */
  AbstractPerson(Name personName, LocalDate personBirthdate,
      License personLicense, Vehicle personVehicle,
      DriverHistory personHistory, VehicleHistory personVehicleHistory,
      VehicleInsurance personVehicleInsurance) {
    this.personName = personName;
    this.personBirthdate = personBirthdate;
    this.personLicense = personLicense;
    this.personVehicle = personVehicle;
    this.personHistory = personHistory;
    this.personVehicleHistory = personVehicleHistory;
    this.personVehicleInsurance = personVehicleInsurance;
  }


  Name getPersonName() {
    return personName;
  }

  LocalDate getPersonBirthdate() {
    return personBirthdate;
  }

  License getPersonLicense() {
    return personLicense;
  }

  Vehicle getPersonVehicle() {
    return personVehicle;
  }

  DriverHistory getPersonHistory() {
    return personHistory;
  }

  VehicleHistory getPersonVehicleHistory() {
    return personVehicleHistory;
  }

  VehicleInsurance getPersonVehicleInsurance() {
    return personVehicleInsurance;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }
    AbstractPerson that = (AbstractPerson) object;
    return Objects.equals(personName, that.personName)
        && Objects.equals(personBirthdate, that.personBirthdate)
        && Objects.equals(personLicense, that.personLicense)
        && Objects.equals(personVehicle, that.personVehicle)
        && Objects.equals(personHistory, that.personHistory)
        && Objects.equals(personVehicleHistory, that.personVehicleHistory)
        && Objects.equals(personVehicleInsurance, that.personVehicleInsurance);
  }

  @Override
  public int hashCode() {
    return Objects.hash(personName, personBirthdate, personLicense, personVehicle, personHistory,
        personVehicleHistory, personVehicleInsurance);
  }

  @Override
  public String toString() {
    return "AbstractPerson{"
        + "personName=" + personName
        + ", personBirthdate=" + personBirthdate
        + ", personLicense=" + personLicense
        + ", personVehicle=" + personVehicle
        + ", personHistory=" + personHistory
        + ", personVehicleHistory=" + personVehicleHistory
        + ", personVehicleInsurance=" + personVehicleInsurance
        + '}';
  }
}
