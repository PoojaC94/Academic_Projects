package edu.neu.ccs.cs5004.problem2;

import java.time.LocalDate;
import java.time.Period;
import java.util.Locale;
import java.util.Objects;

/**
 * A class which validates the application for the driver to be put in the driver pool.
 */
public class Validator implements IValidator {

  private static final LocalDate CURRENT_DATE = LocalDate.now();
  private static final int REQUIRED_MONTHS = 6;
  private Application application;


  /**
   * A constructor for a new application validator.
   *
   * @param application the application to validate
   */
  Validator(Application application) {
    this.application = application;
  }

  /**
   * Checks to see if the driver is no younger than 21.
   *
   * @return true if the applicant is at least 21 years of age, false otherwise
   */
  public Boolean checkAge() {
    int age = this.application.getPersonLicense().calculateAge();
    return (age >= 21);
  }

  /**
   * Checks to see if the Name written on the Application matches the Name written on the Driver's
   * License.
   *
   * @return true if the Names match, false if they don't
   */
  public Boolean checkName() {
    return this.application.getPersonName()
        .equals(this.application.getPersonLicense().getDriverName());
  }

  /**
   * Verifies that the birthdate listed on the Application matches the birthdate listed on the
   * License.
   *
   * @return true if the birthdates match, false otherwise
   */
  public Boolean checkBirthdate() {
    return this.application.getPersonBirthdate().equals(this.application.getPersonLicense()
        .getBirthdate());
  }

  /**
   * Verifies that the driver's License is from the specified country.
   *
   * @return true if the License listed on the Application is from the desired country
   */
  public Boolean checkCountry() {
    Locale country = new Locale("English", "United States");
    return this.application.getPersonLicense().getCountry().getDisplayCountry()
        .equals(country.getCountry());
  }

  /**
   * Checks to see if the driver's license was issued at least 6 months prior.
   *
   * @return a boolean
   */
  public Boolean checkLicenseIssueDate() {
    return this.application.getPersonLicense().calculateLicenseAge(CURRENT_DATE) >= REQUIRED_MONTHS;
  }

  /**
   * Checks to see if the driver's license is expired.
   *
   * @return a boolean
   */
  public Boolean checkLicenseExpirationDate() {
    return !Period.between(this.application.getPersonLicense().getExpirationDate(), CURRENT_DATE)
        .isZero();
  }

  /**
   * Checks to see if the vehicle is less than 20 years old.
   *
   * @return a boolean
   */
  public Boolean checkVehicleAge() {
    int maxAge = 20;
    return this.application.getPersonVehicle().calculateVehicleAge(CURRENT_DATE) <= maxAge;
  }

  /**
   * Checks to see if the applicant is the owner of the vehicle.
   *
   * @return a boolean
   */
  public Boolean checkOwner() {
    return this.application.getPersonVehicleInsurance().getVehicleOwner()
        .equals(this.application.getPersonName());
  }

  /**
   * Checks to see if the applicant is not the owner of the vehicle, then are they insured on the
   * vehicle.
   *
   * @return boolean
   */
  public Boolean checkVehicleInsurance() {
    if (checkOwner()) {
      return true;

    }
    return this.application.getPersonVehicleInsurance().getCoveredPeople()
        .contains(this.application.getPersonName());
  }

  /**
   * Checks to see if the applicant has expired insurance.
   */
  public Boolean isInsuranceExpired() {
    return !Period.between(this.application.getPersonVehicleInsurance().getInsuranceExpiration(),
        CURRENT_DATE).isZero();
  }

  private Boolean movingViolations() {
    return this.application.getPersonHistory().getMovingViolations()
        .getListOfViolations().size() > 0;
  }

  /**
   * Checks to see if there were any specific moving violations on the applicants driving record.
   *
   * @return a boolean
   */
  Boolean movingViolationDetails() {
    if (movingViolations()) {
      return this.application.getPersonVehicleHistory().getMovingViolation().getListOfViolations()
          .containsKey("reckless driving") || this.application.getPersonVehicleHistory()
          .getMovingViolation().getListOfViolations()
          .containsKey("speeding") || this.application.getPersonVehicleHistory()
          .getMovingViolation()
          .getListOfViolations()
          .containsKey("DUI") || this.application.getPersonVehicleHistory().getMovingViolation()
          .getListOfViolations()
          .containsKey("driving with invalid license");
    }
    return false;
  }

  /**
   * Checks to see if there are any crashes on the applicants list.
   *
   * @return a boolean
   */
  private Boolean crashes() {
    return this.application.getPersonVehicleHistory().getCrash().getListOfCrashes().size() > 0;
  }

  /**
   * Checks to see if there were any moving violations in the last six months.
   *
   * @return a boolean
   */
  public Boolean checkDriverHistory() {
    if (!movingViolations()) {
      return true;
    }
    return (this.application.getPersonHistory().getMovingViolations()
        .calculateRecentMovingViolation(CURRENT_DATE) < REQUIRED_MONTHS);
  }

  /**
   * checks to see if there were any crashes in the last six months.
   *
   * @return a boolean
   */
  public Boolean checkVehicleHistory() {
    if (!crashes()) {
      return false;
    }
    return this.application.getPersonVehicleHistory().getCrash()
        .calculateRecentCrash(CURRENT_DATE) >= REQUIRED_MONTHS;
  }

  Boolean validateAll() {
    boolean[] processor = {
        checkAge(),
        checkName(),
        checkOwner(),
        checkVehicleAge(),
        checkVehicleInsurance(),
        checkBirthdate(),
        checkCountry(),
        isInsuranceExpired(),
        checkDriverHistory(),
        checkVehicleHistory()
    };

    for (boolean value : processor) {
      return value;
    }
    return true;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }
    Validator validator = (Validator) object;
    return Objects.equals(application, validator.application);
  }

  @Override
  public int hashCode() {
    return Objects.hash(application);
  }

  @Override
  public String toString() {
    return "Validator{"
        + "application=" + application
        + '}';
  }
}
