package edu.neu.ccs.cs5004.problem2;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DriverTest {

  private Driver sampleDriver1;
  private Driver sampleDriver2;
  private Driver sampleDriver3;

  @Before
  public void setUp() throws Exception {
    Name sampleName1 = new Name("John", "Doe");
    Name sampleName2 = new Name("Jane", "Doe");
    Address sampleAddress = new Address(101, "Strawberry Street",
        "Houston", "TX", 54321);
    LocalDate sampleBirthdate = LocalDate.of(1990, 5, 21);
    Locale sampleCountry = new Locale("", "US");
    LocalDate sampleIssueDate = LocalDate.of(2010, 4, 20);
    LocalDate sampleExpirationDate = LocalDate.of(2020, 4, 20);
    Vehicle sampleVehicle = new Vehicle("Ford", "Focus",
        2015, sampleName2);
    MovingViolation sampleMovingViolation = new MovingViolation();
    NonmovingViolation sampleNonmovingViolation = new NonmovingViolation();
    DriverHistory sampleDriverHistory = new DriverHistory(sampleMovingViolation,
        sampleNonmovingViolation);
    Crash sampleCrash = new Crash();
    VehicleHistory sampleVehicleHistory = new VehicleHistory(sampleMovingViolation, sampleCrash);
    License sampleLicense = new License(sampleName2, sampleAddress, 1234567890,
        sampleBirthdate, sampleCountry, sampleIssueDate, sampleExpirationDate);
    Name vehicleOwner = new Name("Jesus", "Christ");
    Name person1 = new Name("Barack", "Obama");
    List<Name> coveredPeople = new ArrayList<>(Arrays.asList(vehicleOwner, person1));
    LocalDate sampleInsuranceExpiration = LocalDate.of(2024, 2, 14);
    VehicleInsurance sampleVehicleInsurance = new VehicleInsurance(vehicleOwner, coveredPeople,
        sampleInsuranceExpiration);

    sampleDriver1 = new Driver(sampleName2, sampleBirthdate, sampleLicense, sampleVehicle,
        sampleDriverHistory, sampleVehicleHistory, sampleVehicleInsurance);
    sampleDriver2 = new Driver(sampleName2, sampleBirthdate, sampleLicense, sampleVehicle,
        sampleDriverHistory, sampleVehicleHistory, sampleVehicleInsurance);
    sampleDriver3 = new Driver(sampleName1, sampleBirthdate, sampleLicense, sampleVehicle,
        sampleDriverHistory, sampleVehicleHistory, sampleVehicleInsurance);
  }

  @Test
  public void getDriverName() {
    Assert.assertEquals(sampleDriver1.getPersonName().toString(), "Jane Doe");
  }

  @Test
  public void getDriverBirthdate() {
    Assert.assertEquals(sampleDriver1.getPersonBirthdate(), LocalDate.of(1990, Month.MAY,
        21));
  }

  @Test
  public void getLicenseInformation() {
    Assert.assertNotNull(sampleDriver1.getPersonLicense());
  }

  @Test
  public void getDriverHistory() {
    Assert.assertNotNull(sampleDriver1.getPersonHistory());
  }

  @Test
  public void getVehicleHistory() {
    Assert.assertNotNull(sampleDriver1.getPersonHistory());
  }

  @Test
  public void toStringTest() {
    System.out.println(sampleDriver1.toString());
  }

  @Test
  public void equalsToTest1() {
    Assert.assertTrue(sampleDriver1.equals(sampleDriver2));
  }

  @Test
  public void equalsToTest2() {
    Assert.assertFalse(sampleDriver1.equals(sampleDriver3));
  }

  @Test
  public void hashCodeTest() {
    System.out.println(sampleDriver1.hashCode());
  }
}
