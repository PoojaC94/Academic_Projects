package edu.neu.ccs.cs5004.problem2;

import java.time.LocalDate;
import java.util.Objects;

/**
 * A class tha represents an application for a ride sharing service.
 */
public class Application extends AbstractPerson {

  private Name applicantName;
  private LocalDate applicantBirthdate;
  private License applicantLicense;
  private Vehicle applicantVehicle;
  private DriverHistory applicantDriverHistory;
  private VehicleHistory applicantVehicleHistory;
  private VehicleInsurance applicantVehicleInsurance;

  /**
   * A constructor for a Driver.
   *
   * @param applicantName             the Name of the Person
   * @param applicantBirthdate        the birthdate of the Person
   * @param applicantLicense          the License of the Person
   * @param applicantVehicle          the Vehicle of the Person
   * @param applicantDriverHistory    the History of the Person
   * @param applicantVehicleHistory   the Vehicle history of the Person
   * @param applicantVehicleInsurance the Vehicle insurance of the person
   */

  Application(Name applicantName,
      LocalDate applicantBirthdate, License applicantLicense,
      Vehicle applicantVehicle, DriverHistory applicantDriverHistory,
      VehicleHistory applicantVehicleHistory,
      VehicleInsurance applicantVehicleInsurance) {
    super(applicantName, applicantBirthdate, applicantLicense, applicantVehicle,
        applicantDriverHistory,
        applicantVehicleHistory, applicantVehicleInsurance);
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }
    Application that = (Application) object;
    return Objects.equals(applicantName, that.applicantName)
        && Objects.equals(applicantBirthdate, that.applicantBirthdate)
        && Objects.equals(applicantLicense, that.applicantLicense)
        && Objects.equals(applicantVehicle, that.applicantVehicle)
        && Objects.equals(applicantDriverHistory, that.applicantDriverHistory)
        && Objects.equals(applicantVehicleHistory, that.applicantVehicleHistory)
        && Objects.equals(applicantVehicleInsurance, that.applicantVehicleInsurance);
  }

  @Override
  public int hashCode() {
    return Objects.hash(applicantName, applicantBirthdate, applicantLicense, applicantVehicle,
        applicantDriverHistory, applicantVehicleHistory, applicantVehicleInsurance);
  }

  @Override
  public String toString() {
    return "Application{"
        + "applicantName=" + applicantName
        + ", applicantBirthdate=" + applicantBirthdate
        + ", applicantLicense=" + applicantLicense
        + ", applicantVehicle=" + applicantVehicle
        + ", applicantDriverHistory=" + applicantDriverHistory
        + ", applicantVehicleHistory=" + applicantVehicleHistory
        + ", applicantVehicleInsurance=" + applicantVehicleInsurance
        + '}';
  }
}
