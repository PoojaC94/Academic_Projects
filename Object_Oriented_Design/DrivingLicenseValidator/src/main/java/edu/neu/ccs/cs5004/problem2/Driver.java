package edu.neu.ccs.cs5004.problem2;

import java.time.LocalDate;
import java.util.Objects;

/**
 * A class that represents a driver for a ride sharing service.
 */
public class Driver extends AbstractPerson {

  private Name driverName;
  private LocalDate driverBirthdate;
  private License driverLicense;
  private Vehicle driverVehicle;
  private DriverHistory driverHistory;
  private VehicleHistory driverVehicleHistory;
  private VehicleInsurance driverVehicleInsurance;

  /**
   * A constructor for a Driver.
   *
   * @param driverName             the Name of the Driver
   * @param driverBirthdate        the birthdate of the Driver
   * @param driverLicense          the License of the Driver
   * @param driverVehicle          the Vehicle of the Driver
   * @param driverHistory          the History of the Driver
   * @param driverVehicleHistory   the vehicle history of the Driver
   * @param driverVehicleInsurance the vehicle insurance of the Driver
   */
  public Driver(Name driverName, LocalDate driverBirthdate,
      License driverLicense, Vehicle driverVehicle,
      DriverHistory driverHistory, VehicleHistory driverVehicleHistory,
      VehicleInsurance driverVehicleInsurance) {
    super(driverName, driverBirthdate, driverLicense, driverVehicle, driverHistory,
        driverVehicleHistory, driverVehicleInsurance);
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }
    if (!super.equals(object)) {
      return false;
    }
    Driver driver = (Driver) object;
    return Objects.equals(driverName, driver.driverName)
        && Objects.equals(driverBirthdate, driver.driverBirthdate)
        && Objects.equals(driverLicense, driver.driverLicense)
        && Objects.equals(driverVehicle, driver.driverVehicle)
        && Objects.equals(driverHistory, driver.driverHistory)
        && Objects.equals(driverVehicleHistory, driver.driverVehicleHistory)
        && Objects.equals(driverVehicleInsurance, driver.driverVehicleInsurance);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), driverName, driverBirthdate, driverLicense, driverVehicle,
        driverHistory, driverVehicleHistory, driverVehicleInsurance);
  }

  @Override
  public String toString() {
    return "Driver{"
        + "driverName=" + driverName
        + ", driverBirthdate=" + driverBirthdate
        + ", driverLicense=" + driverLicense
        + ", driverVehicle=" + driverVehicle
        + ", driverHistory=" + driverHistory
        + ", driverVehicleHistory=" + driverVehicleHistory
        + ", driverVehicleInsurance=" + driverVehicleInsurance
        + '}';
  }
}
