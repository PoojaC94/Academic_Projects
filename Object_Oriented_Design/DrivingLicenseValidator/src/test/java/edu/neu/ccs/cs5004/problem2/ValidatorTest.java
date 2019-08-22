package edu.neu.ccs.cs5004.problem2;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ValidatorTest {

  private Validator validator1;
  private Validator validator3;

  @Before
  public void setUp() throws Exception {
    Name sampleName1 = new Name("Jane", "Doe");
    Name sampleName2 = new Name("John", "Doe");
    Address sampleAddress = new Address(101, "Strawberry Street",
        "Houston", "TX", 54321);
    LocalDate sampleBirthdate = LocalDate.of(1990, 5, 21);
    Locale sampleCountry = new Locale("", "United States");
    LocalDate sampleIssueDate = LocalDate.of(2010, 4, 20);
    LocalDate sampleExpirationDate = LocalDate.of(2020, 4, 20);
    Vehicle sampleVehicle = new Vehicle("Ford", "Focus",
        2015, sampleName1);
    MovingViolation sampleMovingViolation = new MovingViolation();
    NonmovingViolation sampleNonmovingViolation = new NonmovingViolation();
    DriverHistory sampleDriverHistory = new DriverHistory(sampleMovingViolation,
        sampleNonmovingViolation);
    Crash sampleCrash = new Crash();
    VehicleHistory sampleVehicleHistory = new VehicleHistory(sampleMovingViolation, sampleCrash);
    License sampleLicense = new License(sampleName1, sampleAddress, 1234567890,
        sampleBirthdate, sampleCountry, sampleIssueDate, sampleExpirationDate);
    Name vehicleOwner = new Name("Jesus", "Christ");
    Name person1 = new Name("Barack", "Obama");
    List<Name> coveredPeople = new ArrayList<>(Arrays.asList(vehicleOwner, person1));
    LocalDate sampleInsuranceExpiration = LocalDate.of(2024, 2, 14);
    VehicleInsurance sampleVehicleInsurance = new VehicleInsurance(vehicleOwner, coveredPeople,
        sampleInsuranceExpiration);
    VehicleInsurance sampleVehicleInsurance2 = new VehicleInsurance(sampleName1, coveredPeople,
        sampleInsuranceExpiration);

    Application sampleApplicant1 = new Application(sampleName1, sampleBirthdate, sampleLicense,
        sampleVehicle, sampleDriverHistory, sampleVehicleHistory, sampleVehicleInsurance2);
    validator1 = new Validator(sampleApplicant1);
    validator3 = new Validator(sampleApplicant1);

    Application sampleApplicant2 = new Application(sampleName2, sampleBirthdate, sampleLicense,
        sampleVehicle, sampleDriverHistory, sampleVehicleHistory, sampleVehicleInsurance2);
    Validator validator2 = new Validator(sampleApplicant2);

  }

  @Test
  public void checkAge() {
    Assert.assertTrue(this.validator1.checkAge());
  }

  @Test
  public void checkName() {
    Assert.assertTrue(this.validator1.checkName());
  }

  @Test
  public void checkBirthdate() {
    Assert.assertTrue(this.validator1.checkBirthdate());
  }

  @Test
  public void checkCountry() {
    Assert.assertTrue(this.validator1.checkCountry());
  }

  @Test
  public void checkLicenseIssueDate() {
    Assert.assertTrue(this.validator1.checkLicenseIssueDate());
  }

  @Test
  public void checkLicenseExpirationDate() {
    Assert.assertTrue(this.validator1.checkLicenseExpirationDate());

  }

  @Test
  public void checkVehicleAge() {
    System.out.println(this.validator1.checkVehicleAge());
    Assert.assertTrue(this.validator1.checkVehicleAge());
  }

  @Test
  public void checkOwner() {
    System.out.println(this.validator1.checkOwner());
    Assert.assertTrue(this.validator1.checkOwner());
  }

  @Test
  public void checkVehicleInsurance() {
    Assert.assertTrue(this.validator1.checkVehicleInsurance());
  }

  @Test
  public void isInsuranceExpired() {
    Assert.assertTrue(this.validator1.isInsuranceExpired());
  }

  @Test
  public void movingViolationDetails() {
    Assert.assertTrue(this.validator1.movingViolationDetails());

  }

  @Test
  public void checkDriverHistory() {
    Assert.assertTrue(this.validator1.checkDriverHistory());
  }

  @Test
  public void checkVehicleHistory() {
    Assert.assertTrue(this.validator1.checkVehicleInsurance());
  }

  @Test
  public void validateAll() {
    Assert.assertTrue(this.validator1.validateAll());
  }

  @Test
  public void equalsToTest1() {
    Assert.assertTrue(validator1.equals(validator3));
  }

  @Test
  public void hashCodeTest() {
    System.out.println(validator3.hashCode());
  }
}